package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EscapedEmbeddedIframeEntryMigrator extends EmbeddedIframeEntryMigrator {

    @Override
    Pattern getPattern() {
        return Pattern.compile(
                "(<|&lt;){1}iframe.*?id=\\\\\"kaltura_player\\\\\".*?\\\\/p\\\\/"+ migrationConfiguration.oldPartnerId()+".*?uiconf_id\\\\/(?<uiconfid>\\d+)\\\\/.*?entry_id=(?<entryid>\\S{10}).*?(>|&gt;){1}",
                Pattern.MULTILINE | Pattern.DOTALL);
    }

}
