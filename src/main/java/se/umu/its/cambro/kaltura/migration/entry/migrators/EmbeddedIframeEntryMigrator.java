package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmbeddedIframeEntryMigrator extends PatternEntryMigrator {

    @Override
    Pattern getPattern() {
        return Pattern.compile(
                "(<|&lt;){1}iframe.*?id=\"kaltura_player\".*?/p/"+ migrationConfiguration.oldPartnerId()+".*?uiconf_id/(?<uiconfid>\\d+)/.*?entry_id=(?<entryid>\\S{10}).*?(>|&gt;){1}",
                Pattern.MULTILINE | Pattern.DOTALL);
    }

    @Override
    String migrate(Matcher migrationMatcher, String valueToMigrate) {

        String oldEntryId = migrationMatcher.group("entryid");

        String uiConfId = migrationMatcher.group("uiconfid");

        String newEntryId = mediaEntryIdForReferenceId(oldEntryId);

        String newPlayerId = newPlayerIdFor(uiConfId);

        String replacement = migrationMatcher.group();

        replacement = replacement.replaceAll(oldEntryId, newEntryId);

        replacement = replacement.replaceAll(migrationConfiguration.oldPartnerId(), migrationConfiguration.newPartnerId());
        // Replace old partner id x 100
        replacement = replacement.replaceAll(migrationConfiguration.oldPartnerId() + "00", migrationConfiguration.newPartnerId() + "00");
        // Replace old base url
        replacement = replacement.replaceAll(migrationConfiguration.oldEmbeddedUrl(), migrationConfiguration.newEmbeddedUrl());
        // Replace uiconf_id
        replacement = replacement.replaceAll(uiConfId, newPlayerId);
        // Remove all wid=<id>
        replacement = replacement.replaceAll("&amp;&amp;wid=\\S{10}", "");

        valueToMigrate = valueToMigrate.replace(migrationMatcher.group(), replacement);

        return valueToMigrate;
    }
}
