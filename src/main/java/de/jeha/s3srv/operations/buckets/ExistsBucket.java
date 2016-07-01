package de.jeha.s3srv.operations.buckets;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * @author jenshadlich@googlemail.com
 */
@Path("/")
public class ExistsBucket extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(ExistsBucket.class);

    public ExistsBucket(StorageBackend storageBackend) {
        super(storageBackend);
    }

    @HEAD
    @Path("/{bucket}/")
    @Timed
    public Response createBucket(@Context HttpHeaders headers,
                                 @Context HttpServletResponse response,
                                 @PathParam("bucket") String bucket) {
        LOG.info("existsBucket {}", bucket);

        if (!getStorageBackend().existsBucket(bucket)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        return Response.ok()
                .build();
    }

}
