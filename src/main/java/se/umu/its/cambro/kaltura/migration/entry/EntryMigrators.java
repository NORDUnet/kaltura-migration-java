package se.umu.its.cambro.kaltura.migration.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.umu.its.cambro.kaltura.migration.MigrationConfiguration;
import se.umu.its.cambro.kaltura.migration.entry.migrators.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class EntryMigrators {

    @Autowired
    private EmbeddedIframeEntryMigrator embeddedIframeEntryMigrator;

    @Autowired
    private EscapedEmbeddedIframeEntryMigrator escapedEmbeddedIframeEntryMigrator;

    @Autowired
    private KafUrlMigrator kafUrlMigrator;

    @Autowired
    private KalturaLtiUrlMigrator kalturaLtiUrlMigrator;

    @Autowired
    private KalturaMediaMigrator kalturaMediaMigrator;

    @Autowired
    private KwidgetUrlEntryMigrator kwidgetUrlEntryMigrator;

    @Autowired
    private MediaItemUrlMigrator mediaItemUrlMigrator;

    @Autowired
    private PlayUmuEntryMigrator playUmuEntryMigrator;

    @Autowired
    private EscapedPlayUmuEntryMigrator escapedPlayUmuEntryMigrator;

    @Autowired
    private ThumbnailEntryMigrator thumbnailEntryMigrator;

    @Autowired
    private KalturaKafUriComMigrator kalturaKafUriComMigrator;

    public List<EntryMigrator> migrators() {

        return Arrays.asList(
                kalturaKafUriComMigrator,
                kalturaLtiUrlMigrator,
                embeddedIframeEntryMigrator,
                escapedEmbeddedIframeEntryMigrator,
                kalturaMediaMigrator,
                thumbnailEntryMigrator,
                kwidgetUrlEntryMigrator,
                mediaItemUrlMigrator,
                playUmuEntryMigrator,
                escapedPlayUmuEntryMigrator,
                kafUrlMigrator
        );
    }
}