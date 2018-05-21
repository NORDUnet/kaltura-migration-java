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

public class KwidgetUrlEntryMigratorTest extends MigratorTest {

    @Mock
    private MigrationConfiguration migrationConfiguration;

    @Mock
    private PlayerIdMappingHolder playerIdMappingHolder;

    @InjectMocks
    private KwidgetUrlEntryMigrator kwidgetUrlMigrator;

    @Test
    public void shouldMigrateKwidgetUrl() {

        given(rowToUpdate.getValueToMigrate()).willReturn("https://www.kaltura.com/kwidget/wid/_1842011/ui_conf_id/7473521/entryId/0_59ygx6rr");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("0_59ygx6rr")).willReturn("0_59ygx611");

        given(migrationConfiguration.newKalturaUrl()).willReturn("newurl");

        given(migrationConfiguration.oldPartnerId()).willReturn("1842011");

        given(migrationConfiguration.newPartnerId()).willReturn("new_partner_id");

        given(playerIdMappingHolder.newPlayerIdFor("7473521")).willReturn("new_player_id");

        kwidgetUrlMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("https://newurl/kwidget/wid/_new_partner_id/ui_conf_id/new_player_id/entryId/0_59ygx611");
    }
}