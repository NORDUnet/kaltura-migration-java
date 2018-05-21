package se.umu.its.cambro.kaltura.migration.entry;

public class KalturaMediaEntry {

    private final String id;

    private final String referenceId;

    private final String thumbnailUrl;

    KalturaMediaEntry(String id, String referenceId, String thumbnailUrl) {
        this.id = id;
        this.referenceId = referenceId;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String id() {
        return id;
    }

    String referenceId() {
        return referenceId;
    }

    String thumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public String toString() {
        return "KalturaMediaEntry{" +
                "id='" + id + '\'' +
                ", referenceId='" + referenceId + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
