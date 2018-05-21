package se.umu.its.cambro.kaltura.migration.entry.migrators;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import se.umu.its.cambro.kaltura.migration.MigrationConfiguration;
import se.umu.its.cambro.kaltura.migration.MigratorTest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

public class ThumbnailEntryMigratorTest extends MigratorTest {

    @Mock
    private MigrationConfiguration migrationConfiguration;

    @InjectMocks
    private ThumbnailEntryMigrator thumbnailMigrator;

    @Test
    public void shouldMigrateSrcThumbnail() {

        given(rowToUpdate.getValueToMigrate()).willReturn("<p>En film om de sista bilderna med exempel från vetenskapliga artiklar</p>  <p><span class=\"kaltura-lti-media\"><img height=\"402\" kaltura-lti-url=\"https://oldurl/browseandembed/index/media/entryid/0_whd8xn4d/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/608x402/playerSkin/33436041/\" src=\"https://oldthumbnailurl/p/1842011/sp/184201100/thumbnail/entry_id/0_whd8xn4d/version/0/acv/12\" title=\"IFrame\" width=\"608\" /></span></p>");

        given(kalturaMediaEntriesHolder.thumbnailUrlForReferenceId("0_whd8xn4d")).willReturn("new_thumbnail_url");

        given(migrationConfiguration.oldThumbnailUrl()).willReturn("oldthumbnailurl");

        thumbnailMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("<p>En film om de sista bilderna med exempel från vetenskapliga artiklar</p>  <p><span class=\"kaltura-lti-media\"><img height=\"402\" kaltura-lti-url=\"https://oldurl/browseandembed/index/media/entryid/0_whd8xn4d/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/608x402/playerSkin/33436041/\" src=\"new_thumbnail_url\" title=\"IFrame\" width=\"608\" /></span></p>");

    }

    @Test
    public void shouldMigrateContentThumbnail() {

        given(rowToUpdate.getValueToMigrate()).willReturn("<p>En film om de sista bilderna med exempel från vetenskapliga artiklar</p>  <p><span class=\"kaltura-lti-media\"><img height=\"402\" kaltura-lti-url=\"https://oldurl/browseandembed/index/media/entryid/0_whd8xn4d/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/608x402/playerSkin/33436041/\" content=\"http://oldthumbnailurl/p/1842011/sp/184201100/thumbnail/entry_id/0_whd8xn4d/version/0/acv/31\" title=\"IFrame\" width=\"608\" /></span></p>");

        given(kalturaMediaEntriesHolder.thumbnailUrlForReferenceId("0_whd8xn4d")).willReturn("new_thumbnail_url");

        given(migrationConfiguration.oldThumbnailUrl()).willReturn("oldthumbnailurl");

        thumbnailMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("<p>En film om de sista bilderna med exempel från vetenskapliga artiklar</p>  <p><span class=\"kaltura-lti-media\"><img height=\"402\" kaltura-lti-url=\"https://oldurl/browseandembed/index/media/entryid/0_whd8xn4d/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/608x402/playerSkin/33436041/\" content=\"new_thumbnail_url\" title=\"IFrame\" width=\"608\" /></span></p>");

    }

    @Test
    public void shouldMigrateMultipleThumbnails() {

        given(rowToUpdate.getValueToMigrate()).willReturn("content=\"http://oldthumbnailurl/p/1842011/sp/184201100/thumbnail/entry_id/0_whd8xn4d/version/0/acv/31\" title=\"IFrame\" width=\"608\" /> content=\"http://oldthumbnailurl/p/1842011/sp/184201100/thumbnail/entry_id/0_whd8xn4e/version/0/acv/31\" title=\"IFrame\" width=\"608\" />");

        given(kalturaMediaEntriesHolder.thumbnailUrlForReferenceId("0_whd8xn4d")).willReturn("new_thumbnail_url1");

        given(kalturaMediaEntriesHolder.thumbnailUrlForReferenceId("0_whd8xn4e")).willReturn("new_thumbnail_url2");

        given(migrationConfiguration.oldThumbnailUrl()).willReturn("oldthumbnailurl");

        thumbnailMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("content=\"new_thumbnail_url1\" title=\"IFrame\" width=\"608\" /> content=\"new_thumbnail_url2\" title=\"IFrame\" width=\"608\" />");

    }

    @Test
    public void shouldMigrateThumbnail() {

        given(rowToUpdate.getValueToMigrate()).willReturn("<h1>Mineraler</h1>  <p><img alt=\"\" src=\"/access/meleteDocs/content/private/meleteDocs/72807VT16-1/uploads/granit1.jpg\" style=\"width: 166.0px;height: 150.0px;float: right;\" /><a href=\"http://www.ptable.com/?lang=sv\" target=\"_blank\"><img alt=\"\" src=\"/access/meleteDocs/content/private/meleteDocs/72807VT16-1/uploads/Periodiska_systemet.png\" style=\"width: 266.0px;height: 150.0px;float: right;\" /></a></p>  <p align=\"left\"><em><font color=\"#808080\">Mineraler - Icke organiska grundämnen som har liknande struktur och kristalliknande utseende. Mineraler har många viktiga funktioner i kroppen och ingår t ex i många enzymer där de ofta har en helt avgörande roll. Därför kan också brist av en enskild mineral få allvarliga konsekvenser, även om sådan bristsymptom är ovanliga i vår del av världen, med undantag för vid vissa sjukdomstillstånd. Eftersom kroppens behov av olika mineraler ofta skiljer sig åt nämnvärt delar man ofta in mineraler i makro- (behov över 100 mg per dag) respektive mikroelement (behov på max några få mg. Mikroelement kallas ofta även för spårämnen.</font></em></p>  <div> <h3>Sidhänvisningar Mineraler</h3>  <ul>  <li>Näring och hälsa:  <ul>   <li>Sid 172-173 <strong>Inledning</strong></li>   <li>Sid 174-177 <strong>Kalcium</strong></li>   <li>Sid 182-185 <strong>Natrium</strong></li>   <li>Sid 188-192 <strong>Järn</strong></li>  </ul>  </li> </ul>  <h3>Extra läsning ( ej obligatorisk)</h3>  <ul>  <li>Livsmedelsverkets hemsida om <a href=\"http://www.livsmedelsverket.se/livsmedel-och-innehall/naringsamne/salt-och-mineraler1/\" target=\"_blank\">salt och mineraler inkl. underkapitel</a> (ej obligatorisk, men en bra grund för att lättare ta till sig kurslitteraturen)</li> </ul>  <h3>Instuderingsfrågor Mineraler</h3>  <p>Finner ni <a href=\"/access/meleteDocs/content/private/meleteDocs/72807VT16-1/uploads/Instuderingsfr%C3%A5gor%20Mineraler%20aug%202015.pdf\" target=\"_blank\">här</a></p>  <h3>Inspelade föreläsningar Mineraler (1st)</h3>  <p>OBS! De inspelade föreläsningarna är tänkta som ett stöd till egna studier med kurslitteraturen och är därmed inte heltäckande.</p> </div>  <p><span class=\"kaltura-lti-media\"><img height=\"285\" kaltura-lti-url=\"https://1842011.kaf.kaltura.com/browseandembed/index/media/entryid/0_v10x0qki/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/400x285/playerSkin/33436041/\" src=\"https://oldthumbnailurl/p/1842011/sp/184201100/thumbnail/entry_id/0_v10x0qki/version/100001\" title=\"IFrame\" width=\"400\" /></span></p> ");

        given(kalturaMediaEntriesHolder.thumbnailUrlForReferenceId("0_v10x0qki")).willReturn("new_thumbnail_url");

        given(migrationConfiguration.oldThumbnailUrl()).willReturn("oldthumbnailurl");

        thumbnailMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("<h1>Mineraler</h1>  <p><img alt=\"\" src=\"/access/meleteDocs/content/private/meleteDocs/72807VT16-1/uploads/granit1.jpg\" style=\"width: 166.0px;height: 150.0px;float: right;\" /><a href=\"http://www.ptable.com/?lang=sv\" target=\"_blank\"><img alt=\"\" src=\"/access/meleteDocs/content/private/meleteDocs/72807VT16-1/uploads/Periodiska_systemet.png\" style=\"width: 266.0px;height: 150.0px;float: right;\" /></a></p>  <p align=\"left\"><em><font color=\"#808080\">Mineraler - Icke organiska grundämnen som har liknande struktur och kristalliknande utseende. Mineraler har många viktiga funktioner i kroppen och ingår t ex i många enzymer där de ofta har en helt avgörande roll. Därför kan också brist av en enskild mineral få allvarliga konsekvenser, även om sådan bristsymptom är ovanliga i vår del av världen, med undantag för vid vissa sjukdomstillstånd. Eftersom kroppens behov av olika mineraler ofta skiljer sig åt nämnvärt delar man ofta in mineraler i makro- (behov över 100 mg per dag) respektive mikroelement (behov på max några få mg. Mikroelement kallas ofta även för spårämnen.</font></em></p>  <div> <h3>Sidhänvisningar Mineraler</h3>  <ul>  <li>Näring och hälsa:  <ul>   <li>Sid 172-173 <strong>Inledning</strong></li>   <li>Sid 174-177 <strong>Kalcium</strong></li>   <li>Sid 182-185 <strong>Natrium</strong></li>   <li>Sid 188-192 <strong>Järn</strong></li>  </ul>  </li> </ul>  <h3>Extra läsning ( ej obligatorisk)</h3>  <ul>  <li>Livsmedelsverkets hemsida om <a href=\"http://www.livsmedelsverket.se/livsmedel-och-innehall/naringsamne/salt-och-mineraler1/\" target=\"_blank\">salt och mineraler inkl. underkapitel</a> (ej obligatorisk, men en bra grund för att lättare ta till sig kurslitteraturen)</li> </ul>  <h3>Instuderingsfrågor Mineraler</h3>  <p>Finner ni <a href=\"/access/meleteDocs/content/private/meleteDocs/72807VT16-1/uploads/Instuderingsfr%C3%A5gor%20Mineraler%20aug%202015.pdf\" target=\"_blank\">här</a></p>  <h3>Inspelade föreläsningar Mineraler (1st)</h3>  <p>OBS! De inspelade föreläsningarna är tänkta som ett stöd till egna studier med kurslitteraturen och är därmed inte heltäckande.</p> </div>  <p><span class=\"kaltura-lti-media\"><img height=\"285\" kaltura-lti-url=\"https://1842011.kaf.kaltura.com/browseandembed/index/media/entryid/0_v10x0qki/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/400x285/playerSkin/33436041/\" src=\"new_thumbnail_url\" title=\"IFrame\" width=\"400\" /></span></p> ");

    }

    @Test
    public void shouldMigrateKalturaMediaThumbnail() {

        given(rowToUpdate.getValueToMigrate()).willReturn("src=\"https://oldthumbnailurl/p/1842011/sp/184201100/thumbnail/entry_id/1_14xgtmdk/version/100000/acv/111\"");

        given(kalturaMediaEntriesHolder.thumbnailUrlForReferenceId("1_14xgtmdk")).willReturn("new_thumbnail_url");

        given(migrationConfiguration.oldThumbnailUrl()).willReturn("oldthumbnailurl");

        thumbnailMigrator.migrate(rowToUpdate);

        verify(rowToUpdate).columnMigratedValue(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isNotNull();

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("src=\"new_thumbnail_url\"");


    }
}