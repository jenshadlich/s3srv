package de.jeha.s3srv.operations.buckets;

import de.jeha.s3srv.common.http.Headers;
import de.jeha.s3srv.common.security.Credentials;
import de.jeha.s3srv.model.S3User;
import de.jeha.s3srv.storage.StorageBackend;
import de.jeha.s3srv.test.TestHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreateBucketTest {

    @Test
    public void test() {
        final HttpServletRequest mockedRequest = TestHelper.getMockedRequest("AWS foo:4AvYjPkzjOQWie8IKBTRSuUayPI=");

        final S3User s3user = new S3User("1", "foo", new Credentials("foo", "bar"));
        final StorageBackend mockedStorageBackend = Mockito.mock(StorageBackend.class);
        when(mockedStorageBackend.getUserByAccessId("foo")).thenReturn(s3user);

        Response response = new CreateBucket(mockedStorageBackend).createBucket(mockedRequest, "test-bucket");

        verify(mockedStorageBackend, times(1)).createBucket(s3user, "test-bucket");
        assertEquals(200, response.getStatus());
        assertEquals("/test-bucket", response.getHeaderString(Headers.LOCATION));
    }

}
