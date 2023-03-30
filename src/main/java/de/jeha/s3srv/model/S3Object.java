package de.jeha.s3srv.model;

import de.jeha.s3srv.storage.InputStreamCallback;

import java.io.InputStream;
import java.time.Instant;

public class S3Object {

    private final S3Bucket bucket;
    private final String key;
    private final String md5;
    private final String eTag;
    private final Integer size;
    private final Instant lastModified;
    private final InputStreamCallback inputStreamCallback;
    private final String contentType;

    public S3Object(S3Bucket bucket, String key, String md5, String eTag, Integer size, Instant lastModified,
                    InputStreamCallback inputStreamCallback, String contentType) {
        this.bucket = bucket;
        this.key = key;
        this.md5 = md5;
        this.eTag = eTag;
        this.size = size;
        this.lastModified = lastModified;
        this.inputStreamCallback = inputStreamCallback;
        this.contentType = contentType;
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

    public InputStream getInputStream() {
        return inputStreamCallback.getInputStream();
    }

    public String getContentType() {
        return contentType;
    }

}
