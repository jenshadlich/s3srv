package de.jeha.s3srv.operations.service;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

        String responseBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListAllMyBucketsResult xmlns=\"http://s3.amazonaws.com/doc/2006-03-01\">\n" +
                "  <Owner>\n" +
                "    <ID>foo</ID>\n" +
                "    <DisplayName>bar</DisplayName>\n" +
                "  </Owner>\n" +
                "  <Buckets>\n" +
                "    <Bucket>\n" +
                "      <Name>quotes</Name>\n" +
                "      <CreationDate>2006-02-03T16:45:09.000Z</CreationDate>\n" +
                "    </Bucket>\n" +
                "  </Buckets>\n" +
                "</ListAllMyBucketsResult>";

        LOG.info("listBuckets {}");

        return Response.ok(responseBody, MediaType.APPLICATION_XML_TYPE).build();
    }

}
