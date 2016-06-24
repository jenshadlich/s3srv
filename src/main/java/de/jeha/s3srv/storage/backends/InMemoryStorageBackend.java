package de.jeha.s3srv.storage.backends;

import de.jeha.s3srv.storage.S3Bucket;
import de.jeha.s3srv.storage.S3Object;
import de.jeha.s3srv.storage.StorageBackend;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jenshadlich@googlemail.com
 */
public class InMemoryStorageBackend implements StorageBackend {

    private final Map<String, S3Bucket> buckets = new HashMap<>();
    private final Map<String, S3Object> objects = new HashMap<>();

    @Override
    public void createBucket(String bucket) {
        buckets.put(bucket, new S3Bucket(bucket));
    }

    @Override
    public List<S3Bucket> listBuckets() {
        return buckets.values().stream().collect(Collectors.toList());
    }

    @Override
    public void createObject(String bucket, String key) {
        S3Bucket bucketObject = buckets.get(bucket);
        objects.put(buildObjectKey(bucket, key), new S3Object(bucketObject, key));
    }

    @Override
    public List<S3Object> listObjects(String bucket) {
        return Collections.emptyList();
    }

    @Override
    public S3Object getObject(String bucket, String key) {
        S3Bucket bucketObject = buckets.get(bucket);
        return objects.get(buildObjectKey(bucket, key));
    }

    // -----------------------------------------------------------------------------------------------------------------

    private String buildObjectKey(String bucket, String key) {
        return bucket + "/" + key;
    }

}
