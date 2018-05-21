package se.umu.its.cambro.kaltura.migration.playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.umu.its.cambro.kaltura.migration.playlist.migrators.*;

import java.util.Arrays;
import java.util.List;

@Component
public class PlaylistMigrators {

    @Autowired
    private EmbeddedIframePlaylistMigrator embeddedIframePlaylistMigrator;

    @Autowired
    private EscapedEmbeddedIframePlaylistMigrator escapedEmbeddedIframePlaylistMigrator;

    @Autowired
    private ATagHrefPlaylistMigrator aTagHrefPlaylistMigrator;

    @Autowired
    private MoodleUrlPlaylistMigrator moodleUrlPlaylistMigrator;

    public List<PlaylistMigrator> migrators() {
        return Arrays.asList(
                embeddedIframePlaylistMigrator,
                escapedEmbeddedIframePlaylistMigrator,
                aTagHrefPlaylistMigrator,
                moodleUrlPlaylistMigrator
        );
    }
}
