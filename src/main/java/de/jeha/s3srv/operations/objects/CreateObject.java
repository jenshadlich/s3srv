package de.jeha.s3srv.operations.objects;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.common.errors.BadDigestException;
import de.jeha.s3srv.common.errors.ErrorCodes;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.common.S3Object;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author jenshadlich@googlemail.com
 */
@Path("/")
public class CreateObject extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(CreateObject.class);

    public CreateObject(StorageBackend storageBackend) {
        super(storageBackend);
    }

    @PUT
    @Path("/{bucket}/{key}")
    @Timed
    public Response createObject(@Context HttpHeaders headers,
                                 @Context HttpServletRequest request,
                                 @Context HttpServletResponse response,
                                 @PathParam("bucket") String bucket,
                                 @PathParam("key") String key) {
        LOG.info("createObject {}/{}", bucket, key);

        final String expectedMD5 = headers.getHeaderString("Content-MD5");
        try {
            S3Object object = getStorageBackend().createObject(
                    bucket,
                    key,
                    request.getInputStream(),
                    request.getContentLength(),
                    expectedMD5,
                    request.getContentType());

            return Response.ok()
                    .header("ETag", object.getETag())
                    .build();
        } catch (IOException e) {
            LOG.error("Unable to read input stream", e);
            return createErrorResponse(ErrorCodes.INTERNAL_ERROR, "/" + bucket + "/" + key, null);
        } catch (BadDigestException e) {
            return createErrorResponse(ErrorCodes.BAD_DIGEST, "/" + bucket + "/" + key, null);
        }
    }

}
