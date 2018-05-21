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


public class MediaItemUrlMigratorTest extends MigratorTest {

    @Mock
    private MigrationConfiguration migrationConfiguration;

    @Mock
    private PlayerIdMappingHolder playerIdMappingHolder;

    @InjectMocks
    private MediaItemUrlMigrator mediaItemUrlMigrator;

    @Test
    public void shouldMigrateMediaItemUrl() {

        given(rowToUpdate.getValueToMigrate()).willReturn("src=\"https://www.cambro.umu.se/media-gallery-tool/mediadisplay.htm?mediaitemurl=https%3A%2F%2F1842011.kaf.kaltura.com%2Fbrowseandembed%2Findex%2Fmedia%2Fentryid%2F1_8mut6ldp%2FshowDescription%2Ffalse%2FshowTitle%2Ffalse%2FshowTags%2Ffalse%2FshowDuration%2Ffalse%2FshowOwner%2Ffalse%2FshowUploadDate%2Ffalse%2FplayerSize%2F400x285%2FplayerSkin%2F33436041%2F&amp;siteid=72807VT18-1&amp;userid=4bc04a5c-e2b4-4fcb-ad8b-4a75765b0193\"");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_8mut6ldp")).willReturn("new_entry_id");

        given(playerIdMappingHolder.newPlayerIdFor("33436041")).willReturn("new_player_id");

        given(migrationConfiguration.oldKalturaUrl()).willReturn("1842011.kaf.kaltura.com");

        given(migrationConfiguration.newKalturaUrl()).willReturn("new_kaltura_url");

        mediaItemUrlMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("src=\"https://www.cambro.umu.se/media-gallery-tool/mediadisplay.htm?mediaitemurl=https%3A%2F%2Fnew_kaltura_url%2Fbrowseandembed%2Findex%2Fmedia%2Fentryid%2Fnew_entry_id%2FshowDescription%2Ffalse%2FshowTitle%2Ffalse%2FshowTags%2Ffalse%2FshowDuration%2Ffalse%2FshowOwner%2Ffalse%2FshowUploadDate%2Ffalse%2FplayerSize%2F400x285%2FplayerSkin%2Fnew_player_id%2F&amp;siteid=72807VT18-1&amp;userid=4bc04a5c-e2b4-4fcb-ad8b-4a75765b0193\"");

    }

}