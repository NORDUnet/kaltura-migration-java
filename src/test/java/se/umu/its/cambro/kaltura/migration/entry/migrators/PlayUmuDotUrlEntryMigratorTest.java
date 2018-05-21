package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.junit.Test;
import org.mockito.InjectMocks;
import se.umu.its.cambro.kaltura.migration.MigratorTest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

public class PlayUmuDotUrlEntryMigratorTest extends MigratorTest {

    @InjectMocks
    private PlayUmuDotUrlEntryMigrator playUmuDotUrlMigrator;


    @Test
    public void shouldMigrateUmuPlayDotUrl() {

        given(rowToUpdate.getValueToMigrate()).willReturn("/content/group/34000VT17-1/Lessonbuilder/Examination L-ABCDE/urls/play.umu.se_media_t_1_t5cw5r4e.url");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_t5cw5r4e")).willReturn("2_t5cw5r4e");

        playUmuDotUrlMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("/content/group/34000VT17-1/Lessonbuilder/Examination L-ABCDE/urls/play.umu.se_media_t_2_t5cw5r4e.url");

    }
}