package de.jeha.s3srv.operations.buckets;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.common.BucketNameValidator;
import de.jeha.s3srv.common.errors.ErrorCodes;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
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

        try {
            BucketNameValidator.isValid(bucket);
        } catch (ValidationException e) {
            LOG.info("Invalid bucket name: {}", e.getMessage());
            return createErrorResponse(ErrorCodes.INVALID_BUCKET_NAME, bucket, null);
        }

        if (getStorageBackend().existsBucket(bucket)) {
            return createErrorResponse(ErrorCodes.BUCKET_ALREADY_EXISTS, bucket, null);
        } else {
            getStorageBackend().createBucket(bucket);

            return Response.ok()
                    .header("Location", "/" + bucket)
                    .header("Content-Length", "0")
                    .header("Connection", "close")
                    .build();
        }
    }

}
