package de.jeha.s3srv.operations.buckets;

import de.jeha.s3srv.api.ListBucketResult;
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
public class ListObjects extends AbstractOperation {
    private static final Logger LOG = LoggerFactory.getLogger(ListObjects.class);

    public ListObjects(StorageBackend storageBackend) {
        super(storageBackend);
    }

    /**
     * List the objects of a given bucket.
     *
     * @param request HTTP request
     * @param bucket  the bucket to list
     * @return response
     */
    public Response listBuckets(HttpServletRequest request,
                                String bucket) {
        LOG.info("listObjects '{}'", bucket);
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

        List<ListBucketResult.ContentsEntry> objects = getStorageBackend()
                .listObjects(bucket)
                .stream()
                .map(object -> new ListBucketResult.ContentsEntry(
                        object.getKey(),
                        object.getLastModified(),
                        object.getETag(),
                        object.getSize()))
                .collect(Collectors.toList());

        ListBucketResult response = new ListBucketResult(bucket, objects.size(), 1000, objects);

        try {
            return Response.ok(JaxbMarshaller.marshall(response), MediaType.APPLICATION_XML_TYPE)
                    .build();
        } catch (Exception e) {
            LOG.error("Unable to create xml response body", e);
            return createErrorResponse(ErrorCodes.INTERNAL_ERROR, "/" + bucket, null);
        }
    }

}
