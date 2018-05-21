package se.umu.its.cambro.kaltura.migration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RowToUpdate implements Serializable {

    private TableInformation tableInformation;

    private List<Object> primaryKeyValues = new ArrayList<>();

    private String columnToUpdateValue;

    private String columnMigratedValue;

    private List<String> errorMessages = new ArrayList<>();

    public TableInformation tableInformation() {
        return tableInformation;
    }

    public void tableInformation(TableInformation tableInformation) {
        this.tableInformation = tableInformation;
    }

    public List<Object> primaryKeyValues() {
        return primaryKeyValues;
    }

    public String columnToUpdateValue() {
        return columnToUpdateValue;
    }

    public void columnToUpdateValue(String columnToUpdateValue) {
        this.columnToUpdateValue = columnToUpdateValue;
    }

    public String columnMigratedValue() {
        return columnMigratedValue;
    }

    public void columnMigratedValue(String migratedValue) {
        this.columnMigratedValue = migratedValue;
    }

    public String getValueToMigrate() {
        return columnMigratedValue != null ? columnMigratedValue : columnToUpdateValue;
    }

    public List<String> errorMessages() {
        return errorMessages;
    }

    public boolean hasBeenMigrated(){
        return !columnToUpdateValue.equalsIgnoreCase(columnMigratedValue);
    }

    @Override
    public String toString() {
        return "RowToUpdate{" +
                "tableInformation=" + tableInformation +
                ", primaryKeyValues=" + primaryKeyValues +
                ", \ncolumnToUpdateValue='" + columnToUpdateValue + "\'\n" +
                ", columnMigratedValue='" + (hasBeenMigrated() ? columnMigratedValue : "") + "\'\n" +
                ", errorMessages=" + errorMessages +
                '}';
    }

    public String toHtml() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<tr");

        if(hasBeenMigrated() && errorMessages.isEmpty()) {
            stringBuilder.append(" class=\"success\"");
        } else if(hasBeenMigrated() && !errorMessages.isEmpty()) {
            stringBuilder.append(" class=\"warning\"");
        } else if(!hasBeenMigrated() && !errorMessages.isEmpty()) {
            stringBuilder.append(" class=\"danger\"");
        } else {
            stringBuilder.append(" class=\"info\"");
        }

        stringBuilder.append(">\n");

        stringBuilder.append(primaryKeyValues.stream().map(p -> "<td><xmp>" + p.toString() + "</xmp></td>\n").collect(Collectors.joining()));

        stringBuilder.append("<td style=\"width:500px\">")
                     .append(DiffToHtmlExecutor.diffToHtml(columnToUpdateValue, columnMigratedValue))
                     .append("</td>\n");

        stringBuilder.append("<td>")
                     .append(errorMessages.stream().collect(Collectors.joining("\n")))
                     .append("</td>\n");

        stringBuilder.append("</tr>\n");

        return stringBuilder.toString();
    }
}
