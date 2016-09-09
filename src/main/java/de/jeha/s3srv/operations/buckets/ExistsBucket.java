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
public class ExistsBucket extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(ExistsBucket.class);

    public ExistsBucket(StorageBackend storageBackend) {
        super(storageBackend);
    }

    public Response existsBucket(HttpServletRequest request,
                                 String bucket) {
        LOG.info("existsBucket '{}'", bucket);
        final String resource = "/" + bucket + "/";

        AuthorizationContext authorizationContext = checkAuthorization(request, resource);
        if (!authorizationContext.isUserValid()) {
            return createErrorResponse(ErrorCodes.INVALID_ACCESS_KEY_ID, resource, null);
        }
        if (!authorizationContext.isSignatureValid()) {
            return createErrorResponse(ErrorCodes.SIGNATURE_DOES_NOT_MATCH, resource, null);
        }
        if (!getStorageBackend().existsBucket(bucket)) {
            return createErrorResponse(ErrorCodes.NO_SUCH_BUCKET, resource, null);
        }
        if (!getStorageBackend().getBucket(bucket).isOwnedBy(authorizationContext.getUser())) {
            return createErrorResponse(ErrorCodes.ACCESS_DENIED, resource, null);
        }

        return Response.ok()
                .build();
    }

}
