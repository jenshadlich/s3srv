package de.jeha.s3srv.operations.service;

import com.codahale.metrics.annotation.Timed;
import de.jeha.s3srv.operations.AbstractOperation;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @Produces(MediaType.APPLICATION_XML)
    @Timed
    public Response listBuckets() {
        LOG.info("listBuckets {}");
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
