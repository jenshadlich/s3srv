package de.jeha.s3srv.storage;

import de.jeha.s3srv.storage.S3Bucket;
import de.jeha.s3srv.storage.S3Object;

import java.util.List;

/**
 * @author jenshadlich@googlemail.com
 */
public interface StorageBackend {

    void createBucket(String bucket);

    List<S3Bucket> listBuckets();

    void createObject(String bucket, String key);

    List<S3Object> listObjects(String bucket);

    S3Object getObject(String bucket, String key);

}
