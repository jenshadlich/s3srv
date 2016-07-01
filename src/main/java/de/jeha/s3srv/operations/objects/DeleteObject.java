package de.jeha.s3srv.operations.objects;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * @author jenshadlich@googlemail.com
 */
@Path("/")
public class DeleteObject extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteObject.class);

    public DeleteObject(StorageBackend storageBackend) {
        super(storageBackend);
    }

    @DELETE
    @Path("/{bucket}/{key}")
    @Timed
    public Response deleteObject(@Context HttpHeaders headers,
                                 @Context HttpServletResponse response,
                                 @PathParam("bucket") String bucket,
                                 @PathParam("key") String key) {
        LOG.info("deleteObject {}/{}", bucket, key);

        if (!getStorageBackend().existsObject(bucket, key)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        getStorageBackend().deleteObject(bucket, key);

        return Response.noContent()
                .build();
    }

}
