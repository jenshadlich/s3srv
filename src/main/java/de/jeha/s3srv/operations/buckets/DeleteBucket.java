package de.jeha.s3srv.operations.buckets;

import de.jeha.s3srv.common.errors.ErrorCodes;
import de.jeha.s3srv.common.security.AuthorizationContext;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 * @author jenshadlich@googlemail.com
 */
public class DeleteBucket extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteBucket.class);

    public DeleteBucket(StorageBackend storageBackend) {
        super(storageBackend);
    }

    /**
     * Delete a bucket
     *
     * @param request HTTP request
     * @param bucket  bucket to delete
     * @return response
     */
    public Response deleteBucket(HttpServletRequest request,
                                 String bucket) {
        LOG.info("deleteBucket '{}'", bucket);
        final String resource = "/" + bucket + "/";

        AuthorizationContext authorizationContext = checkAuthorization(request, resource);
        if (!authorizationContext.isUserValid()) {
            return createErrorResponse(ErrorCodes.INVALID_ACCESS_KEY_ID, resource, null);
        }
        if (!authorizationContext.isSignatureValid()) {
            return createErrorResponse(ErrorCodes.SIGNATURE_DOES_NOT_MATCH, resource, null);
        }
        if (!getStorageBackend().doesBucketExist(bucket)) {
            return createErrorResponse(ErrorCodes.NO_SUCH_BUCKET, resource, null);
        }
        if (!getStorageBackend().getBucket(bucket).isOwnedBy(authorizationContext.getUser())) {
            return createErrorResponse(ErrorCodes.ACCESS_DENIED, resource, null);
        }
        if (!getStorageBackend().listObjects(bucket, 1).isEmpty()) {
            return createErrorResponse(ErrorCodes.BUCKET_NOT_EMPTY, "/" + bucket, null);
        }

        getStorageBackend().deleteBucket(bucket);

        return Response.noContent()
                .build();
    }

}
