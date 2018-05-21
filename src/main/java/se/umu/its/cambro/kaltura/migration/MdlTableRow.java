package se.umu.its.cambro.kaltura.migration;

import java.math.BigInteger;
import java.util.Base64;

public class MdlTableRow {

    private BigInteger id;

    private BigInteger uiConfId;

    private String entryId;

    private String source;

    private String metadata;

    public BigInteger id() {
        return id;
    }

    public void id(BigInteger id) {
        this.id = id;
    }

    public BigInteger uiconfId() {
        return uiConfId;
    }

    public void uiconfId(BigInteger uiconf_id) {
        this.uiConfId = uiconf_id;
    }

    public String entryId() {
        return entryId;
    }

    public void entryId(String entry_id) {
        this.entryId = entry_id;
    }

    public String source() {
        return source;
    }

    public void source(String source) {
        this.source = source;
    }

    public String metadata() {
        return metadata;
    }

    public void metadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "MdlTableRow{" +
                "id=" + id +
                (uiConfId == null ? "" : ", uiConfId=" + uiConfId) +
                ", entryId='" + entryId + '\'' +
                ", source='" + source + '\'' +
                ", metadata_decoded='" + (metadata == null ? "null" : new String(Base64.getDecoder().decode(metadata))) + '\'' +
                '}';
    }
}