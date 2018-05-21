package se.umu.its.cambro.kaltura.migration;


import de.ailis.pherialize.MixedArray;
import de.ailis.pherialize.Pherialize;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import se.umu.its.cambro.kaltura.migration.entry.KalturaMediaEntriesHolder;

import java.util.Base64;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

public class MdlTableRowMigratorTest extends MockitoTest {

    @Mock
    private MigrationConfiguration migrationConfiguration;

    @Mock
    private KalturaMediaEntriesHolder kalturaMediaEntriesHolder;

    @InjectMocks
    private MdlTableRowMigrator mdlTableRowMigrator;

    private MdlTableRowHolder mdlTableRowHolder;

    @Before
    public void before() {


        mdlTableRowHolder = new MdlTableRowHolder();

        mdlTableRowHolder.mdlTable(MdlTableRowHolder.MdlTable.MDL_KALVIDRES);

        MdlTableRow mdlTableRow = new MdlTableRow();

        mdlTableRow.entryId("entryId");

        mdlTableRow.source("http://kaltura-kaf-uri.com/browseandembed/index/media/entryid/entryId/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/608x402/playerSkin/36886252/");

        mdlTableRow.metadata("Tzo4OiJzdGRDbGFzcyI6MTc6e3M6MzoidXJsIjtzOjIyODoiaHR0cHM6Ly8xODQyMDExLTEua2FmLmthbHR1cmEuY29tL2Jyb3dzZWFuZGVtYmVkL2luZGV4L21lZGlhL2VudHJ5aWQvMV9lMTJyYXkxcy9zaG93RGVzY3JpcHRpb24vZmFsc2Uvc2hvd1RpdGxlL2ZhbHNlL3Nob3dUYWdzL2ZhbHNlL3Nob3dEdXJhdGlvbi9mYWxzZS9zaG93T3duZXIvZmFsc2Uvc2hvd1VwbG9hZERhdGUvZmFsc2UvcGxheWVyU2l6ZS82MDh4NDAyL3BsYXllclNraW4vMzY4ODYyNTIvIjtzOjU6IndpZHRoIjtpOjYwODtzOjY6ImhlaWdodCI7aTo0MDI7czo3OiJlbnRyeWlkIjtzOjEwOiIxX2UxMnJheTFzIjtzOjU6InRpdGxlIjtzOjIzOiJCdWdnIGkgVW1VIFBsYXkgKDAxOjMwKSI7czoxMjoidGh1bWJuYWlsdXJsIjtzOjk4OiJodHRwczovL2NkbnNlY2FrbWkua2FsdHVyYS5jb20vcC8xODQyMDExL3NwLzE4NDIwMTEwMC90aHVtYm5haWwvZW50cnlfaWQvMV9lMTJyYXkxcy92ZXJzaW9uLzEwMDAxMSI7czo4OiJkdXJhdGlvbiI7czo1OiIwMTozMCI7czoxMToiZGVzY3JpcHRpb24iO3M6MTI6IkJ1Z2ctcmFwcG9ydCI7czo5OiJjcmVhdGVkYXQiO3M6MTA6IjE0Nzc1NzExMTEiO3M6NToib3duZXIiO3M6MTU6Impvbml2bjg2QHVtdS5zZSI7czo0OiJ0YWdzIjtzOjA6IiI7czo5OiJzaG93dGl0bGUiO3M6MDoiIjtzOjE1OiJzaG93ZGVzY3JpcHRpb24iO3M6MDoiIjtzOjEyOiJzaG93ZHVyYXRpb24iO3M6MDoiIjtzOjk6InNob3dvd25lciI7czowOiIiO3M6NjoicGxheWVyIjtzOjA6IiI7czo0OiJzaXplIjtzOjc6IjYwOHg0MDIiO30=");

        mdlTableRowHolder.rowToMigrate(mdlTableRow);

        given(kalturaMediaEntriesHolder.mediaEntryIdForReferenceId("entryId")).willReturn("new_entry_id");

        given(kalturaMediaEntriesHolder.thumbnailUrlForReferenceId("entryId")).willReturn("thumbnail_url");

        given(migrationConfiguration.playerId()).willReturn("111111111");
    }

    @Test
    public void shouldMigrateEntryId() {

        MdlTableRowHolder migrated = mdlTableRowMigrator.migrate(mdlTableRowHolder);

        assertThat(migrated.migratedRow().entryId()).isEqualToIgnoringCase("new_entry_id");
    }

    @Test
    public void shouldMigrateUiConfId() {

        MdlTableRowHolder migrated = mdlTableRowMigrator.migrate(mdlTableRowHolder);

        assertThat(migrated.migratedRow().uiconfId()).isEqualTo("111111111");
    }

    @Test
    public void shouldMigrateSource() {

        MdlTableRowHolder migrated = mdlTableRowMigrator.migrate(mdlTableRowHolder);

        assertThat(migrated.migratedRow().source()).isEqualToIgnoringCase("http://kaltura-kaf-uri.com/browseandembed/index/media/entryid/new_entry_id/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/608x402/playerSkin/111111111/");
    }

    @Test
    public void shouldMigrateMetadata() {

        MdlTableRowHolder migrated = mdlTableRowMigrator.migrate(mdlTableRowHolder);

        String metadata = migrated.migratedRow().metadata();

        String decodedMetadata = new String(Base64.getDecoder().decode(metadata));

        MixedArray mixedArray = Pherialize.unserialize(decodedMetadata).toArray();

        assertThat(mixedArray.getString("url")).isEqualToIgnoringCase("http://kaltura-kaf-uri.com/browseandembed/index/media/entryid/new_entry_id/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/608x402/playerSkin/111111111/");

        assertThat(mixedArray.getString("entryid")).isEqualToIgnoringCase("new_entry_id");

        assertThat(mixedArray.getString("thumbnailurl")).isEqualToIgnoringCase("thumbnail_url");
    }
}