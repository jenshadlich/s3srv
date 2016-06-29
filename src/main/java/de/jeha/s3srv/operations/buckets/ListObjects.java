package de.jeha.s3srv.operations.buckets;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.api.ListBucketResult;
import de.jeha.s3srv.jaxb.JaxbMarshaller;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jenshadlich@googlemail.com
 */
@Path("/")
public class ListObjects extends AbstractOperation {
    private static final Logger LOG = LoggerFactory.getLogger(ListObjects.class);

    public ListObjects(StorageBackend storageBackend) {
        super(storageBackend);
    }

    @GET
    @Path("/{bucket}/")
    @Timed
    public Response listBuckets(@PathParam("bucket") String bucket) {
        LOG.info("listObjects {}", bucket);

        List<ListBucketResult.ContentsEntry> objects = getStorageBackend()
                .listObjects(bucket)
                .stream()
                .map(object -> new ListBucketResult.ContentsEntry(
                        object.getKey(),
                        object.getLastModified(),
                        object.getETag(),
                        object.getSize()))
                .collect(Collectors.toList());

        ListBucketResult response = new ListBucketResult(bucket, 0, 1000, objects);

        try {
            return Response.ok(JaxbMarshaller.marshall(response), MediaType.APPLICATION_XML_TYPE).build();
        } catch (Exception e) {
            LOG.error("Unable to create xml response body", e);
            return Response.serverError().build();
        }
    }
}
