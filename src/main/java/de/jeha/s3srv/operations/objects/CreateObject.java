package de.jeha.s3srv.operations.objects;

import de.jeha.s3srv.common.errors.BadDigestException;
import de.jeha.s3srv.common.errors.ErrorCodes;
import de.jeha.s3srv.common.http.Headers;
import de.jeha.s3srv.common.security.AuthorizationContext;
import de.jeha.s3srv.model.S3Object;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author jenshadlich@googlemail.com
 */
public class CreateObject extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(CreateObject.class);

    public CreateObject(StorageBackend storageBackend) {
        super(storageBackend);
    }

    /**
     * Create an object.
     *
     * @param request HTTP request
     * @param bucket  bucket
     * @param key     object key
     * @return response
     */
    public Response createObject(HttpServletRequest request,
                                 String bucket,
                                 String key) {
        LOG.info("createObject '{}/{}'", bucket, key);
        final String resource = "/" + bucket + "/" + key;

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

        final String expectedMD5 = request.getHeader(Headers.CONTENT_MD5); // optional
        try {
            S3Object object = getStorageBackend().createObject(
                    bucket,
                    key,
                    request.getInputStream(),
                    request.getContentLength(),
                    expectedMD5,
                    request.getContentType());

            return Response.ok()
                    .header(Headers.ETAG, object.getETag())
                    .build();
        } catch (IOException e) {
            LOG.error("Unable to read input stream", e);
            return createErrorResponse(ErrorCodes.INTERNAL_ERROR, "/" + bucket + "/" + key, null);
        } catch (BadDigestException e) {
            return createErrorResponse(ErrorCodes.BAD_DIGEST, "/" + bucket + "/" + key, null);
        }
    }

}
