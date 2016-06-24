package de.jeha.s3srv.operations.buckets;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.storage.S3Bucket;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author jenshadlich@googlemail.com
 */
@Path("/")
public class CreateBucket {

    private static final Logger LOG = LoggerFactory.getLogger(CreateBucket.class);
    private final StorageBackend storageBackend;

    public CreateBucket(StorageBackend storageBackend) {
        this.storageBackend = storageBackend;
    }

    @PUT
    @Path("/{bucket}/")
    @Produces(MediaType.APPLICATION_XML)
    @Timed
    public void createBucket(@PathParam("bucket") String bucket) {
        LOG.info("createBucket {}", bucket);

        storageBackend.createBucket(bucket);

        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
