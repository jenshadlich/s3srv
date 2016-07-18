package de.jeha.s3srv.operations.buckets;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.common.errors.ErrorCodes;
import de.jeha.s3srv.common.security.AuthorizationContext;
import de.jeha.s3srv.model.S3Bucket;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author jenshadlich@googlemail.com
 */
@Path("/")
public class DeleteBucket extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteBucket.class);

    public DeleteBucket(StorageBackend storageBackend) {
        super(storageBackend);
    }

    @DELETE
    @Path("/{bucket}/")
    @Timed
    public Response createBucket(@Context HttpServletRequest request,
                                 @PathParam("bucket") String bucket) {
        LOG.info("deleteBucket '{}'", bucket);
        final String resource = "/" + bucket + "/";

        AuthorizationContext authorizationContext = checkAuthorization(request, resource);
        if (!authorizationContext.isUserValid()) {
            return createErrorResponse(ErrorCodes.INVALID_ACCESS_KEY_ID, resource, null);
        }
        if (!authorizationContext.isSignatureValid()) {
            return createErrorResponse(ErrorCodes.SIGNATURE_DOES_NOT_MATCH, resource, null);
        }

        if (!getStorageBackend().existsBucket(bucket)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        S3Bucket bucketObject = getStorageBackend().getBucket(bucket);
        if (!bucketObject.getOwner().getId().equals(authorizationContext.getUser().getId())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }

        if (!getStorageBackend().listObjects(bucket).isEmpty()) {
            return createErrorResponse(ErrorCodes.BUCKET_NOT_EMPTY, "/" + bucket, null);
        }

        getStorageBackend().deleteBucket(bucket);

        return Response.noContent()
                .build();
    }

}
