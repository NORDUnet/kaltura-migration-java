package se.umu.its.cambro.kaltura.migration.entry.migrators;

import se.umu.its.cambro.kaltura.migration.RowToUpdate;

public interface EntryMigrator {

    RowToUpdate migrate(RowToUpdate rowToUpdate);
}
