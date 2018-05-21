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

public class MoodleUrlPlaylistMigratorTest extends MigratorTest{

    @Mock
    private MigrationConfiguration migrationConfiguration;

    @Mock
    private PlayerIdMappingHolder playerIdMappingHolder;

    @InjectMocks
    private MoodleUrlPlaylistMigrator moodleUrlPlaylistMigrator;

    @Test
    public void shouldMigrateMoodleUrl() {

        given(rowToUpdate.getValueToMigrate()).willReturn("https://cdnapisec.kaltura.com/p/1842011/sp/184201100/embedIframeJs/uiconf_id/27244492/partner_id/1842011/widget_id/1_2iyyl8m1?iframeembed=true&playerId=kaltura_player_5a65c1169e241&flashvars[playlistAPI.kpl0Id]=1_shms2sl0&flashvars[playlistAPI.autoContinue]=true&flashvars[playlistAPI.autoInsert]=true&flashvars[ks]=&flashvars[streamerType]=auto&flashvars[localizationCode]=en\" width=\"740\" height=\"330\" allowfullscreen webkitallowfullscreen mozAllowFullScreen frameborder=\"0\" title=\"Kaltura Player\">");

        given(kalturaPlaylistsHolder.playlistIdForReferenceId("1_shms2sl0")).willReturn("new_playlist_id");

        given(migrationConfiguration.oldEmbeddedUrl()).willReturn("cdnapisec.kaltura.com");

        given(migrationConfiguration.newEmbeddedUrl()).willReturn("new_embedd_url");

        given(migrationConfiguration.oldPartnerId()).willReturn("1842011");

        given(migrationConfiguration.newPartnerId()).willReturn("new_partner_id");

        given(playerIdMappingHolder.newPlayerIdFor("27244492")).willReturn("new_player_id");

        given(migrationConfiguration.playlistKalturaPlayer()).willReturn("new_kaltura_player_id");

        moodleUrlPlaylistMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("https://new_embedd_url/p/new_partner_id/sp/new_partner_id00/embedIframeJs/uiconf_id/new_player_id/partner_id/new_partner_id?iframeembed=true&playerId=new_kaltura_player_id&flashvars[playlistAPI.kpl0Id]=new_playlist_id&flashvars[playlistAPI.autoContinue]=true&flashvars[playlistAPI.autoInsert]=true&flashvars[ks]=&flashvars[streamerType]=auto&flashvars[localizationCode]=en\" width=\"740\" height=\"330\" allowfullscreen webkitallowfullscreen mozAllowFullScreen frameborder=\"0\" title=\"Kaltura Player\">");
    }

    @Test
    public void shouldNotMigrateMoodleUrl() {

        given(rowToUpdate.getValueToMigrate()).willReturn("asdfadsfdsafdsafdsafdsafdsafhttps://cdnapisec.kaltura.com/p/1842011/sp/184201100/embedIframeJs/uiconf_id/27244492/partner_id/1842011/widget_id/1_2iyyl8m1?iframeembed=true&playerId=kaltura_player_5a65c1169e241&flashvars[playlistAPI.kpl0Id]=1_shms2sl0&flashvars[playlistAPI.autoContinue]=true&flashvars[playlistAPI.autoInsert]=true&flashvars[ks]=&flashvars[streamerType]=auto&flashvars[localizationCode]=en\" width=\"740\" height=\"330\" allowfullscreen webkitallowfullscreen mozAllowFullScreen frameborder=\"0\" title=\"Kaltura Player\">");

        moodleUrlPlaylistMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("asdfadsfdsafdsafdsafdsafdsafhttps://cdnapisec.kaltura.com/p/1842011/sp/184201100/embedIframeJs/uiconf_id/27244492/partner_id/1842011/widget_id/1_2iyyl8m1?iframeembed=true&playerId=kaltura_player_5a65c1169e241&flashvars[playlistAPI.kpl0Id]=1_shms2sl0&flashvars[playlistAPI.autoContinue]=true&flashvars[playlistAPI.autoInsert]=true&flashvars[ks]=&flashvars[streamerType]=auto&flashvars[localizationCode]=en\" width=\"740\" height=\"330\" allowfullscreen webkitallowfullscreen mozAllowFullScreen frameborder=\"0\" title=\"Kaltura Player\">");
    }

}