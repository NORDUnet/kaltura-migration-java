package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PlayUmuEntryMigrator extends PatternEntryMigrator {

    @Override
    Pattern getPattern() {
        return Pattern.compile("play.umu.se/(media/t/|t/media/|t/|id/|edit/){1}(?<entryid>\\S{10})");
    }

    @Override
    String migrate(Matcher migrationMatcher, String valueToMigrate) {

        String oldEntryId = migrationMatcher.group("entryid");

        String newEntryId = mediaEntryIdForReferenceId(oldEntryId);

        return valueToMigrate.replaceAll(oldEntryId, newEntryId);
    }
}
