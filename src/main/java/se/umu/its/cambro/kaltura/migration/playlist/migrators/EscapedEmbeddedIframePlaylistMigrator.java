package se.umu.its.cambro.kaltura.migration.playlist.migrators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EscapedEmbeddedIframePlaylistMigrator extends EmbeddedIframePlaylistMigrator {

    @Override
    protected Pattern getPattern() {
        return Pattern.compile("<iframe.*?\\\\/p\\\\/" + migrationConfiguration.oldPartnerId() + ".*?uiconf_id\\\\/(?<uiconfid>\\d+).*?playlistAPI.kpl0Id.=(?<playlistid>\\S{10}).*?(<){1}", Pattern.DOTALL);
    }

    @Override
    protected String migrate(Matcher matcher, String valueToMigrate) {
        String replacement = super.migrate(matcher, valueToMigrate);

        replacement = replacement.replaceFirst("\\\\/widget_id\\\\/\\S{10}", "");

        return replacement;
    }
}