package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import se.umu.its.cambro.kaltura.migration.MigrationConfiguration;
import se.umu.its.cambro.kaltura.migration.MigratorTest;
import se.umu.its.cambro.kaltura.migration.player.PlayerIdMappingHolder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

public class EscapedEmbeddedIframeEntryMigratorTest extends MigratorTest {

    @Mock
    private MigrationConfiguration migrationConfiguration;

    @Mock
    private PlayerIdMappingHolder playerIdMappingHolder;

    @InjectMocks
    private EscapedEmbeddedIframeEntryMigrator escapedEmbeddedIframeEntryMigrator;

    @Test
    public void shouldMigrateEscapedEmbeddedIframe(){

        given(rowToUpdate.getValueToMigrate()).willReturn("{\"multimediaDisplayType\":\"1\",\"addedby\":\"f6ebb6e6-be64-44a8-a338-77702b9e8914\",\"multimediaEmbedCode\":\"<iframe id=\\\"kaltura_player\\\" src=\\\"https:\\/\\/cdnapisec.kaltura.com\\/p\\/1842011\\/sp\\/184201100\\/embedIframeJs\\/uiconf_id\\/27244472\\/partner_id\\/1842011?iframeembed=true&amp;playerId=kaltura_player&amp;entry_id=1_4t0kotc0&amp;flashvars[streamerType]=auto&amp;flashvars[leadWithHTML5]=true&amp;flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;flashvars[dualScreen.plugin]=true&amp;&amp;wid=1_94uzrlrx\\\" width=\\\"400\\\" height=\\\"285\\\" allowfullscreen=\\\"\\\" webkitallowfullscreen=\\\"\\\" mozallowfullscreen=\\\"\\\" frameborder=\\\"0\\\"><\\/iframe>\"}");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_4t0kotc0")).willReturn("new_entry_id");

        given(migrationConfiguration.oldEmbeddedUrl()).willReturn("cdnapisec.kaltura.com");

        given(migrationConfiguration.newEmbeddedUrl()).willReturn("new_embedd_url");

        given(migrationConfiguration.oldPartnerId()).willReturn("1842011");

        given(migrationConfiguration.newPartnerId()).willReturn("new_partner_id");

        given(playerIdMappingHolder.newPlayerIdFor("27244472")).willReturn("new_player_id");

        escapedEmbeddedIframeEntryMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("{\"multimediaDisplayType\":\"1\",\"addedby\":\"f6ebb6e6-be64-44a8-a338-77702b9e8914\",\"multimediaEmbedCode\":\"<iframe id=\\\"kaltura_player\\\" src=\\\"https:\\/\\/new_embedd_url\\/p\\/new_partner_id\\/sp\\/new_partner_id00\\/embedIframeJs\\/uiconf_id\\/new_player_id\\/partner_id\\/new_partner_id?iframeembed=true&amp;playerId=kaltura_player&amp;entry_id=new_entry_id&amp;flashvars[streamerType]=auto&amp;flashvars[leadWithHTML5]=true&amp;flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;flashvars[dualScreen.plugin]=true\\\" width=\\\"400\\\" height=\\\"285\\\" allowfullscreen=\\\"\\\" webkitallowfullscreen=\\\"\\\" mozallowfullscreen=\\\"\\\" frameborder=\\\"0\\\"><\\/iframe>\"}");


    }

}