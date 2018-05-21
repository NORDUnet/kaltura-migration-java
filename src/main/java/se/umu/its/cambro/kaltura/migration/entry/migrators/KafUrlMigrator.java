package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.umu.its.cambro.kaltura.migration.MigrationConfiguration;
import se.umu.its.cambro.kaltura.migration.RowToUpdate;

@Component
public class KafUrlMigrator implements EntryMigrator{

    @Autowired
    private MigrationConfiguration migrationConfiguration;

    @Override
    public RowToUpdate migrate(RowToUpdate rowToUpdate) {
        String valueToMigrate = rowToUpdate.getValueToMigrate();

        valueToMigrate = valueToMigrate.replaceAll(migrationConfiguration.oldKafUrl(), migrationConfiguration.newKalturaUrl());

        rowToUpdate.columnMigratedValue(valueToMigrate);

        return rowToUpdate;
    }
}
