package de.jeha.s3srv.operations.objects;

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
public class ExistsObject extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(ExistsObject.class);

    public ExistsObject(StorageBackend storageBackend) {
        super(storageBackend);
    }

    /**
     * Check if an object exists.
     *
     * @param request HTTP request
     * @param bucket bucket
     * @param key    object key
     * @return response
     */
    public Response doesObjectExist(HttpServletRequest request,
                                    String bucket,
                                    String key) {
        LOG.info("doesObjectExist '{}/{}'", bucket, key);
        final String resource = "/" + bucket + "/" + key;

        AuthorizationContext authorizationContext = checkAuthorization(request, resource);
        if (!authorizationContext.isUserValid()) {
            return createErrorResponse(ErrorCodes.INVALID_ACCESS_KEY_ID, resource, null);
        }
        if (!authorizationContext.isSignatureValid()) {
            return createErrorResponse(ErrorCodes.SIGNATURE_DOES_NOT_MATCH, resource, null);
        }
        if (!getStorageBackend().getBucket(bucket).isOwnedBy(authorizationContext.getUser())) {
            return createErrorResponse(ErrorCodes.ACCESS_DENIED, resource, null);
        }
        if (getStorageBackend().doesObjectExist(bucket, key)) {
            return Response.ok()
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

}
