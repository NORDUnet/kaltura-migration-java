package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KalturaMediaMigrator extends PatternEntryMigrator {

    @Override
    Pattern getPattern() {
        return Pattern.compile("<span class=\"kaltura-media\" rel=\"(?<entryid>\\S{10})::video\">");
    }

    @Override
    String migrate(Matcher migrationMatcher, String valueToMigrate) {

        String oldEntryId = migrationMatcher.group("entryid");

        String newEntryId =  mediaEntryIdForReferenceId(oldEntryId);

        String replacement = migrationMatcher.group();

        replacement = replacement.replaceAll(oldEntryId, newEntryId);

        return valueToMigrate.replaceAll(migrationMatcher.group(), replacement);
    }
}
