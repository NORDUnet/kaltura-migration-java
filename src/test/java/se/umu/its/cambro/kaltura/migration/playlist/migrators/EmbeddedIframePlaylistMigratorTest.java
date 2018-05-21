package se.umu.its.cambro.kaltura.migration.playlist.migrators;


import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import se.umu.its.cambro.kaltura.migration.MigrationConfiguration;
import se.umu.its.cambro.kaltura.migration.MigratorTest;
import se.umu.its.cambro.kaltura.migration.player.PlayerIdMappingHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class EmbeddedIframePlaylistMigratorTest extends MigratorTest {

    @Mock
    private MigrationConfiguration migrationConfiguration;

    @Mock
    private PlayerIdMappingHolder playerIdMappingHolder;

    @InjectMocks
    private EmbeddedIframePlaylistMigrator embeddedIframePlaylistMigrator;

    @Test
    public void shouldMigrateMultipleEmbeddedIframePlaylist() {

        given(rowToUpdate.getValueToMigrate()).willReturn("<p><iframe allowfullscreen=\"\" frameborder=\"0\" height=\"620\" mozallowfullscreen=\"\" src=\"https://cdnapisec.kaltura.com/p/1842011/sp/184201100/embedIframeJs/uiconf_id/27244512/partner_id/1842011/widget_id/1_uphi82ds?iframeembed=true&amp;playerId=kaltura_player_57eb69dc5f28b&amp;flashvars[playlistAPI.kpl0Id]=1_jaqg1zqt&amp;flashvars[playlistAPI.autoContinue]=true&amp;flashvars[playlistAPI.autoInsert]=true&amp;flashvars[ks]=&amp;flashvars[streamerType]=auto&amp;flashvars[localizationCode]=en\" webkitallowfullscreen=\"\" width=\"600\"></iframe></p>  <p><iframe allowfullscreen=\"\" frameborder=\"0\" height=\"400\" mozallowfullscreen=\"\" src=\"https://cdnapisec.kaltura.com/p/1842011/sp/184201100/embedIframeJs/uiconf_id/27244492/partner_id/1842011/widget_id/1_uphi82ds?iframeembed=true&amp;playerId=kaltura_player_57eb69e866848&amp;flashvars[playlistAPI.kpl0Id]=1_jaqg1zqt&amp;flashvars[playlistAPI.autoContinue]=true&amp;flashvars[playlistAPI.autoInsert]=true&amp;flashvars[ks]=&amp;flashvars[streamerType]=auto&amp;flashvars[localizationCode]=en\" webkitallowfullscreen=\"\" width=\"900\"></iframe></p>");

        given(kalturaPlaylistsHolder.playlistIdForReferenceId("1_jaqg1zqt")).willReturn("new_playlist_id");

        given(migrationConfiguration.oldEmbeddedUrl()).willReturn("cdnapisec.kaltura.com");

        given(migrationConfiguration.newEmbeddedUrl()).willReturn("new_embedd_url");

        given(migrationConfiguration.oldPartnerId()).willReturn("1842011");

        given(migrationConfiguration.newPartnerId()).willReturn("new_partner_id");

        given(playerIdMappingHolder.newPlayerIdFor("27244512")).willReturn("new_player_id");

        given(playerIdMappingHolder.newPlayerIdFor("27244492")).willReturn("new_player_id");

        given(migrationConfiguration.playlistKalturaPlayer()).willReturn("new_kaltura_player_id");

        embeddedIframePlaylistMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("<p><iframe allowfullscreen=\"\" frameborder=\"0\" height=\"620\" mozallowfullscreen=\"\" src=\"https://new_embedd_url/p/new_partner_id/sp/new_partner_id00/embedIframeJs/uiconf_id/new_player_id/partner_id/new_partner_id?iframeembed=true&amp;playerId=new_kaltura_player_id&amp;flashvars[playlistAPI.kpl0Id]=new_playlist_id&amp;flashvars[playlistAPI.autoContinue]=true&amp;flashvars[playlistAPI.autoInsert]=true&amp;flashvars[ks]=&amp;flashvars[streamerType]=auto&amp;flashvars[localizationCode]=en\" webkitallowfullscreen=\"\" width=\"600\"></iframe></p>  <p><iframe allowfullscreen=\"\" frameborder=\"0\" height=\"400\" mozallowfullscreen=\"\" src=\"https://new_embedd_url/p/new_partner_id/sp/new_partner_id00/embedIframeJs/uiconf_id/new_player_id/partner_id/new_partner_id?iframeembed=true&amp;playerId=new_kaltura_player_id&amp;flashvars[playlistAPI.kpl0Id]=new_playlist_id&amp;flashvars[playlistAPI.autoContinue]=true&amp;flashvars[playlistAPI.autoInsert]=true&amp;flashvars[ks]=&amp;flashvars[streamerType]=auto&amp;flashvars[localizationCode]=en\" webkitallowfullscreen=\"\" width=\"900\"></iframe></p>");


    }

}