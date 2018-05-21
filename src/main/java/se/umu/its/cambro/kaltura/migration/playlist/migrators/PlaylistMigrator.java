package se.umu.its.cambro.kaltura.migration.playlist.migrators;

import se.umu.its.cambro.kaltura.migration.RowToUpdate;

public interface PlaylistMigrator {

    RowToUpdate migrate(RowToUpdate rowToUpdate);
}
