package de.jeha.s3srv.operations.service;

import de.jeha.s3srv.api.ListAllMyBucketsResult;
import de.jeha.s3srv.common.http.Headers;
import de.jeha.s3srv.common.security.Credentials;
import de.jeha.s3srv.model.S3User;
import de.jeha.s3srv.storage.StorageBackend;
import de.jeha.s3srv.xml.JaxbMarshaller;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author jenshadlich@googlemail.com
 */
public class ListBucketsTest {

    @Test
    public void test() throws JAXBException, IOException {
        final HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        when(mockedRequest.getHeader(Headers.AUTHORIZATION)).thenReturn("AWS foo:s63buzYAu2AUptL/y4N7fuX0/L0=");
        when(mockedRequest.getHeader(Headers.DATE)).thenReturn("Wed, 06 Jul 2016 15:53:17 GMT");
        when(mockedRequest.getMethod()).thenReturn("MOCK");

        final S3User s3user = new S3User("1", "foo", new Credentials("foo", "bar"));
        final StorageBackend mockedStorageBackend = Mockito.mock(StorageBackend.class);
        when(mockedStorageBackend.getUserByAccessId("foo")).thenReturn(s3user);

        Response response = new ListBuckets(mockedStorageBackend).listBuckets(mockedRequest);

        verify(mockedStorageBackend, times(1)).listBuckets();
        assertEquals(200, response.getStatus());

        ListAllMyBucketsResult responseEntity =
                JaxbMarshaller.unmarshall((String) response.getEntity(), ListAllMyBucketsResult.class);
        assertEquals("1", responseEntity.getOwner().getId());
        assertEquals("foo", responseEntity.getOwner().getDisplayName());
        assertEquals(0, responseEntity.getBuckets().size());
    }

}
