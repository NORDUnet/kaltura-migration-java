package se.umu.its.cambro.kaltura.migration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TableInformation implements Serializable {

    private String tableName;

    private List<String> primaryKeyColumns = new ArrayList<>();

    private String columnName;

    private TableInformation(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    static TableInformation create(String tableName, String columnName) {
        return new TableInformation(tableName, columnName);
    }

    String tableName() {
        return tableName;
    }

    List<String> primaryKeyColumns() {
        return primaryKeyColumns;
    }

    String columnName() {
        return columnName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableInformation that = (TableInformation) o;
        return Objects.equals(tableName, that.tableName) &&
                Objects.equals(columnName, that.columnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, columnName);
    }

    @Override
    public String toString() {
        return "TableInformation{" +
                "tableName='" + tableName + '\'' +
                ", primaryKeyColumns=" + primaryKeyColumns +
                ", columnName='" + columnName + '\'' +
                '}';
    }
}