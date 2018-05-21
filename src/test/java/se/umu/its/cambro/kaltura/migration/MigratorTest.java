package se.umu.its.cambro.kaltura.migration;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import se.umu.its.cambro.kaltura.migration.entry.KalturaMediaEntriesHolder;
import se.umu.its.cambro.kaltura.migration.playlist.KalturaPlaylistsHolder;
import se.umu.its.cambro.kaltura.migration.MockitoTest;
import se.umu.its.cambro.kaltura.migration.RowToUpdate;

public abstract class MigratorTest extends MockitoTest {

    @Mock
    protected RowToUpdate rowToUpdate;

    @Mock
    protected KalturaMediaEntriesHolder kalturaMediaEntriesHolder;

    @Mock
    protected KalturaPlaylistsHolder kalturaPlaylistsHolder;

    @Captor
    protected ArgumentCaptor<String> stringArgumentCaptor;

}
