package se.umu.its.cambro.kaltura.migration.entry;

import org.junit.Test;
import org.mockito.InjectMocks;
import se.umu.its.cambro.kaltura.migration.MockitoTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class KalturaMediaEntriesHolderTest extends MockitoTest{

    @InjectMocks
    private KalturaMediaEntriesHolder kalturaMediaEntriesHolder;

    @Test
    public void shouldFetchMediaEntries() throws IOException {
        kalturaMediaEntriesHolder.filePath("src/test/resources/entries.csv");

        kalturaMediaEntriesHolder.init();

        assertThat(kalturaMediaEntriesHolder.existsNewMediaEntryWithId("0_8o4weqwm")).isTrue();
        assertThat(kalturaMediaEntriesHolder.existsNewMediaEntryWithId("0_kftfosdi")).isTrue();
        assertThat(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_prru36eh")).isEqualToIgnoringCase("0_8o4weqwm");
        assertThat(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("1_75f18q5d")).isEqualToIgnoringCase("0_kftfosdi");
        assertThat(kalturaMediaEntriesHolder.thumbnailUrlForReferenceId("1_prru36eh")).isEqualToIgnoringCase("https://streaming.kaltura.nordu.net/p/297/sp/29700/thumbnail/entry_id/0_8o4weqwm/version/100002");
        assertThat(kalturaMediaEntriesHolder.thumbnailUrlForReferenceId("1_75f18q5d")).isEqualToIgnoringCase("https://streaming.kaltura.nordu.net/p/297/sp/29700/thumbnail/entry_id/0_kftfosdi/version/100002");
    }
}
