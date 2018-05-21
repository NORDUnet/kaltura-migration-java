package se.umu.its.cambro.kaltura.migration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MigrationDAO {

    private static final Logger logger = LogManager.getLogger(MigrationDAO.class);

    private final JdbcTemplate jdbcTemplate;

    private final String schema;

    public MigrationDAO(JdbcTemplate jdbcTemplate, String schema) {
        this.jdbcTemplate = jdbcTemplate;
        this.schema = schema;
    }


    List<TableInformation> getTablesWithPossibleColumns() {

        List<Map<String, Object>> results = jdbcTemplate.queryForList("" +
                "SELECT table_name, column_name FROM information_schema.columns " +
                "WHERE table_schema in ('" + schema + "') " +
                "AND table_name NOT LIKE '%INNODB%' " +
                "AND table_name NOT LIKE 'sakai_event%' " +
                "AND table_name NOT LIKE 'rwikihistory%' " +
                "AND table_name NOT LIKE 'sakai_message_bundle%'" +
                "AND table_name NOT LIKE 'mfr%'" +
                "AND table_name NOT LIKE 'mdl_event%'" +
                "AND table_name NOT LIKE 'mdl_kalvidres'" +
                "AND table_name NOT LIKE 'mdl_kalvidpres'" +
                "AND table_name NOT LIKE 'mdl_kalvidassign_submission'" +
                "AND " +
                    "(column_type = 'longtext' " +
                    "OR column_type = 'text' " +
                    "OR column_type REGEXP 'varchar\\\\(([1-9]{1}[0-9]{2,}|[0-9]{4,})\\\\)')");

        List<TableInformation> tableInformations = results.stream()
                .map(p -> TableInformation.create((String) p.get("table_name"), (String) p.get("column_name")))
                .collect(toList());


        Set<String> tableNames = tableInformations.stream()
                                                  .map(TableInformation::tableName)
                                                  .collect(Collectors.toSet());

        for(String tableName : tableNames) {
            logger.debug("Fetching keys for: " + tableName);

            List<String> keys = new ArrayList<>();

            List<Map<String, Object>> primaryKeys = jdbcTemplate.queryForList("show keys from " + tableName + " where Key_name = 'PRIMARY'");
            for (Map<String, Object> primaryKey : primaryKeys) {
                keys.add((String) primaryKey.get("Column_name"));
            }
            if (keys.isEmpty()) {
                List<Map<String, Object>> uniques = jdbcTemplate.queryForList("show keys from " + tableName + " where Non_unique = 0");
                for (Map<String, Object> unique : uniques) {
                    keys.add((String) unique.get("Column_name"));
                }
            }

            if(keys.isEmpty()) {
                logger.error("Could not find primary key for table: " + tableName);
            }

            tableInformations.stream()
                             .filter(p -> p.tableName().equals(tableName))
                             .forEach(p -> p.primaryKeyColumns().addAll(keys));
        }

        tableInformations.stream()
                         .filter(p->p.primaryKeyColumns().contains(p.columnName()))
                         .forEach(p -> logger.error("Column is primary key, not updating: " + p.toString()));

        // Remove columns that are primary keys before returning
        return tableInformations.stream()
                                .filter(p -> !p.primaryKeyColumns().contains(p.columnName()))
                                .collect(Collectors.toList());
    }

    List<RowToUpdate> getRowsToUpdate(TableInformation tableInformation) {

        logger.debug("Checking for match: " + tableInformation.tableName() + "::" + tableInformation.columnName());

        if(tableInformation.primaryKeyColumns().isEmpty()) {
            logger.error("Not possible to check: " + tableInformation.tableName() + "::" + tableInformation.columnName() + ", since it doesn't have any unique values");
            return new ArrayList<>();
        }

        if(tableInformation.columnName().contains("_REF")) {
            logger.error("Ignoring column: " + tableInformation.tableName() + "::" + tableInformation.columnName() + " since it is possibly a reference to other table");
            return new ArrayList<>();
        }

        return jdbcTemplate.query("SELECT " + tableInformation.primaryKeyColumns().stream().collect(Collectors.joining(",")) + ", " + tableInformation.columnName() +
                        " FROM " + tableInformation.tableName() +
                        " WHERE " + tableInformation.columnName() + " LIKE '%entryId%'" +
                        " OR " + tableInformation.columnName() + " LIKE '%entryid%'" +
                        " OR " + tableInformation.columnName() + " LIKE '%entry_id%'" +
                        " OR " + tableInformation.columnName() + " LIKE '%play.umu.se%'" +
                        " OR " + tableInformation.columnName() + " LIKE '%kaltura%'" +
                        " OR " + tableInformation.columnName() + " LIKE '%uiconf_id%'" +
                        " OR " + tableInformation.columnName() + " LIKE '%playerSkin%'",
                new RowsToUpdateMapper(tableInformation));
    }

    boolean hasTable(String tableName) {
        return jdbcTemplate.queryForRowSet("SELECT table_name FROM information_schema.tables WHERE TABLE_SCHEMA=\'" + schema + "\' AND TABLE_NAME=\'" + tableName + "\'").next();
    }

    void updateRow(RowToUpdate rowToUpdate) {

        updateTableRow(rowToUpdate.tableInformation().tableName(),
                rowToUpdate.tableInformation().columnName(),
                rowToUpdate.tableInformation().primaryKeyColumns(),
                rowToUpdate.columnMigratedValue(),
                rowToUpdate.primaryKeyValues());
    }

    public void resetRow(RowToUpdate rowToUpdate) {

        updateTableRow(rowToUpdate.tableInformation().tableName(),
                rowToUpdate.tableInformation().columnName(),
                rowToUpdate.tableInformation().primaryKeyColumns(),
                rowToUpdate.columnToUpdateValue(),
                rowToUpdate.primaryKeyValues());
    }

    private void updateTableRow(String tableName, String columnName, List<String> primaryKeyColumnNames, String columnValue, List<Object> primaryKeyValues) {

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + tableName + " SET " + columnName + " = ? ");
        sql.append(" WHERE ");
        sql.append(primaryKeyColumnNames.stream().map(p -> p + " = ?").collect(Collectors.joining(" AND ")));

        List<Object> parameters = new ArrayList<>();
        parameters.add(columnValue);
        parameters.addAll(primaryKeyValues);

        jdbcTemplate.update(sql.toString(), parameters.toArray());

    }

    List<MdlTableRowHolder> getMdlTableRows(MdlTableRowHolder.MdlTable mdlTable) {
        if(mdlTable.equals(MdlTableRowHolder.MdlTable.MDL_KALVIDASSIGN_SUBMISSION)) {
            return jdbcTemplate.query("SELECT id, entry_id, source, metadata FROM " + mdlTable.tableName(), new MdlTableRowHolderMapper(mdlTable));
        }
        return jdbcTemplate.query("SELECT id, entry_id, uiconf_id, source, metadata FROM " + mdlTable.tableName(), new MdlTableRowHolderMapper(mdlTable));
    }

    public String schema() {
        return schema;
    }

    public void updateMdlTableRow(MdlTableRowHolder.MdlTable mdlTable, MdlTableRow mdlTableRow) {
        if(mdlTable.equals(MdlTableRowHolder.MdlTable.MDL_KALVIDASSIGN_SUBMISSION)) {
            jdbcTemplate.update("UPDATE " + mdlTable.tableName() + " SET entry_id=?, source=?, metadata=? WHERE id=?", mdlTableRow.entryId(), mdlTableRow.source(), mdlTableRow.metadata(), mdlTableRow.id());
        } else {
            jdbcTemplate.update("UPDATE " + mdlTable.tableName() + " SET entry_id=?, uiconf_id=?, source=?, metadata=? WHERE id=?", mdlTableRow.entryId(), mdlTableRow.uiconfId(), mdlTableRow.source(), mdlTableRow.metadata(), mdlTableRow.id());
        }
    }


    private class RowsToUpdateMapper implements RowMapper<RowToUpdate> {


        private final TableInformation tableInformation;

        RowsToUpdateMapper(TableInformation tableInformation) {
            this.tableInformation = tableInformation;
        }

        @Nullable
        @Override
        public RowToUpdate mapRow(ResultSet resultSet, int i) throws SQLException {
            RowToUpdate rowToUpdate = new RowToUpdate();
            rowToUpdate.tableInformation(this.tableInformation);
            for (String primaryKeyColumn : tableInformation.primaryKeyColumns()) {
                rowToUpdate.primaryKeyValues().add(resultSet.getObject(primaryKeyColumn));
            }
            rowToUpdate.columnToUpdateValue((String) resultSet.getObject(tableInformation.columnName()));
            return rowToUpdate;
        }
    }

    private class MdlTableRowHolderMapper implements RowMapper<MdlTableRowHolder> {


        private final MdlTableRowHolder.MdlTable mdlTable;

        MdlTableRowHolderMapper(MdlTableRowHolder.MdlTable mdlTable) {
            this.mdlTable = mdlTable;
        }

        @Nullable
        @Override
        public MdlTableRowHolder mapRow(ResultSet resultSet, int i) throws SQLException {

            MdlTableRowHolder mdlTableRowHolder = new MdlTableRowHolder();
            mdlTableRowHolder.mdlTable(mdlTable);

            MdlTableRow mdlTableRow = new MdlTableRow();
            mdlTableRow.id(resultSet.getBigDecimal("id").toBigInteger());
            mdlTableRow.entryId(resultSet.getString("entry_id"));
            if(!mdlTable.equals(MdlTableRowHolder.MdlTable.MDL_KALVIDASSIGN_SUBMISSION)) {
                mdlTableRow.uiconfId(resultSet.getBigDecimal("uiconf_id").toBigInteger());
            }
            mdlTableRow.source(resultSet.getString("source"));
            mdlTableRow.metadata(resultSet.getString("metadata"));

            mdlTableRowHolder.rowToMigrate(mdlTableRow);

            return mdlTableRowHolder;
        }
    }
}