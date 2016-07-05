package de.jeha.s3srv.model;

/**
 * @author jenshadlich@googlemail.com
 */
public class S3Owner {

    private final String displayName;
    private final String id;

    public S3Owner(String displayName, String id) {
        this.displayName = displayName;
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return id;
    }

}
