package se.umu.its.cambro.kaltura.migration.player;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@PropertySource("classpath:kaltura_migration.properties")
@Component
public class PlayerIdMappingHolder {

    private static final Logger logger = LogManager.getLogger(PlayerIdMappingHolder.class);

    @Value("${migration.player.id.mapping.file}")
    private String playerIdMappingFile;

    private Map<String, String> playerIdMapping = new HashMap<>();

    @PostConstruct
    public void importFromFile() throws IOException {

        if (playerIdMappingFile != null && !playerIdMappingFile.isEmpty()) {
            logger.info("Fetching player id mapping from: " + playerIdMappingFile);

            Files.lines(Paths.get(playerIdMappingFile))
                    .forEach(this::addToPlayerIdMapping);
        } else {
            logger.info("No file for player id mapping given, using default values");
        }
    }

    public String newPlayerIdFor(String oldPlayerId) {
        if (playerIdMapping.containsKey(oldPlayerId)) {
            return playerIdMapping.get(oldPlayerId);
        }
        throw new RuntimeException("Failed to find new player id for old player id: " + oldPlayerId);
    }

    void playerIdMappingFile(String playerIdMappingFile) {
        this.playerIdMappingFile = playerIdMappingFile;
    }

    private void addToPlayerIdMapping(String formattedString) {
        String[] splittedLine = formattedString.split(",");
        playerIdMapping.put(splittedLine[0], splittedLine[1]);
    }
}
