package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KwidgetUrlEntryMigrator extends PatternEntryMigrator {

    @Override
    Pattern getPattern() {
        return Pattern.compile("www.kaltura.com.*?wid/_" + migrationConfiguration.oldPartnerId() + ".*?ui_conf_id/(?<uiconfid>\\d+).*?entryId/(?<entryid>\\S{10})", Pattern.DOTALL);
    }

    @Override
    String migrate(Matcher migrationMatcher, String valueToMigrate) {

        String oldEntryId = migrationMatcher.group("entryid");

        String uiConfId = migrationMatcher.group("uiconfid");

        String newEntryId = mediaEntryIdForReferenceId(oldEntryId);

        String newPlayerId = newPlayerIdFor(uiConfId);

        String replacement = migrationMatcher.group();

        replacement = replacement.replaceAll(oldEntryId, newEntryId);

        replacement = replacement.replaceAll(uiConfId, newPlayerId);

        replacement = replacement.replaceAll(migrationConfiguration.oldPartnerId(), migrationConfiguration.newPartnerId());

        replacement = replacement.replaceAll("www.kaltura.com", migrationConfiguration.newKalturaUrl());

        return valueToMigrate.replaceAll(migrationMatcher.group(), replacement);
    }
}
