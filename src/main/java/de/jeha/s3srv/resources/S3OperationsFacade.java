package de.jeha.s3srv.resources;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.operations.buckets.*;
import de.jeha.s3srv.operations.objects.CreateObject;
import de.jeha.s3srv.operations.objects.DeleteObject;
import de.jeha.s3srv.operations.objects.ExistsObject;
import de.jeha.s3srv.operations.objects.GetObject;
import de.jeha.s3srv.operations.service.ListBuckets;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author jenshadlich@googlemail.com
 */
@Path("/")
public class S3OperationsFacade {

    private static final Logger LOG = LoggerFactory.getLogger(S3OperationsFacade.class);

    private final StorageBackend storageBackend;

    public S3OperationsFacade(StorageBackend storageBackend) {
        this.storageBackend = storageBackend;
    }

    @HEAD
    @Path("{subResources:.*}")
    @Timed
    public Response head(@Context HttpServletRequest request) {
        LOG.info("head");

        final S3RequestContext context = S3RequestContext.build(request);
        LOG.info("{}", context);

        if (context.getBucket() != null && context.getKey() != null) {
            return new ExistsObject(storageBackend).doesObjectExist(request, context.getBucket(), context.getKey());
        }

        if (context.getBucket() != null) {
            return new ExistsBucket(storageBackend).doesBucketExist(request, context.getBucket());
        }

        throw new UnsupportedOperationException("Not yet implemented!"); // TODO
    }

    @GET
    @Path("{subResources:.*}")
    @Timed
    public Response get(@Context HttpServletRequest request) {
        LOG.info("get");

        final S3RequestContext context = S3RequestContext.build(request);
        LOG.info("{}", context);

        if (context.getBucket() != null && context.getKey() != null) {
            return new GetObject(storageBackend).getObject(request, context.getBucket(), context.getKey());
        }

        if (context.getBucket() != null && "acl".equals(request.getQueryString())) {
            return new GetBucketACL(storageBackend).getBucketACL(request, context.getBucket());
        }

        if (context.getBucket() != null) {
            return new ListObjects(storageBackend).listBuckets(request, context.getBucket());
        }

        return new ListBuckets(storageBackend).listBuckets(request);
    }

    @PUT
    @Path("{subResources:.*}")
    @Timed
    public Response put(@Context HttpServletRequest request) {
        LOG.info("put");

        final S3RequestContext context = S3RequestContext.build(request);
        LOG.info("{}", context);

        if (context.getBucket() != null && context.getKey() != null) {
            return new CreateObject(storageBackend).createObject(request, context.getBucket(), context.getKey());
        }

        if (context.getBucket() != null) {
            return new CreateBucket(storageBackend).createBucket(request, context.getBucket());
        }

        throw new UnsupportedOperationException("Not yet implemented!"); // TODO
    }

    @POST
    @Path("{subResources:.*}")
    @Timed
    public Response post(@Context HttpServletRequest request) {
        LOG.info("post");

        final S3RequestContext context = S3RequestContext.build(request);
        LOG.info("{}", context);

        throw new UnsupportedOperationException("Not yet implemented!"); // TODO
    }

    @DELETE
    @Path("{subResources:.*}")
    @Timed
    public Response delete(@Context HttpServletRequest request) {
        LOG.info("delete");

        final S3RequestContext context = S3RequestContext.build(request);
        LOG.info("{}", context);

        if (context.getBucket() != null && context.getKey() != null) {
            return new DeleteObject(storageBackend).deleteObject(request, context.getBucket(), context.getKey());
        }

        if (context.getBucket() != null) {
            return new DeleteBucket(storageBackend).deleteBucket(request, context.getBucket());
        }

        throw new UnsupportedOperationException("Not yet implemented!"); // TODO
    }

}
