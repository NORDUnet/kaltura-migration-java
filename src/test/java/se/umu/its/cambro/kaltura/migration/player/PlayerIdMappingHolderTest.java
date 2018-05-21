package se.umu.its.cambro.kaltura.migration.player;

import org.junit.Test;
import org.mockito.InjectMocks;
import se.umu.its.cambro.kaltura.migration.MockitoTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerIdMappingHolderTest extends MockitoTest {

    @InjectMocks
    private PlayerIdMappingHolder playerIdMappingHolder;

    @Test
    public void shouldFetchPlayerIdMappings() throws IOException {

        playerIdMappingHolder.playerIdMappingFile("src/test/resources/player_ids.csv");

        playerIdMappingHolder.importFromFile();

        assertThat(playerIdMappingHolder.newPlayerIdFor("42555611")).isEqualToIgnoringCase("23449676");
        assertThat(playerIdMappingHolder.newPlayerIdFor("42126081")).isEqualToIgnoringCase("23449676");
        assertThat(playerIdMappingHolder.newPlayerIdFor("37096691")).isEqualToIgnoringCase("23449684");

    }
}