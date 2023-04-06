package de.jeha.s3srv.operations.buckets;

import de.jeha.s3srv.common.security.Credentials;
import de.jeha.s3srv.model.S3Bucket;
import de.jeha.s3srv.model.S3User;
import de.jeha.s3srv.storage.StorageBackend;
import de.jeha.s3srv.test.TestHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetBucketAclTest {

    @Test
    public void test() {
        final HttpServletRequest mockedRequest = TestHelper.getMockedRequest("AWS foo:Xe4Ju4lf0a/cmXi5eznuxHQyGBs=");

        final S3User s3user = new S3User("1", "foo", new Credentials("foo", "bar"));
        final StorageBackend mockedStorageBackend = Mockito.mock(StorageBackend.class);
        when(mockedStorageBackend.getUserByAccessId("foo")).thenReturn(s3user);
        when(mockedStorageBackend.doesBucketExist("test-bucket")).thenReturn(true);
        final S3Bucket mockedBucket = Mockito.mock(S3Bucket.class);
        when(mockedStorageBackend.getBucket("test-bucket")).thenReturn(mockedBucket);
        when(mockedBucket.isOwnedBy(s3user)).thenReturn(true);

        Response response = new GetBucketACL(mockedStorageBackend).getBucketACL(mockedRequest, "test-bucket");

        assertEquals(200, response.getStatus());
    }

}
