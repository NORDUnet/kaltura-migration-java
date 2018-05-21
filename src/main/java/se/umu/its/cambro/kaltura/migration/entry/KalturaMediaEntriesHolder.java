package se.umu.its.cambro.kaltura.migration.entry;

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
public class KalturaMediaEntriesHolder {

    private static final Logger logger = LogManager.getLogger(KalturaMediaEntriesHolder.class);

    @Value("${kaltura.entries.file.path}")
    private String filePath;

    private List<KalturaMediaEntry> kalturaMediaEntries;

    @PostConstruct
    public void init() throws IOException {
        logger.info("Fetching entries from: " + filePath);

        if(filePath == null) {
            throw new RuntimeException("No file path given for entries information");
        }

        this.kalturaMediaEntries = Files.lines(Paths.get(filePath))
                .map(this::fromFormattedString)
                .collect(Collectors.toList());
    }

    private KalturaMediaEntry fromFormattedString(String formattedString) {
        String[] splittedLine = formattedString.split(",");
        return new KalturaMediaEntry(splittedLine[1], splittedLine[0], splittedLine[2]);
    }

    public boolean existsNewMediaEntryWithId(String id) {
        return getById(id).isPresent();
    }

    public String mediaEntryIdForReferenceId(String referenceId) {
        Optional<KalturaMediaEntry> kalturaMediaEntry = getByReferenceId(referenceId);
        if(kalturaMediaEntry.isPresent()) {
            return kalturaMediaEntry.get().id();
        } else {
            throw new RuntimeException("Could not find entry with reference id: " + referenceId);
        }
    }

    public String thumbnailUrlForReferenceId(String referenceId) {
        Optional<KalturaMediaEntry> kalturaMediaEntry = getByReferenceId(referenceId);
        if(kalturaMediaEntry.isPresent()) {
            return kalturaMediaEntry.get().thumbnailUrl();
        } else {
            throw new RuntimeException("Could not find entry with reference id: " + referenceId);
        }
    }

    public String thumbnailUrlForId(String id) {
        Optional<KalturaMediaEntry> kalturaMediaEntry = getById(id);
        if(kalturaMediaEntry.isPresent()) {
            return kalturaMediaEntry.get().thumbnailUrl();
        } else {
            throw new RuntimeException("Could not find entry with id: " + id);
        }
    }

    private Optional<KalturaMediaEntry> getById(String id) {
        return kalturaMediaEntries.stream().filter(p -> p.id().equalsIgnoreCase(id)).findAny();
    }

    private Optional<KalturaMediaEntry> getByReferenceId(String referenceId) {
        return kalturaMediaEntries.stream().filter(p -> p.referenceId().equalsIgnoreCase(referenceId)).findAny();
    }


    void filePath(String filePath) {
        this.filePath = filePath;
    }
}