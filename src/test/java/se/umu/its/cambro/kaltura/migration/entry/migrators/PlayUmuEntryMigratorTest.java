package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.junit.Test;
import org.mockito.InjectMocks;
import se.umu.its.cambro.kaltura.migration.MigratorTest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

public class PlayUmuEntryMigratorTest extends MigratorTest {

    @InjectMocks
    private PlayUmuEntryMigrator playUmuMigrator;

    @Test
    public void shouldMigrateUmuPlayLink() {

        given(rowToUpdate.getValueToMigrate()).willReturn("This text contains a link to play.umu.se/t/1_BCR12345 that should be migrated");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_BCR12345")).willReturn("1_BCR13456");

        playUmuMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("This text contains a link to play.umu.se/t/1_BCR13456 that should be migrated");

    }

    @Test
    public void shouldMigrateMultipleUmuPlayLinks() {

        given(rowToUpdate.getValueToMigrate()).willReturn("This text contains a link to play.umu.se/t/1_BCR12345 that should be migrated and also a second link play.umu.se/t/1_BCR21234 that should be migrated");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_BCR12345")).willReturn("1_BCR13456");
        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_BCR21234")).willReturn("1_BCR22345");

        playUmuMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("This text contains a link to play.umu.se/t/1_BCR13456 that should be migrated and also a second link play.umu.se/t/1_BCR22345 that should be migrated");
    }
}