package se.umu.its.cambro.kaltura.migration.playlist;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PropertySource("classpath:kaltura_migration.properties")
@Component
public class KalturaPlaylistsHolder {

    private static final Logger logger = LogManager.getLogger(KalturaPlaylistsHolder.class);

    @Value("${kaltura.playlists.file.path}")
    private String filePath;

    private List<KalturaPlaylist> kalturaPlaylists;

    @PostConstruct
    public void init() throws IOException {

        logger.info("Fetching entries from: " + filePath);

        if (filePath == null) {
            throw new RuntimeException("Filepath has not been set");
        }

        this.kalturaPlaylists = Files.lines(Paths.get(filePath))
                .map(this::fromFormattedString)
                .collect(Collectors.toList());
    }

    public String playlistIdForReferenceId(String referenceId) {
        Optional<KalturaPlaylist> kalturaPlaylist = getByReferenceId(referenceId);
        if (kalturaPlaylist.isPresent()) {
            return kalturaPlaylist.get().id();
        } else {
            throw new RuntimeException("Could not find playlist with reference id: " + referenceId);
        }
    }

    public boolean existsPlaylistWithId(String id) {
        return kalturaPlaylists.stream().anyMatch(p -> p.id().equalsIgnoreCase(id));
    }

    private Optional<KalturaPlaylist> getByReferenceId(String referenceId) {
        return kalturaPlaylists.stream().filter(p -> p.referenceId().equalsIgnoreCase(referenceId)).findAny();
    }

    void filePath(String filePath) {
        this.filePath = filePath;
    }

    private KalturaPlaylist fromFormattedString(String formattedString) {
        String[] splittedLine = formattedString.split(",");
        return new KalturaPlaylist(splittedLine[1], splittedLine[0]);
    }
}
