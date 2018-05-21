package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.junit.Test;
import org.mockito.InjectMocks;
import se.umu.its.cambro.kaltura.migration.MigratorTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class EscapedPlayUmuEntryMigratorTest extends MigratorTest {

    @InjectMocks
    private EscapedPlayUmuEntryMigrator escapedPlayUmuEntryMigrator;

    @Test
    public void shouldMigrateEscapedPlayUmuLink() {

        given(rowToUpdate.getValueToMigrate()).willReturn("This text contains a link to https:\\/\\/play.umu.se\\/media\\/t\\/1_BCR12345 that should be migrated");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_BCR12345")).willReturn("1_BCR13456");

        escapedPlayUmuEntryMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("This text contains a link to https:\\/\\/play.umu.se\\/media\\/t\\/1_BCR13456 that should be migrated");

    }

}