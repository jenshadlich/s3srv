package de.jeha.s3srv.operations.objects;

import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

/**
 * @author jenshadlich@googlemail.com
 */
public class ExistsObject extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(ExistsObject.class);

    public ExistsObject(StorageBackend storageBackend) {
        super(storageBackend);
    }

    public Response existsObject(String bucket,
                                 String key) {
        LOG.info("existsObject '{}/{}'", bucket, key);

        if (getStorageBackend().existsObject(bucket, key)) {
            return Response.ok()
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

}
