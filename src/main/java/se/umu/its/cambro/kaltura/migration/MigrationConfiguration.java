package se.umu.its.cambro.kaltura.migration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:kaltura_migration.properties")
public class MigrationConfiguration {

    @Value("${migration.old.kaltura.url}")
    private String oldKalturaUrl;
    @Value("${migration.old.kaf.url}")
    private String oldKafUrl;
    @Value("${migration.new.kaltura.url}")
    private String newKalturaUrl;
    @Value("${migration.old.thumbnail.url}")
    private String oldThumbnailUrl;
    @Value("${migration.old.partner.id}")
    private String oldPartnerId;
    @Value("${migration.new.partner.id}")
    private String newPartnerId;
    @Value("${migration.old.embedded.url}")
    private String oldEmbeddedUrl;
    @Value("${migration.new.embedded.url}")
    private String newEmbeddedUrl;
    @Value("${migration.player.id}")
    private String playerId;
    @Value("${migration.playlist.player.id}")
    private String playlistPlayerId;
    @Value("${migration.playlist.kaltura.player}")
    private String playlistKalturaPlayer;

    public String oldKalturaUrl() {
        return oldKalturaUrl;
    }

    public String oldKafUrl() {
        return oldKafUrl;
    }

    public String newKalturaUrl() {
        return newKalturaUrl;
    }

    public String oldThumbnailUrl() {
        return oldThumbnailUrl;
    }

    public String oldPartnerId() {
        return oldPartnerId;
    }

    public String newPartnerId() {
        return newPartnerId;
    }

    public String oldEmbeddedUrl() {
        return oldEmbeddedUrl;
    }

    public String newEmbeddedUrl() {
        return newEmbeddedUrl;
    }

    public String playerId() {
        return playerId;
    }

    public String playlistPlayerId() {
        return playlistPlayerId;
    }

    public String playlistKalturaPlayer() {
        return playlistKalturaPlayer;
    }
}
