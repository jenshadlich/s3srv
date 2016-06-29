package de.jeha.s3srv.operations.buckets;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.errors.ErrorCodes;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * @author jenshadlich@googlemail.com
 */
@Path("/")
public class CreateBucket extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(CreateBucket.class);

    public CreateBucket(StorageBackend storageBackend) {
        super(storageBackend);
    }

    @PUT
    @Path("/{bucket}/")
    @Timed
    public Response createBucket(@Context HttpHeaders headers,
                                 @Context HttpServletResponse response,
                                 @PathParam("bucket") String bucket) {
        LOG.info("createBucket {}", bucket);

        if (getStorageBackend().existsBucket(bucket)) {
            return createErrorResponse(ErrorCodes.BUCKET_ALREADY_EXISTS, bucket, null);
        } else {
            getStorageBackend().createBucket(bucket);

            response.addHeader("Location", "/" + bucket);
            return Response.ok().build();
        }
    }

}
