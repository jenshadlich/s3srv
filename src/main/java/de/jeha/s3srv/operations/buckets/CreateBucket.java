package de.jeha.s3srv.operations.buckets;

import de.jeha.s3srv.common.BucketNameValidator;
import de.jeha.s3srv.common.errors.ErrorCodes;
import de.jeha.s3srv.common.http.Headers;
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
public class CreateBucket extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(CreateBucket.class);

    public CreateBucket(StorageBackend storageBackend) {
        super(storageBackend);
    }

    /**
     * Create a bucket.
     *
     * @param request HTTP request
     * @param bucket  bucket to create
     * @return response
     */
    public Response createBucket(HttpServletRequest request,
                                 String bucket) {
        LOG.info("createBucket '{}'", bucket);
        final String resource = "/" + bucket + "/";

        AuthorizationContext authorizationContext = checkAuthorization(request, resource);
        if (!authorizationContext.isUserValid()) {
            return createErrorResponse(ErrorCodes.INVALID_ACCESS_KEY_ID, resource, null);
        }
        if (!authorizationContext.isSignatureValid()) {
            return createErrorResponse(ErrorCodes.SIGNATURE_DOES_NOT_MATCH, resource, null);
        }
        if (!BucketNameValidator.isValid(bucket)) {
            return createErrorResponse(ErrorCodes.INVALID_BUCKET_NAME, resource, null);
        }
        if (getStorageBackend().doesBucketExist(bucket)) {
            return createErrorResponse(ErrorCodes.BUCKET_ALREADY_EXISTS, resource, null);
        }

        getStorageBackend().createBucket(authorizationContext.getUser(), bucket);

        return Response.ok()
                .header(Headers.LOCATION, "/" + bucket)
                .header(Headers.CONTENT_LENGTH, "0")
                .header(Headers.CONNECTION, "close")
                .build();
    }

}
