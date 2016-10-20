package de.jeha.s3srv.operations;

import de.jeha.s3srv.api.ErrorResponse;
import de.jeha.s3srv.common.errors.ErrorCodes;
import de.jeha.s3srv.common.http.Headers;
import de.jeha.s3srv.common.security.AuthorizationContext;
import de.jeha.s3srv.common.security.AuthorizationUtils;
import de.jeha.s3srv.model.S3User;
import de.jeha.s3srv.storage.StorageBackend;
import de.jeha.s3srv.xml.JaxbMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
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
        LOG.info("invalid request: error code {}, status code {}", errorCode.getCode(), errorCode.getStatusCode());
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

    protected AuthorizationContext checkAuthorization(HttpServletRequest request, String resource) {
        final String date = request.getHeader(Headers.DATE);
        final String authorization = request.getHeader(Headers.AUTHORIZATION);
        final String contentMD5 = request.getHeader(Headers.CONTENT_MD5);
        final String contentType = request.getHeader(Headers.CONTENT_TYPE);
        final String host = request.getHeader(Headers.HOST);

        LOG.info("Date: {}", date);
        LOG.info("Authorization: {}", authorization);
        LOG.info("Host: {}", host);

        if (authorization == null) {
            return new AuthorizationContext(new S3User("anonymous", "", null), true);
        }

        final String accessKey = AuthorizationUtils.extractAccessKey(authorization);
        final S3User user = getStorageBackend().getUserByAccessId(accessKey);
        boolean signatureValid = false;

        if (user != null) {
            signatureValid = AuthorizationUtils.checkAuthorization(
                    authorization,
                    user.getCredentials(),
                    request.getMethod(),
                    contentMD5,
                    contentType,
                    date,
                    resource);
        }

        return new AuthorizationContext(user, signatureValid);
    }

}
