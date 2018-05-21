package se.umu.its.cambro.kaltura.migration.playlist;

public class KalturaPlaylist {

    private final String id;
    private final String referenceId;

    KalturaPlaylist(String id, String referenceId) {
        this.id = id;
        this.referenceId = referenceId;
    }

    public String id() {
        return id;
    }

    String referenceId() {
        return referenceId;
    }

    @Override
    public String toString() {
        return "KalturaPlaylist{" +
                "id='" + id + '\'' +
                ", referenceId='" + referenceId + '\'' +
                '}';
    }
}
