package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import se.umu.its.cambro.kaltura.migration.MigrationConfiguration;
import se.umu.its.cambro.kaltura.migration.MigratorTest;
import se.umu.its.cambro.kaltura.migration.player.PlayerIdMappingHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

public class KalturaKafUriComMigratorTest extends MigratorTest {

    @Mock
    private MigrationConfiguration migrationConfiguration;

    @Mock
    private PlayerIdMappingHolder playerIdMappingHolder;

    @InjectMocks
    private KalturaKafUriComMigrator kalturaKafUriComMigrator;

    @Test
    public void shouldMigrateKalturaKafUri() {

        given(rowToUpdate.getValueToMigrate()).willReturn("http://kaltura-kaf-uri.com/browseandembed/index/media/entryid/0_b88fyjwc/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/608x402/playerSkin/36886252/");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("0_b88fyjwc")).willReturn("new_entry_id");

        given(playerIdMappingHolder.newPlayerIdFor("36886252")).willReturn("new_player_id");

        kalturaKafUriComMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("http://kaltura-kaf-uri.com/browseandembed/index/media/entryid/new_entry_id/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/608x402/playerSkin/new_player_id/");

    }
}