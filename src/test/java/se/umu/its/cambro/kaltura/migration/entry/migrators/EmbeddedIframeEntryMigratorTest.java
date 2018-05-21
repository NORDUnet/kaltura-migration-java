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

public class EmbeddedIframeEntryMigratorTest extends MigratorTest {

    @Mock
    private MigrationConfiguration migrationConfiguration;

    @Mock
    private PlayerIdMappingHolder playerIdMappingHolder;

    @InjectMocks
    private EmbeddedIframeEntryMigrator embeddedIframeMigrator;

    @Test
    public void shouldMigrateEmbeddedIframe() {

        given(rowToUpdate.getValueToMigrate()).willReturn("<iframe id=\"kaltura_player\" src=\"https://cdnapisec.kaltura.com/p/1842011/sp/184201100/embedIframeJs/uiconf_id/27244472/partner_id/1842011?iframeembed=true&amp;playerId=kaltura_player&amp;entry_id=0_hkbjldte&amp;flashvars[streamerType]=auto&amp;flashvars[leadWithHTML5]=true&amp;\n" +
                "         flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;\n" +
                "         flashvars[controlBarContainer.hover]=false&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;\n" +
                "         flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;\n" +
                "         flashvars[dualScreen.plugin]=true&amp;&amp;wid=1_9neoeljz\" width=\"400\" height=\"285\" allowfullscreen=\"\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" frameborder=\"0\"></iframe>");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("0_hkbjldte")).willReturn("new_entry_id");

        given(migrationConfiguration.oldEmbeddedUrl()).willReturn("cdnapisec.kaltura.com");

        given(migrationConfiguration.newEmbeddedUrl()).willReturn("new_embedd_url");

        given(migrationConfiguration.oldPartnerId()).willReturn("1842011");

        given(migrationConfiguration.newPartnerId()).willReturn("new_partner_id");

        given(playerIdMappingHolder.newPlayerIdFor("27244472")).willReturn("migrated_player_id");

        embeddedIframeMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("<iframe id=\"kaltura_player\" src=\"https://new_embedd_url/p/new_partner_id/sp/new_partner_id00/embedIframeJs/uiconf_id/migrated_player_id/partner_id/new_partner_id?iframeembed=true&amp;playerId=kaltura_player&amp;entry_id=new_entry_id&amp;flashvars[streamerType]=auto&amp;flashvars[leadWithHTML5]=true&amp;\n" +
                "         flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;\n" +
                "         flashvars[controlBarContainer.hover]=false&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;\n" +
                "         flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;\n" +
                "         flashvars[dualScreen.plugin]=true\" width=\"400\" height=\"285\" allowfullscreen=\"\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" frameborder=\"0\"></iframe>");

    }

    @Test
    public void shouldMigrateEmbeddedIframeDifferentOrderOfElements() {

        given(rowToUpdate.getValueToMigrate()).willReturn("<h3>Videoföreläsning</h3>  <p><iframe allowfullscreen=\"\" frameborder=\"0\" height=\"402\" id=\"kaltura_player\" mozallowfullscreen=\"\" src=\"https://cdnapisec.kaltura.com/p/1842011/sp/184201100/embedIframeJs/uiconf_id/26987861/partner_id/1842011?iframeembed=true&amp;playerId=kaltura_player&amp;entry_id=1_ds0j7d5h&amp;flashvars[streamerType]=auto&amp;flashvars[localizationCode]=en&amp;flashvars[leadWithHTML5]=true&amp;flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;flashvars[dualScreen.plugin]=true&amp;&amp;wid=1_c6nohuea\" webkitallowfullscreen=\"\" width=\"608\"></iframe></p> ");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_ds0j7d5h")).willReturn("new_entry_id");

        given(migrationConfiguration.oldEmbeddedUrl()).willReturn("cdnapisec.kaltura.com");

        given(migrationConfiguration.newEmbeddedUrl()).willReturn("new_embedd_url");

        given(migrationConfiguration.oldPartnerId()).willReturn("1842011");

        given(migrationConfiguration.newPartnerId()).willReturn("new_partner_id");

        given(playerIdMappingHolder.newPlayerIdFor("26987861")).willReturn("new_player_id");

        embeddedIframeMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("<h3>Videoföreläsning</h3>  <p><iframe allowfullscreen=\"\" frameborder=\"0\" height=\"402\" id=\"kaltura_player\" mozallowfullscreen=\"\" src=\"https://new_embedd_url/p/new_partner_id/sp/new_partner_id00/embedIframeJs/uiconf_id/new_player_id/partner_id/new_partner_id?iframeembed=true&amp;playerId=kaltura_player&amp;entry_id=new_entry_id&amp;flashvars[streamerType]=auto&amp;flashvars[localizationCode]=en&amp;flashvars[leadWithHTML5]=true&amp;flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;flashvars[dualScreen.plugin]=true\" webkitallowfullscreen=\"\" width=\"608\"></iframe></p> ");
    }

    @Test
    public void shouldMigrateMultipleIframes() {

        given(rowToUpdate.getValueToMigrate()).willReturn("<iframe id=\"kaltura_player\" src=\"https://cdnapisec.kaltura.com/p/1842011/sp/184201100/embedIframeJs/uiconf_id/26987861/partner_id/1842011?iframeembed=true&amp;playerId=kaltura_player&amp;entry_id=1_q249h3vl&amp;flashvars[streamerType]=auto&amp;flashvars[leadWithHTML5]=true&amp;flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;flashvars[dualScreen.plugin]=true&amp;&amp;wid=1_k3tjgl5h\" allowfullscreen=\"\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" frameborder=\"0\" height=\"402\" width=\"608\"></iframe>" +
                "<br><br><br>Pulsmätning<br><br><iframe id=\"kaltura_player\" src=\"https://cdnapisec.kaltura.com/p/1842011/sp/184201100/embedIframeJs/uiconf_id/26987861/partner_id/1842011?iframeembed=true&amp;playerId=kaltura_player&amp;entry_id=1_clkbbhmx&amp;flashvars[streamerType]=auto&amp;flashvars[leadWithHTML5]=true&amp;flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;flashvars[dualScreen.plugin]=true&amp;&amp;wid=1_ocbubwkk\" allowfullscreen=\"\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" frameborder=\"0\" height=\"402\" width=\"608\"></iframe>" +
                "<br><br><br>Ekblom-bak<br><br><iframe id=\"kaltura_player\" src=\"https://cdnapisec.kaltura.com/p/1842011/sp/184201100/embedIframeJs/uiconf_id/26987861/partner_id/1842011?iframeembed=true&amp;playerId=kaltura_player&amp;entry_id=1_l56o3iid&amp;flashvars[streamerType]=auto&amp;flashvars[leadWithHTML5]=true&amp;flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;flashvars[dualScreen.plugin]=true&amp;&amp;wid=1_6adu7ar0\" allowfullscreen=\"\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" frameborder=\"0\" height=\"402\" width=\"608\"></iframe>\n");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_q249h3vl")).willReturn("new_entry_id1");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_clkbbhmx")).willReturn("new_entry_id2");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_l56o3iid")).willReturn("new_entry_id3");

        given(migrationConfiguration.oldEmbeddedUrl()).willReturn("cdnapisec.kaltura.com");

        given(migrationConfiguration.newEmbeddedUrl()).willReturn("new_embedd_url");

        given(migrationConfiguration.oldPartnerId()).willReturn("1842011");

        given(migrationConfiguration.newPartnerId()).willReturn("new_partner_id");

        given(playerIdMappingHolder.newPlayerIdFor("26987861")).willReturn("new_player_id");

        embeddedIframeMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("<iframe id=\"kaltura_player\" src=\"https://new_embedd_url/p/new_partner_id/sp/new_partner_id00/embedIframeJs/uiconf_id/new_player_id/partner_id/new_partner_id?iframeembed=true&amp;playerId=kaltura_player&amp;entry_id=new_entry_id1&amp;flashvars[streamerType]=auto&amp;flashvars[leadWithHTML5]=true&amp;flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;flashvars[dualScreen.plugin]=true\" allowfullscreen=\"\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" frameborder=\"0\" height=\"402\" width=\"608\"></iframe>" +
                "<br><br><br>Pulsmätning<br><br><iframe id=\"kaltura_player\" src=\"https://new_embedd_url/p/new_partner_id/sp/new_partner_id00/embedIframeJs/uiconf_id/new_player_id/partner_id/new_partner_id?iframeembed=true&amp;playerId=kaltura_player&amp;entry_id=new_entry_id2&amp;flashvars[streamerType]=auto&amp;flashvars[leadWithHTML5]=true&amp;flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;flashvars[dualScreen.plugin]=true\" allowfullscreen=\"\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" frameborder=\"0\" height=\"402\" width=\"608\"></iframe>" +
                "<br><br><br>Ekblom-bak<br><br><iframe id=\"kaltura_player\" src=\"https://new_embedd_url/p/new_partner_id/sp/new_partner_id00/embedIframeJs/uiconf_id/new_player_id/partner_id/new_partner_id?iframeembed=true&amp;playerId=kaltura_player&amp;entry_id=new_entry_id3&amp;flashvars[streamerType]=auto&amp;flashvars[leadWithHTML5]=true&amp;flashvars[sideBarContainer.plugin]=true&amp;flashvars[sideBarContainer.position]=left&amp;flashvars[sideBarContainer.clickToClose]=true&amp;flashvars[chapters.plugin]=true&amp;flashvars[chapters.layout]=vertical&amp;flashvars[chapters.thumbnailRotator]=false&amp;flashvars[streamSelector.plugin]=true&amp;flashvars[EmbedPlayer.SpinnerTarget]=videoHolder&amp;flashvars[dualScreen.plugin]=true\" allowfullscreen=\"\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" frameborder=\"0\" height=\"402\" width=\"608\"></iframe>\n");
    }
}