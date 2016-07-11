package de.jeha.s3srv.operations.objects;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.model.S3Object;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * @author jenshadlich@googlemail.com
 */
@Path("/")
public class GetObject extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(ExistsObject.class);

    public GetObject(StorageBackend storageBackend) {
        super(storageBackend);
    }

    @GET
    @Path("/{bucket}/{key}")
    @Timed
    public Response getObject(@Context HttpHeaders headers,
                              @Context HttpServletResponse response,
                              @PathParam("bucket") String bucket,
                              @PathParam("key") String key) {
        LOG.info("getObject '{}/{}'", bucket, key);

        if (getStorageBackend().existsObject(bucket, key)) {
            S3Object object = getStorageBackend().getObject(bucket, key);

            return Response.ok(object.getInputStream())
                    .type(object.getContentType())
                    .header("ETag", object.getETag())
                    .header("Content-Length", Integer.toString(object.getSize()))
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

}
