package de.jeha.s3srv.operations;

import de.jeha.s3srv.common.errors.ErrorCodes;
import de.jeha.s3srv.api.ErrorResponse;
import de.jeha.s3srv.xml.JaxbMarshaller;
import de.jeha.s3srv.storage.StorageBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * @author jenshadlich@googlemail.com
 */
public abstract class AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractOperation.class);

    private final StorageBackend storageBackend;

    protected AbstractOperation(StorageBackend storageBackend) {
        this.storageBackend = storageBackend;
    }

    protected StorageBackend getStorageBackend() {
        return storageBackend;
    }

    protected Response createErrorResponse(ErrorCodes errorCode, String resource, String requestId) {
        ErrorResponse error = new ErrorResponse(errorCode.getCode(), errorCode.getDescription(), resource, requestId);
        try {
            String errorResponseXml = JaxbMarshaller.marshall(error);
            return Response
                    .status(errorCode.getStatusCode())
                    .entity(errorResponseXml)
                    .type(MediaType.APPLICATION_XML_TYPE)
                    .build();
        } catch (IOException | JAXBException e) {
            LOG.error("Unable to create xml error response body", e);
            // TODO: add compliant internal server error
            return Response.serverError().build();
        }
    }

}
