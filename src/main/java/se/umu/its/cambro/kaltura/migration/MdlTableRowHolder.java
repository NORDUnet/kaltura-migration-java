package se.umu.its.cambro.kaltura.migration;

import java.util.ArrayList;
import java.util.List;

public class MdlTableRowHolder {

    public enum MdlTable {
        MDL_KALVIDRES("mdl_kalvidres"),
        MDL_KALVIDPRES("mdl_kalvidpres"),
        MDL_KALVIDASSIGN_SUBMISSION("mdl_kalvidassign_submission");

        private final String tableName;

        MdlTable(String tableName) {
            this.tableName = tableName;
        }

        public String tableName() {
            return tableName;
        }
    }

    private MdlTable mdlTable;

    private MdlTableRow rowToMigrate;

    private MdlTableRow migratedRow;

    private List<String> errors = new ArrayList<>();

    public MdlTable mdlTable() {
        return mdlTable;
    }

    public void mdlTable(MdlTable mdlTable) {
        this.mdlTable = mdlTable;
    }

    public MdlTableRow rowToMigrate() {
        return rowToMigrate;
    }

    public void rowToMigrate(MdlTableRow rowToMigrate) {
        this.rowToMigrate = rowToMigrate;
    }

    public MdlTableRow migratedRow() {
        return migratedRow;
    }

    public void migratedRow(MdlTableRow migratedRow) {
        this.migratedRow = migratedRow;
    }

    public boolean hasBeenMigrated() {
        return migratedRow != null && !rowToMigrate.entryId().equalsIgnoreCase(migratedRow.entryId());
    }

    public void error(String error) {
        this.errors.add(error);
    }

    @Override
    public String toString() {
        return "MdlTableRowHolder{" +
                "mdlTable=" + mdlTable +
                ", rowToMigrate=" + rowToMigrate +
                ", migratedRow=" + migratedRow +
                ", errors=" + errors +
                '}';
    }
}
