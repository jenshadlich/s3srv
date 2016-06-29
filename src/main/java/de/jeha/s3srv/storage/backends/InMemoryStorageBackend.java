package de.jeha.s3srv.storage.backends;

import de.jeha.s3srv.storage.S3Bucket;
import de.jeha.s3srv.storage.S3Object;
import de.jeha.s3srv.storage.StorageBackend;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
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
    private final Map<String, byte[]> objectContents = new HashMap<>();

    @Override
    public void createBucket(String bucket) {
        buckets.put(bucket, new S3Bucket(bucket));
    }

    @Override
    public boolean existsBucket(String bucket) {
        return buckets.containsKey(bucket);
    }

    @Override
    public List<S3Bucket> listBuckets() {
        return buckets.values().stream().collect(Collectors.toList());
    }

    @Override
    public S3Object createObject(String bucket, String key, InputStream contentStream, int contentLength) throws IOException {
        S3Bucket bucketObject = buckets.get(bucket);
        byte[] content = IOUtils.readFully(contentStream, contentLength);
        String md5 = DigestUtils.md5Hex(content);

        final String objectKey = buildObjectKey(bucket, key);
        final S3Object object = new S3Object(bucketObject, key, md5);

        objects.put(objectKey, object);
        objectContents.put(objectKey, content);

        return object;
    }

    @Override
    public boolean existsObject(String bucket, String key) {
        return objects.containsKey(buildObjectKey(bucket, key));
    }

    @Override
    public List<S3Object> listObjects(String bucket) {
        return objects.values().stream().filter(v -> v.getBucket().getName().equals(bucket)).collect(Collectors.toList());
    }

    @Override
    public S3Object getObject(String bucket, String key) {
        return objects.get(buildObjectKey(bucket, key));
    }

    // -----------------------------------------------------------------------------------------------------------------

    private String buildObjectKey(String bucket, String key) {
        return bucket + "/" + key;
    }

}
