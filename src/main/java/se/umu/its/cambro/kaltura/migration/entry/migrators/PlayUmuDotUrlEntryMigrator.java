package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PlayUmuDotUrlEntryMigrator extends PatternEntryMigrator {

    private static final Logger logger = LogManager.getLogger(PlayUmuDotUrlEntryMigrator.class);

    @Override
    Pattern getPattern() {
        return Pattern.compile("play.umu.se_(media_t_|t_media_|t_){1}(?<entryid>\\S{10}).url");
    }

    @Override
    String migrate(Matcher migrationMatcher, String valueToMigrate) {
        String oldEntryId = migrationMatcher.group("entryid");

        String newEntryId = mediaEntryIdForReferenceId(oldEntryId);

        logger.debug("Updating id: " + oldEntryId + " with id:" + newEntryId);

        valueToMigrate = valueToMigrate.replaceAll(oldEntryId, newEntryId);

        return valueToMigrate;
    }
}
