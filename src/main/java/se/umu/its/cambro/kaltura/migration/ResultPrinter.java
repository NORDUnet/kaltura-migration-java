package se.umu.its.cambro.kaltura.migration;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ResultPrinter {

    private static final String MIGRATED_LOG = "migrated.log";
    private static final String MIGRATED_ERROR_LOG = "migrated_error.log";
    private static final String UNMIGRATED_LOG = "unmigrated.log";
    private static final String UNMIGRATED_ERROR_LOG = "unmigrated_error.log";
    private static final String NOT_FOUND_IDS_LOG = "not_found_ids.log";
    private static final String MIGRATION_HTML = "migration.html";

    private Set<String> notFoundIds = new HashSet<>();


    void printResults(List<RowToUpdate> rowsToUpdate) throws Exception {

        Path migratedPath = Paths.get(MIGRATED_LOG);
        writeToFile(migratedPath, rowsToUpdate.stream()
                                              .filter(p -> p.hasBeenMigrated() && p.errorMessages().isEmpty())
                                              .collect(Collectors.toList()), "\n\n");

        Path migratedErrorPath = Paths.get(MIGRATED_ERROR_LOG);
        writeToFile(migratedErrorPath, rowsToUpdate.stream()
                .filter(p -> p.hasBeenMigrated() && !p.errorMessages().isEmpty())
                .collect(Collectors.toList()), "\n\n");

        Path unmigratedPath = Paths.get(UNMIGRATED_LOG);
        writeToFile(unmigratedPath, rowsToUpdate.stream()
                                                .filter(p -> !p.hasBeenMigrated() && p.errorMessages().isEmpty())
                                                .collect(Collectors.toList()), "\n\n");

        Path unmigratedErrorPath = Paths.get(UNMIGRATED_ERROR_LOG);
        writeToFile(unmigratedErrorPath, rowsToUpdate.stream()
                                                     .filter(p -> !p.hasBeenMigrated() && !p.errorMessages().isEmpty())
                                                     .collect(Collectors.toList()), "\n\n");

        Path notFoundIdsPath = Paths.get(NOT_FOUND_IDS_LOG);
        writeToFile(notFoundIdsPath, new ArrayList<>(notFoundIds), "");


        writeHtml(rowsToUpdate);
    }

    private void writeHtml(List<RowToUpdate> rowsToUpdate) throws IOException {
        Path htmlPath = Paths.get(MIGRATION_HTML);

        Set<TableInformation> tables = rowsToUpdate.stream().map(RowToUpdate::tableInformation).collect(Collectors.toSet());


        try(BufferedWriter writer = Files.newBufferedWriter(htmlPath, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {

            writer.write("<html>");

            writeHead(writer);


            for(TableInformation tableInformation : tables) {

                writer.write("<table class=\"table table-bordered table-condensed\" style=\"table-layout:fixed;word-wrap:break-word;\">\n");

                writer.write("<thead>\n");

                writer.write("<tr>\n");

                writer.write("<th colspan=" + (tableInformation.primaryKeyColumns().size() + 2)+ ">" + tableInformation.tableName() + "</th>\n");

                writer.write("</tr>\n");

                writer.write("<tr>\n");

                writer.write(tableInformation.primaryKeyColumns().stream().map(p -> "<th>" + p + "</th>").collect(Collectors.joining("\n")));

                writer.write("<th class=\"col-md-2\">" + tableInformation.columnName() + "</th>\n");

                writer.write("<th>Error</th<\n");

                writer.write("</tr>\n");

                writer.write("</thead>\n");

                writer.write("<tbody>\n");

                writer.write(rowsToUpdate.stream()
                                         .filter(p -> p.hasBeenMigrated() && p.tableInformation().tableName().equalsIgnoreCase(tableInformation.tableName()) && p.tableInformation().columnName().equalsIgnoreCase(tableInformation.columnName()))
                                         .map(RowToUpdate::toHtml).collect(Collectors.joining("\n")));

                writer.write("</tbody>\n");

                writer.write("</table>\n");

                writer.write("<br><br>\n");
            }

            writer.write("</html>");
        }
    }

    private void writeHead(BufferedWriter writer) throws IOException {
       writer.write("<head>\n");
       writer.write("<title>Migration Report</title>\n");
       writer.write("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n");
       writer.write("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n");
       writer.write("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n");
       writer.write("</head>\n");
    }

    private void writeToFile(Path path, List<Object> objectsToWrite, String extraPrint) throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            for(Object objectToWrite : objectsToWrite) {
                writer.write(objectToWrite + "\n" + extraPrint);
            }

            writer.write("Total count: " + objectsToWrite.size());
        }
    }

    public void addNotFoundId(String id) {
        notFoundIds.add(id);
    }

    void printMdlTableResult(String tableName, List<MdlTableRowHolder> mdlTableRows) throws IOException {

        Path migratedFile = Paths.get(tableName + "_migrated.log");

        try (BufferedWriter writer = Files.newBufferedWriter(migratedFile, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {

            for (MdlTableRowHolder mdlTableRowHolder : mdlTableRows.stream().filter(MdlTableRowHolder::hasBeenMigrated).collect(Collectors.toList())) {
                writer.write(mdlTableRowHolder.toString() + "\n");
            }

            writer.write("Total count: " + mdlTableRows.stream().filter(MdlTableRowHolder::hasBeenMigrated).count());
        }

        Path unmigratedFile = Paths.get( tableName + "_unmigrated.log");

        try (BufferedWriter writer = Files.newBufferedWriter(unmigratedFile, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {

            for (MdlTableRowHolder mdlTableRowHolder : mdlTableRows.stream().filter(p -> !p.hasBeenMigrated()).collect(Collectors.toList())) {
                writer.write(mdlTableRowHolder.toString() + "\n");
            }

            writer.write("Total count: " + mdlTableRows.stream().filter(p -> !p.hasBeenMigrated()).count());
        }
    }
}
