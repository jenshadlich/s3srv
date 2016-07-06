package de.jeha.s3srv.operations.buckets;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.common.BucketNameValidator;
import de.jeha.s3srv.common.errors.ErrorCodes;
import de.jeha.s3srv.common.security.AuthorizationContext;
import de.jeha.s3srv.common.security.AuthorizationUtils;
import de.jeha.s3srv.common.security.Credentials;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
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
    public Response createBucket(@Context HttpServletRequest request,
                                 @PathParam("bucket") String bucket) {
        LOG.info("createBucket '{}'", bucket);
        final String resource = "/" + bucket + "/";

        AuthorizationContext authorizationContext = checkAuthorization(request, resource);
        if (!authorizationContext.isAccessKeyValid()) {
            return createErrorResponse(ErrorCodes.INVALID_ACCESS_KEY_ID, resource, null);
        }
        if (!authorizationContext.isSignatureValid()) {
            return createErrorResponse(ErrorCodes.SIGNATURE_DOES_NOT_MATCH, resource, null);
        }

        try {
            BucketNameValidator.isValid(bucket);
        } catch (ValidationException e) {
            LOG.info("Invalid bucket name: {}", e.getMessage());
            return createErrorResponse(ErrorCodes.INVALID_BUCKET_NAME, resource, null);
        }

        if (getStorageBackend().existsBucket(bucket)) {
            return createErrorResponse(ErrorCodes.BUCKET_ALREADY_EXISTS, resource, null);
        } else {
            getStorageBackend().createBucket(bucket);

            return Response.ok()
                    .header("Location", "/" + bucket)
                    .header("Content-Length", "0")
                    .header("Connection", "close")
                    .build();
        }
    }

    private AuthorizationContext checkAuthorization(HttpServletRequest request, String resource) {
        final String date = request.getHeader("Date");
        final String authorization = request.getHeader("Authorization");
        final String contentMD5 = request.getHeader("Content-MD5");
        final String contentType = request.getHeader("Content-Type");

        LOG.info("Date: {}", date);
        LOG.info("Authorization: {}", authorization);

        Credentials credentials = new Credentials("foo", "bar"); // TODO

        boolean valid = AuthorizationUtils.checkAuthorization(
                authorization,
                credentials,
                request.getMethod(),
                contentMD5,
                contentType,
                date,
                resource);

        return new AuthorizationContext(credentials.getAccessKey(), valid);
    }

}
