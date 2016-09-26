package de.jeha.s3srv.operations.service;

import de.jeha.s3srv.api.ListAllMyBucketsResponse;
import de.jeha.s3srv.api.Owner;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jenshadlich@googlemail.com
 */
public class ListBuckets extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(ListBuckets.class);

    public ListBuckets(StorageBackend storageBackend) {
        super(storageBackend);
    }

    /**
     * List the buckets of some user
     *
     * @param request HTTP request
     * @return response
     */
    public Response listBuckets(HttpServletRequest request) {
        LOG.info("listBuckets");
        final String resource = "/";

        AuthorizationContext authorizationContext = checkAuthorization(request, resource);
        if (!authorizationContext.isUserValid()) {
            return createErrorResponse(ErrorCodes.INVALID_ACCESS_KEY_ID, resource, null);
        }
        if (!authorizationContext.isSignatureValid()) {
            return createErrorResponse(ErrorCodes.SIGNATURE_DOES_NOT_MATCH, resource, null);
        }

        List<ListAllMyBucketsResponse.BucketsEntry> buckets = getStorageBackend()
                .listBuckets()
                .stream()
                .filter(bucket -> bucket.isOwnedBy(authorizationContext.getUser()))
                .map(bucket -> new ListAllMyBucketsResponse.BucketsEntry(bucket.getName(), bucket.getCreationDate()))
                .collect(Collectors.toList());

        ListAllMyBucketsResponse response =
                new ListAllMyBucketsResponse(
                        new Owner(
                                authorizationContext.getUser().getId(),
                                authorizationContext.getUser().getDisplayName()),
                        buckets);

        try {
            return Response.ok(JaxbMarshaller.marshall(response), MediaType.APPLICATION_XML_TYPE)
                    .build();
        } catch (Exception e) {
            LOG.error("Unable to create xml response body", e);
            return createErrorResponse(ErrorCodes.INTERNAL_ERROR, "/", null);
        }
    }

}
