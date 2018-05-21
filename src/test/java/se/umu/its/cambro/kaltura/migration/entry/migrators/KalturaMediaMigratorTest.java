package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.junit.Test;
import org.mockito.InjectMocks;
import se.umu.its.cambro.kaltura.migration.MigratorTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class KalturaMediaMigratorTest extends MigratorTest {

    @InjectMocks
    private KalturaMediaMigrator kalturaMediaMigrator;

    @Test
    public void shouldMigrateKalturaMedia(){

        given(rowToUpdate.getValueToMigrate()).willReturn("<span class=\"kaltura-media\" rel=\"1_14xgtmdk::video\">");

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_14xgtmdk")).willReturn("new_entry_id");

        kalturaMediaMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("<span class=\"kaltura-media\" rel=\"new_entry_id::video\">");
    }
}