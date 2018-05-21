package se.umu.its.cambro.kaltura.migration.playlist.migrators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import se.umu.its.cambro.kaltura.migration.MigrationConfiguration;
import se.umu.its.cambro.kaltura.migration.player.PlayerIdMappingHolder;
import se.umu.its.cambro.kaltura.migration.playlist.KalturaPlaylistsHolder;
import se.umu.its.cambro.kaltura.migration.ResultPrinter;
import se.umu.its.cambro.kaltura.migration.RowToUpdate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PatternPlaylistMigrator implements PlaylistMigrator {

    private static final Logger logger = LogManager.getLogger(PatternPlaylistMigrator.class);

    @Autowired
    private KalturaPlaylistsHolder kalturaPlaylistsHolder;

    @Autowired
    private PlayerIdMappingHolder playerIdMappingHolder;

    @Autowired
    private ResultPrinter resultPrinter;

    @Autowired
    protected MigrationConfiguration migrationConfiguration;

    @Override
    public RowToUpdate migrate(RowToUpdate rowToUpdate) {
        String valueToMigrate = rowToUpdate.getValueToMigrate();

        Matcher playlistMigrationMatcher = getPattern().matcher(valueToMigrate);

        while (playlistMigrationMatcher.find()) {
            try {
                valueToMigrate = migrate(playlistMigrationMatcher, valueToMigrate);
            } catch (Exception e) {
                rowToUpdate.errorMessages().add(e.getMessage());
                logger.error(e.getMessage());
            }
        }

        rowToUpdate.columnMigratedValue(valueToMigrate);

        return rowToUpdate;
    }


    protected String playlistIdForReferenceId(String referenceId) {
        try {
            return kalturaPlaylistsHolder.playlistIdForReferenceId(referenceId);
        } catch (Exception e) {
            if (!kalturaPlaylistsHolder.existsPlaylistWithId(referenceId)) {
                resultPrinter.addNotFoundId(referenceId);
                throw e;
            }
            return referenceId;
        }
    }

    protected String newPlayerIdFor(String oldPlayerId) {
        try {
            return playerIdMappingHolder.newPlayerIdFor(oldPlayerId);
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
        return migrationConfiguration.playlistPlayerId();
    }


    protected abstract Pattern getPattern();

    protected abstract String migrate(Matcher matcher, String valueToMigrate);
}
