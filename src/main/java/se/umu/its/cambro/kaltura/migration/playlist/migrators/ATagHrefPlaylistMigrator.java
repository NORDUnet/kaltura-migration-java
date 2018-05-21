package se.umu.its.cambro.kaltura.migration.playlist.migrators;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ATagHrefPlaylistMigrator extends EmbeddedIframePlaylistMigrator {

    @Override
    protected Pattern getPattern() {
        return Pattern.compile("<a.*?/p/" + migrationConfiguration.oldPartnerId() + ".*?uiconf_id/(?<uiconfid>\\d+).*?playlistAPI.kpl0Id.=(?<playlistid>\\S{10}).*?(<){1}", Pattern.DOTALL);
    }

}
