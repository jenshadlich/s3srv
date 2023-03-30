package de.jeha.s3srv.operations.buckets;

import de.jeha.s3srv.api.AccessControlPolicy;
import de.jeha.s3srv.api.Grantee;
import de.jeha.s3srv.api.Owner;
import de.jeha.s3srv.common.acl.ACLPermissions;
import de.jeha.s3srv.common.errors.ErrorCodes;
import de.jeha.s3srv.common.security.AuthorizationContext;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import de.jeha.s3srv.xml.JaxbMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

public class GetBucketACL extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(GetBucketACL.class);

    public GetBucketACL(StorageBackend storageBackend) {
        super(storageBackend);
    }

    /**
     * Get the ACL for a bucket. TODO: currently hardwired FULL_CONTROL only
     *
     * @param request HTTP request
     * @param bucket  the bucket to check
     * @return response
     */
    public Response getBucketACL(HttpServletRequest request,
                                 String bucket) {
        LOG.info("getBucketACL '{}'", bucket);
        final String resource = "/" + bucket + "/?acl";

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

        AccessControlPolicy response = new AccessControlPolicy(
                new Owner(authorizationContext.getUser().getId(), authorizationContext.getUser().getDisplayName()),
                Collections.singletonList(
                        new AccessControlPolicy.Grant(
                                new Grantee(authorizationContext.getUser().getId(), authorizationContext.getUser().getDisplayName()),
                                ACLPermissions.FULL_CONTROL.name())));

        try {
            return Response.ok(JaxbMarshaller.marshall(response), MediaType.APPLICATION_XML_TYPE)
                    .build();
        } catch (Exception e) {
            LOG.error("Unable to create xml response body", e);
            return createErrorResponse(ErrorCodes.INTERNAL_ERROR, "/" + bucket, null);
        }
    }

}
