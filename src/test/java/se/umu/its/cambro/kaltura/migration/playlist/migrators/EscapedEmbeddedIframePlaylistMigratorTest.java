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

public class EscapedEmbeddedIframePlaylistMigratorTest extends MigratorTest {

    @Mock
    private MigrationConfiguration migrationConfiguration;

    @Mock
    private PlayerIdMappingHolder playerIdMappingHolder;

    @InjectMocks
    private EscapedEmbeddedIframePlaylistMigrator escapedEmbeddedIframePlaylistMigrator;

    @Test
    public void shouldMigrateEscapedIframePlaylist() {

        given(rowToUpdate.getValueToMigrate()).willReturn("<iframe src=\\\"https:\\/\\/cdnapisec.kaltura.com\\/p\\/1842011\\/sp\\/184201100\\/embedIframeJs\\/uiconf_id\\/36911461\\/partner_id\\/1842011\\/widget_id\\/1_1k0k9rse?iframeembed=true&amp;playerId=kaltura_player_59f7299ff3fe6&amp;flashvars[playlistAPI.kpl0Id]=1_2zzq0lp2&amp;flashvars[playlistAPI.autoContinue]=true&amp;flashvars[playlistAPI.autoInsert]=true&amp;flashvars[ks]=&amp;flashvars[streamerType]=auto&amp;flashvars[localizationCode]=sv_SE&amp;flashvars[imageDefaultDuration]=30&amp;flashvars[leadWithHTML5]=true&amp;flashvars[nextPrevBtn.plugin]=true&amp;flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;flashvars[dualScreen.plugin]=true\\\" width=\\\"740\\\" height=\\\"330\\\" allowfullscreen=\\\"\\\" webkitallowfullscreen=\\\"\\\" mozallowfullscreen=\\\"\\\" frameborder=\\\"0\\\" title=\\\"Kaltura Player\\\"><\\/iframe>");

        given(kalturaPlaylistsHolder.playlistIdForReferenceId("1_2zzq0lp2")).willReturn("new_playlist_id");

        given(migrationConfiguration.oldEmbeddedUrl()).willReturn("cdnapisec.kaltura.com");

        given(migrationConfiguration.newEmbeddedUrl()).willReturn("new_embedd_url");

        given(migrationConfiguration.oldPartnerId()).willReturn("1842011");

        given(migrationConfiguration.newPartnerId()).willReturn("new_partner_id");

        given(playerIdMappingHolder.newPlayerIdFor("36911461")).willReturn("new_player_id");

        given(migrationConfiguration.playlistKalturaPlayer()).willReturn("new_playlist_kaltura_player");

        escapedEmbeddedIframePlaylistMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("<iframe src=\\\"https:\\/\\/new_embedd_url\\/p\\/new_partner_id\\/sp\\/new_partner_id00\\/embedIframeJs\\/uiconf_id\\/new_player_id\\/partner_id\\/new_partner_id?iframeembed=true&amp;playerId=new_playlist_kaltura_player&amp;flashvars[playlistAPI.kpl0Id]=new_playlist_id&amp;flashvars[playlistAPI.autoContinue]=true&amp;flashvars[playlistAPI.autoInsert]=true&amp;flashvars[ks]=&amp;flashvars[streamerType]=auto&amp;flashvars[localizationCode]=sv_SE&amp;flashvars[imageDefaultDuration]=30&amp;flashvars[leadWithHTML5]=true&amp;flashvars[nextPrevBtn.plugin]=true&amp;flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;flashvars[dualScreen.plugin]=true\\\" width=\\\"740\\\" height=\\\"330\\\" allowfullscreen=\\\"\\\" webkitallowfullscreen=\\\"\\\" mozallowfullscreen=\\\"\\\" frameborder=\\\"0\\\" title=\\\"Kaltura Player\\\"><\\/iframe>");


    }

}