package se.umu.its.cambro.kaltura.migration.playlist;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import se.umu.its.cambro.kaltura.migration.MockitoTest;
import se.umu.its.cambro.kaltura.migration.playlist.KalturaPlaylistsHolder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class KalturaPlaylistsHolderTest extends MockitoTest{

    @InjectMocks
    private KalturaPlaylistsHolder kalturaPlaylistsHolder;

    @Test
    public void shouldFetchMediaEntries() throws IOException {
        kalturaPlaylistsHolder.filePath("src/test/resources/playlists.csv");

        kalturaPlaylistsHolder.init();

        assertThat(kalturaPlaylistsHolder.existsPlaylistWithId("0_8o4weqwm")).isTrue();
        assertThat(kalturaPlaylistsHolder.existsPlaylistWithId("0_kftfosdi")).isTrue();
        assertThat(kalturaPlaylistsHolder.playlistIdForReferenceId("1_prru36eh")).isEqualToIgnoringCase("0_8o4weqwm");
        assertThat(kalturaPlaylistsHolder.playlistIdForReferenceId("1_75f18q5d")).isEqualToIgnoringCase("0_kftfosdi");
    }

}
