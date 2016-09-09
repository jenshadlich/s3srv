package de.jeha.s3srv.operations.objects;

import de.jeha.s3srv.common.http.Headers;
import de.jeha.s3srv.model.S3Object;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

/**
 * @author jenshadlich@googlemail.com
 */
public class GetObject extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(ExistsObject.class);

    public GetObject(StorageBackend storageBackend) {
        super(storageBackend);
    }

    public Response getObject(String bucket,
                              String key) {
        LOG.info("getObject '{}/{}'", bucket, key);

        if (getStorageBackend().existsObject(bucket, key)) {
            S3Object object = getStorageBackend().getObject(bucket, key);

            return Response.ok(object.getInputStream())
                    .type(object.getContentType())
                    .header(Headers.ETAG, object.getETag())
                    .header(Headers.CONTENT_LENGTH, Integer.toString(object.getSize()))
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

}
