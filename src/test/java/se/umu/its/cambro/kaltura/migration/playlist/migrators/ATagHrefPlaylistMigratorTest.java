package se.umu.its.cambro.kaltura.migration.playlist.migrators;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import se.umu.its.cambro.kaltura.migration.MigrationConfiguration;
import se.umu.its.cambro.kaltura.migration.MigratorTest;
import se.umu.its.cambro.kaltura.migration.player.PlayerIdMappingHolder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ATagHrefPlaylistMigratorTest extends MigratorTest{


    @Mock
    private MigrationConfiguration migrationConfiguration;

    @Mock
    private PlayerIdMappingHolder playerIdMappingHolder;

    @InjectMocks
    private ATagHrefPlaylistMigrator aTagHrefPlaylistMigrator;

    @Test
    public void shouldMigrateATagPlaylist() {


        given(rowToUpdate.getValueToMigrate()).willReturn("mixed text <a href=\"https://cdnapisec.kaltura.com/p/1842011/sp/184201100/embedIframeJs/uiconf_id/27244492/partner_id/1842011/widget_id/1_if0qncww?iframeembed=true&amp;playerId=kaltura_player_599ecd01015f5&amp;flashvars[playlistAPI.kpl0Id]=1_0yu82cyx&amp;flashvars[playlistAPI.autoContinue]=true&amp;flashvars[playlistAPI.autoInsert]=true&amp;flashvars[ks]=&amp;flashvars[streamerType]=auto&amp;flashvars[localizationCode]=en%22%20width=%22740%22%20height=%22330%22%20allowfullscreen%20webkitallowfullscreen%20mozAllowFullScreen%20frameborder=%220%22%20title=%22Kaltura%20Player\" target=\"_blank\"><b>FILMER om L-ABCDE</b></a>");

        given(kalturaPlaylistsHolder.playlistIdForReferenceId("1_0yu82cyx")).willReturn("new_playlist_id");

        given(migrationConfiguration.oldEmbeddedUrl()).willReturn("cdnapisec.kaltura.com");

        given(migrationConfiguration.newEmbeddedUrl()).willReturn("new_embedd_url");

        given(migrationConfiguration.oldPartnerId()).willReturn("1842011");

        given(migrationConfiguration.newPartnerId()).willReturn("new_partner_id");

        given(playerIdMappingHolder.newPlayerIdFor("27244492")).willReturn("new_player_id");

        given(migrationConfiguration.playlistKalturaPlayer()).willReturn("new_kaltura_player_id");

        aTagHrefPlaylistMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("mixed text <a href=\"https://new_embedd_url/p/new_partner_id/sp/new_partner_id00/embedIframeJs/uiconf_id/new_player_id/partner_id/new_partner_id?iframeembed=true&amp;playerId=new_kaltura_player_id&amp;flashvars[playlistAPI.kpl0Id]=new_playlist_id&amp;flashvars[playlistAPI.autoContinue]=true&amp;flashvars[playlistAPI.autoInsert]=true&amp;flashvars[ks]=&amp;flashvars[streamerType]=auto&amp;flashvars[localizationCode]=en%22%20width=%22740%22%20height=%22330%22%20allowfullscreen%20webkitallowfullscreen%20mozAllowFullScreen%20frameborder=%220%22%20title=%22Kaltura%20Player\" target=\"_blank\"><b>FILMER om L-ABCDE</b></a>");
    }
}