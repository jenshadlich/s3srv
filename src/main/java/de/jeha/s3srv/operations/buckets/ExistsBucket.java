package de.jeha.s3srv.operations.buckets;

import de.jeha.s3srv.common.errors.ErrorCodes;
import de.jeha.s3srv.common.security.AuthorizationContext;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import static de.jeha.s3srv.common.errors.ErrorCodes.NO_SUCH_BUCKET;

/**
 * @author jenshadlich@googlemail.com
 */
public class ExistsBucket extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(ExistsBucket.class);

    public ExistsBucket(StorageBackend storageBackend) {
        super(storageBackend);
    }

    /**
     * Check if a bucket exists.
     *
     * @param request HTTP request
     * @param bucket  the bucket to check
     * @return response
     */
    public Response doesBucketExist(HttpServletRequest request,
                                    String bucket) {
        LOG.info("doesBucketExist '{}'", bucket);
        final String resource = "/" + bucket + "/";

        AuthorizationContext authorizationContext = checkAuthorization(request, resource);
        if (!authorizationContext.isUserValid()) {
            return createErrorResponse(ErrorCodes.INVALID_ACCESS_KEY_ID, resource, null);
        }
        if (!authorizationContext.isSignatureValid()) {
            return createErrorResponse(ErrorCodes.SIGNATURE_DOES_NOT_MATCH, resource, null);
        }
        if (!getStorageBackend().doesBucketExist(bucket)) {
            return Response.status(NO_SUCH_BUCKET.getStatusCode())
                    .build();
        }
        if (!getStorageBackend().getBucket(bucket).isOwnedBy(authorizationContext.getUser())) {
            return createErrorResponse(ErrorCodes.ACCESS_DENIED, resource, null);
        }

        return Response.ok()
                .build();
    }

}
