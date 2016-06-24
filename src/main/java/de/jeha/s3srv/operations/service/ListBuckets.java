package de.jeha.s3srv.operations.service;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author jenshadlich@googlemail.com
 */
@Path("/")
public class ListBuckets {

    private static final Logger LOG = LoggerFactory.getLogger(ListBuckets.class);

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    @Timed
    public void listBuckets() {
        LOG.info("listBuckets {}");
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
