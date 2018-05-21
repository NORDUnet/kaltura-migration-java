package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class ThumbnailEntryMigrator extends PatternEntryMigrator {

    @Override
    Pattern getPattern() {
        return Pattern.compile(
                "(content=|src=){1}\"(?<oldurl>http(s)?://" + migrationConfiguration.oldThumbnailUrl() + ".*?/entry_id/(?<entryid>\\S{10})/.*?)\"", Pattern.MULTILINE | Pattern.DOTALL);
    }

    @Override
    String migrate(Matcher migrationMatcher, String valueToMigrate) {

        String oldUrl = migrationMatcher.group("oldurl");

        String oldEntryId = migrationMatcher.group("entryid");

        String thumbnailUrl = thumbnailUrlForReferenceId(oldEntryId);

        return valueToMigrate.replaceAll(oldUrl, thumbnailUrl);
    }
}
