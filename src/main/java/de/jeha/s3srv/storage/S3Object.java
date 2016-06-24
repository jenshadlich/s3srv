package de.jeha.s3srv.storage;

/**
 * @author jenshadlich@googlemail.com
 */
public class S3Object {

    private final S3Bucket bucket;
    private final String key;

    public S3Object(S3Bucket bucket, String key) {
        this.bucket = bucket;
        this.key = key;
    }

    public S3Bucket getBucket() {
        return bucket;
    }

    public String getKey() {
        return key;
    }

}
