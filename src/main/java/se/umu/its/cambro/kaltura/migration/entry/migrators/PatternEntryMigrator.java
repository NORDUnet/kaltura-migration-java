package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import se.umu.its.cambro.kaltura.migration.MigrationConfiguration;
import se.umu.its.cambro.kaltura.migration.entry.KalturaMediaEntriesHolder;
import se.umu.its.cambro.kaltura.migration.ResultPrinter;
import se.umu.its.cambro.kaltura.migration.RowToUpdate;
import se.umu.its.cambro.kaltura.migration.player.PlayerIdMappingHolder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PatternEntryMigrator implements EntryMigrator {

    private static final Logger logger = LogManager.getLogger(PatternEntryMigrator.class);

    @Autowired
    private KalturaMediaEntriesHolder kalturaMediaEntriesHolder;

    @Autowired
    private PlayerIdMappingHolder playerIdMappingHolder;

    @Autowired
    private ResultPrinter resultPrinter;

    @Autowired
    protected MigrationConfiguration migrationConfiguration;

    @Override
    public RowToUpdate migrate(RowToUpdate rowToUpdate) {

        String valueToMigrate = rowToUpdate.getValueToMigrate();

        Matcher migrationMatcher = getPattern().matcher(valueToMigrate);

        while (migrationMatcher.find()) {

            try {
                valueToMigrate = migrate(migrationMatcher, valueToMigrate);
            } catch (Exception e) {
                rowToUpdate.errorMessages().add(e.getMessage());
                logger.error(e.getMessage());
            }
        }

        rowToUpdate.columnMigratedValue(valueToMigrate);

        return rowToUpdate;
    }

    protected String mediaEntryIdForReferenceId(String referenceId) {
        try {
            return kalturaMediaEntriesHolder.mediaEntryIdForReferenceId(referenceId);
        } catch (Exception e) {
            if (!kalturaMediaEntriesHolder.existsNewMediaEntryWithId(referenceId)) {
                resultPrinter.addNotFoundId(referenceId);
                throw e;
            }
            return referenceId;
        }
    }

    protected String thumbnailUrlForReferenceId(String referenceId) {
        try {
            return kalturaMediaEntriesHolder.thumbnailUrlForReferenceId(referenceId);
        } catch (Exception e) {
            if (!kalturaMediaEntriesHolder.existsNewMediaEntryWithId(referenceId)) {
                resultPrinter.addNotFoundId(referenceId);
                throw e;
            }
            return kalturaMediaEntriesHolder.thumbnailUrlForId(referenceId);
        }
    }

    protected String newPlayerIdFor(String oldPlayerId) {
        try {
            return playerIdMappingHolder.newPlayerIdFor(oldPlayerId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return migrationConfiguration.playerId();
    }

    abstract Pattern getPattern();

    abstract String migrate(Matcher migrationMatcher, String valueToMigrate);
}