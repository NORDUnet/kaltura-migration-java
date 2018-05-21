package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KalturaKafUriComMigrator extends PatternEntryMigrator {

    @Override
    Pattern getPattern() {
        return Pattern.compile("kaltura-kaf-uri.com.*?entryid/(?<entryid>\\S{10}).*?playerSkin/(?<playerskin>\\d+)", Pattern.DOTALL);
    }

    @Override
    String migrate(Matcher migrationMatcher, String valueToMigrate) {

        String oldEntryId = migrationMatcher.group("entryid");

        String playerSkin = migrationMatcher.group("playerskin");

        String newEntryId = mediaEntryIdForReferenceId(oldEntryId);

        String newPlayerId = newPlayerIdFor(playerSkin);

        String replacement = migrationMatcher.group();

        replacement = replacement.replaceAll(oldEntryId, newEntryId);

        replacement = replacement.replaceAll(playerSkin, newPlayerId);

        return valueToMigrate.replaceAll(migrationMatcher.group(), replacement);
    }
}
