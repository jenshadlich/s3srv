package de.jeha.s3srv.operations.buckets;

import de.jeha.s3srv.common.http.Headers;
import de.jeha.s3srv.common.security.Credentials;
import de.jeha.s3srv.model.S3Bucket;
import de.jeha.s3srv.model.S3User;
import de.jeha.s3srv.storage.StorageBackend;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExistsBucketTest {

    @Test
    public void test() {
        final HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        when(mockedRequest.getHeader(Headers.AUTHORIZATION)).thenReturn("AWS foo:4AvYjPkzjOQWie8IKBTRSuUayPI=");
        when(mockedRequest.getHeader(Headers.DATE)).thenReturn("Wed, 06 Jul 2016 15:53:17 GMT");
        when(mockedRequest.getMethod()).thenReturn("MOCK");

        final S3User s3user = new S3User("1", "foo", new Credentials("foo", "bar"));
        final StorageBackend mockedStorageBackend = Mockito.mock(StorageBackend.class);
        when(mockedStorageBackend.getUserByAccessId("foo")).thenReturn(s3user);
        when(mockedStorageBackend.doesBucketExist("test-bucket")).thenReturn(true);
        final S3Bucket mockedBucket = Mockito.mock(S3Bucket.class);
        when(mockedStorageBackend.getBucket("test-bucket")).thenReturn(mockedBucket);
        when(mockedBucket.isOwnedBy(s3user)).thenReturn(true);

        Response response = new ExistsBucket(mockedStorageBackend).doesBucketExist(mockedRequest, "test-bucket");

        verify(mockedStorageBackend, times(1)).doesBucketExist("test-bucket");
        assertEquals(200, response.getStatus());
    }

}
