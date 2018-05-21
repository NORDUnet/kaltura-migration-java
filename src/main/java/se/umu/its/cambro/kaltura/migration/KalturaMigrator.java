package se.umu.its.cambro.kaltura.migration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import se.umu.its.cambro.kaltura.migration.entry.migrators.EntryMigrator;
import se.umu.its.cambro.kaltura.migration.entry.EntryMigrators;
import se.umu.its.cambro.kaltura.migration.playlist.PlaylistMigrators;
import se.umu.its.cambro.kaltura.migration.playlist.migrators.PlaylistMigrator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class KalturaMigrator {

    private static final Logger logger = LogManager.getLogger(KalturaMigrator.class);

    @Autowired
    private MigrationDAO migrationDAO;

    @Autowired
    private ResultPrinter resultPrinter;

    @Autowired
    private EntryMigrators entryMigrators;

    @Autowired
    private PlaylistMigrators playlistMigrators;

    @Autowired
    private MdlTableRowMigrator mdlTableRowMigrator;

    public void migrate() throws Exception {

        LocalDateTime startTime = LocalDateTime.now();

        Arrays.stream(MdlTableRowHolder.MdlTable.values()).filter(p -> migrationDAO.hasTable(p.tableName()))
                .peek(p -> logger.info("Migrating: " + p.tableName()))
                .forEach(this::migrateMdlTable);

        migrateTextColumns();

        LocalDateTime endTime = LocalDateTime.now();

        logger.info("Migration done, took: " + ChronoUnit.MINUTES.between(startTime, endTime) + " minutes");
    }

    private void migrateMdlTable(MdlTableRowHolder.MdlTable mdlTable) {

        List<MdlTableRowHolder> mdlTableRows = migrationDAO.getMdlTableRows(mdlTable).stream()
                .map(p -> mdlTableRowMigrator.migrate(p))
                .collect(Collectors.toList());

        logger.info("Will migrate " + mdlTableRows.stream().filter(MdlTableRowHolder::hasBeenMigrated).count() + " number of rows in " + mdlTable.tableName());

        try {
            resultPrinter.printMdlTableResult(mdlTable.tableName(), mdlTableRows);
        } catch (IOException e) {
            logger.error("Failed to print result files: " + e.getMessage(), e);
        }

        mdlTableRows.stream()
                .filter(p -> p.hasBeenMigrated())
                .forEach(p -> migrationDAO.updateMdlTableRow(mdlTable, p.migratedRow()));
    }

    private void migrateTextColumns() throws Exception {

        List<RowToUpdate> rowsToUpdate = fetchRowsToUpdate();

        logger.info("Found " + rowsToUpdate.stream().map(p -> p.tableInformation().tableName()).distinct().count() + " number of tables to update");
        logger.info("Found " + rowsToUpdate.size() + " number of rows to update");

        rowsToUpdate = rowsToUpdate.parallelStream()
                .map(this::migrateData)
                .collect(Collectors.toList());

        logger.info("Will update " + rowsToUpdate.stream().filter(RowToUpdate::hasBeenMigrated).map(p -> p.tableInformation().tableName()).distinct().count() + " number of tables");
        logger.info("Will update " + rowsToUpdate.stream().filter(RowToUpdate::hasBeenMigrated).count() + " number of rows");

        resultPrinter.printResults(rowsToUpdate);

        rowsToUpdate.stream()
                .filter(p -> p.hasBeenMigrated())
                .forEach(p -> migrationDAO.updateRow(p));
    }

    private List<RowToUpdate> fetchRowsToUpdate() throws Exception {

        List<TableInformation> tablesWithPossibleColumns = migrationDAO.getTablesWithPossibleColumns();

        logger.info("Found " + tablesWithPossibleColumns.stream().map(TableInformation::tableName).distinct().count() + " number of tables");
        logger.info("Found " + tablesWithPossibleColumns.size() + " number of columns with possible migration");

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        return forkJoinPool.submit(
                () -> tablesWithPossibleColumns.parallelStream()
                        // Filter does that has no unique keys, not possible to perform update of those
                        .filter(p -> !p.primaryKeyColumns().isEmpty())
                        .map(p -> migrationDAO.getRowsToUpdate(p))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())).get();
    }

    private RowToUpdate migrateData(RowToUpdate rowToUpdate) {

        for (EntryMigrator entryMigrator : entryMigrators.migrators()) {
            rowToUpdate = entryMigrator.migrate(rowToUpdate);
        }

        for (PlaylistMigrator playlistMigrator : playlistMigrators.migrators()) {
            rowToUpdate = playlistMigrator.migrate(rowToUpdate);
        }

        return rowToUpdate;
    }
}