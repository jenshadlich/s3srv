package de.jeha.s3srv.operations.service;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.api.ListAllMyBucketsResponse;
import de.jeha.s3srv.jaxb.JaxbMarshaller;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jenshadlich@googlemail.com
 */
@Path("/")
public class ListBuckets extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(ListBuckets.class);

    public ListBuckets(StorageBackend storageBackend) {
        super(storageBackend);
    }

    @GET
    @Path("/")
    @Timed
    public Response listBuckets() {
        LOG.info("listBuckets {}");

        List<ListAllMyBucketsResponse.Entry> buckets = getStorageBackend()
                .listBuckets()
                .stream()
                .map(bucket -> new ListAllMyBucketsResponse.Entry(bucket.getName(), bucket.getCreationDate()))
                .collect(Collectors.toList());

        ListAllMyBucketsResponse response = new ListAllMyBucketsResponse(new ListAllMyBucketsResponse.Owner("foo", "bar"), buckets);

        try {
            return Response.ok(JaxbMarshaller.marshall(response), MediaType.APPLICATION_XML_TYPE).build();
        } catch (Exception e) {
            LOG.error("Unable to create xml response body", e);
            return Response.serverError().build();
        }
    }

}
