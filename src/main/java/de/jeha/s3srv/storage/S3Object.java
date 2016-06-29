package de.jeha.s3srv.storage;

/**
 * @author jenshadlich@googlemail.com
 */
public class S3Object {

    private final S3Bucket bucket;
    private final String key;
    private final String md5;
    private final String eTag;

    public S3Object(S3Bucket bucket, String key, String md5) {
        this.bucket = bucket;
        this.key = key;
        this.md5 = md5;
        this.eTag = "\"" + md5 + "\"";
    }

    public S3Bucket getBucket() {
        return bucket;
    }

    public String getKey() {
        return key;
    }

    public String getMD5() {
        return md5;
    }

    public String getETag() {
        return eTag;
    }

}
