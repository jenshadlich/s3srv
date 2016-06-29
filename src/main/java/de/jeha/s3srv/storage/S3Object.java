package de.jeha.s3srv.storage;

import java.time.Instant;

/**
 * @author jenshadlich@googlemail.com
 */
public class S3Object {

    private final S3Bucket bucket;
    private final String key;
    private final String md5;
    private final String eTag;
    private final Integer size;
    private final Instant lastModified;

    public S3Object(S3Bucket bucket, String key, String md5, Integer size, Instant lastModified) {
        this.bucket = bucket;
        this.key = key;
        this.md5 = md5;
        this.eTag = "\"" + md5 + "\"";
        this.size = size;
        this.lastModified = lastModified;
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

    public Integer getSize() {
        return size;
    }

    public Instant getLastModified() {
        return lastModified;
    }

}
