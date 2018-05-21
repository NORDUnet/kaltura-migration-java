package se.umu.its.cambro.kaltura.migration;

import de.ailis.pherialize.MixedArray;
import de.ailis.pherialize.Pherialize;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.umu.its.cambro.kaltura.migration.entry.KalturaMediaEntriesHolder;

import java.math.BigInteger;
import java.util.Base64;

@Component
public class MdlTableRowMigrator {

    private static final Logger logger = LogManager.getLogger(MdlTableRowMigrator.class);

    @Autowired
    private MigrationConfiguration migrationConfiguration;

    @Autowired
    private KalturaMediaEntriesHolder kalturaMediaEntriesHolder;

    @Autowired
    private ResultPrinter resultPrinter;


    public MdlTableRowHolder migrate(MdlTableRowHolder mdlTableRowHolder) {

        String oldId = mdlTableRowHolder.rowToMigrate().entryId();

        if (oldId == null || oldId.isEmpty()) {
            return mdlTableRowHolder;
        }

        String newId;
        try {
            newId = kalturaMediaEntriesHolder.mediaEntryIdForReferenceId(oldId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultPrinter.addNotFoundId(oldId);
            mdlTableRowHolder.error(e.getMessage());
            return mdlTableRowHolder;
        }

        MdlTableRow mdlTableRow = new MdlTableRow();

        mdlTableRow.id(mdlTableRowHolder.rowToMigrate().id());
        mdlTableRow.entryId(newId);
        if (!mdlTableRowHolder.mdlTable().equals(MdlTableRowHolder.MdlTable.MDL_KALVIDASSIGN_SUBMISSION)) {
            mdlTableRow.uiconfId(new BigInteger(migrationConfiguration.playerId()));
        }
        mdlTableRow.source(mdlTableRowHolder.rowToMigrate().source().replaceAll(oldId, newId).replaceAll("playerSkin/\\d+", "playerSkin/" + migrationConfiguration.playerId()));

        try {
            mdlTableRow.metadata(migrateMetadata(mdlTableRowHolder.rowToMigrate().metadata(), mdlTableRow.source(), newId, kalturaMediaEntriesHolder.thumbnailUrlForReferenceId(oldId)));
        } catch (Exception e) {
            logger.error("Failed to migrate id " + mdlTableRowHolder.rowToMigrate().id() + " : " + e.getMessage(), e);
            mdlTableRowHolder.error(e.getMessage());
            return mdlTableRowHolder;
        }

        mdlTableRowHolder.migratedRow(mdlTableRow);

        return mdlTableRowHolder;
    }

    private String migrateMetadata(String metadata, String source, String newId, String thumbnailUrl) {
        if (metadata == null || metadata.isEmpty()) {
            return metadata;
        }

        String decodedMetadata = new String(Base64.getDecoder().decode(metadata));

        MixedArray mixedArray = Pherialize.unserialize(decodedMetadata).toArray();

        if (mixedArray.containsKey("url")) {
            mixedArray.put("url", source);
        }
        if (mixedArray.containsKey("entryid")) {
            mixedArray.put("entryid", newId);
        }
        if (mixedArray.containsKey("entryId")) {
            mixedArray.put("entryId", newId);
        }
        if (mixedArray.containsKey("entry_id")) {
            mixedArray.put("entry_id", newId);
        }
        if (mixedArray.containsKey("thumbnailurl")) {
            mixedArray.put("thumbnailurl", thumbnailUrl);
        }

        String migratedData = Pherialize.serialize(mixedArray).replaceFirst("a", "O:8:\"stdClass\"");

        return Base64.getEncoder().encodeToString(migratedData.getBytes());
    }
}
