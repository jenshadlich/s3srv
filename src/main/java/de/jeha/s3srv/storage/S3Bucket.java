package de.jeha.s3srv.storage;

/**
 * @author jenshadlich@googlemail.com
 */
public class S3Bucket {

    private final String name;

    public S3Bucket(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
