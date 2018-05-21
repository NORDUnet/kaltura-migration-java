package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import se.umu.its.cambro.kaltura.migration.MigrationConfiguration;
import se.umu.its.cambro.kaltura.migration.MigratorTest;
import se.umu.its.cambro.kaltura.migration.player.PlayerIdMappingHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class KalturaLtiUrlMigratorTest extends MigratorTest {

    @Mock
    private MigrationConfiguration migrationConfiguration;

    @Mock
    private PlayerIdMappingHolder playerIdMappingHolder;

    @InjectMocks
    private KalturaLtiUrlMigrator kalturaLtiUrlMigrator;

    @Test
    public void shouldMigrateKalturaLtiUrl() {

        given(rowToUpdate.getValueToMigrate()).willReturn("text before kaltura-lti-url=\"https://kaf.cambro.umu.se/browseandembed/index/media/entryid/0_06x6ry3l/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/608x402/playerSkin/33436041/\"");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("0_06x6ry3l")).willReturn("new_entry_id");

        given(playerIdMappingHolder.newPlayerIdFor("33436041")).willReturn("new_player_id");

        given(migrationConfiguration.oldKalturaUrl()).willReturn("kaf.cambro.umu.se");

        given(migrationConfiguration.newKalturaUrl()).willReturn("new_kaltura_url");

        kalturaLtiUrlMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("text before kaltura-lti-url=\"https://new_kaltura_url/browseandembed/index/media/entryid/new_entry_id/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/608x402/playerSkin/new_player_id/\"");
    }
}