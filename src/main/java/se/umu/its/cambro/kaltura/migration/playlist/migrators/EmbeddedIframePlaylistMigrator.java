package se.umu.its.cambro.kaltura.migration.playlist.migrators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmbeddedIframePlaylistMigrator extends PatternPlaylistMigrator {

    @Override
    protected Pattern getPattern() {
        return Pattern.compile("<iframe.*?/p/" + migrationConfiguration.oldPartnerId() + ".*?uiconf_id/(?<uiconfid>\\d+).*?playlistAPI.kpl0Id.=(?<playlistid>\\S{10}).*?(<){1}", Pattern.DOTALL);
    }

    @Override
    protected String migrate(Matcher matcher, String valueToMigrate) {

        String oldPlaylistId = matcher.group("playlistid");

        String uiConfId = matcher.group("uiconfid");

        String newPlaylistId = playlistIdForReferenceId(oldPlaylistId);

        String newPlayerId = newPlayerIdFor(uiConfId);

        String replacement = matcher.group();

        replacement = replacement.replaceAll(oldPlaylistId, newPlaylistId);
        replacement = replacement.replaceAll(uiConfId, newPlayerId);
        replacement = replacement.replaceAll(migrationConfiguration.oldEmbeddedUrl(), migrationConfiguration.newEmbeddedUrl());
        replacement = replacement.replaceAll(migrationConfiguration.oldPartnerId(), migrationConfiguration.newPartnerId());
        replacement = replacement.replaceAll(migrationConfiguration.oldPartnerId() + "00", migrationConfiguration.newPartnerId() + "00");

        replacement = replacement.replaceAll("/widget_id/\\S{10}", "");
        replacement = replacement.replaceFirst("playerId=kaltura_player_\\S+?&", "playerId=" + migrationConfiguration.playlistKalturaPlayer() + "&");

        return valueToMigrate.replace(matcher.group(), replacement);
    }
}
