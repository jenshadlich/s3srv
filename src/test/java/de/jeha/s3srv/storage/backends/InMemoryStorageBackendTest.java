package de.jeha.s3srv.storage.backends;

import de.jeha.s3srv.common.errors.BadDigestException;
import de.jeha.s3srv.model.S3Object;
import de.jeha.s3srv.storage.StorageBackend;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author jenshadlich@googlemail.com
 */
public class InMemoryStorageBackendTest {

    @Test
    public void test() throws IOException, BadDigestException {
        StorageBackend storageBackend = new InMemoryStorageBackend();

        final String content = "content";
        final S3Object object = storageBackend.createObject(
                "test-bucket",
                "test-object",
                new ByteArrayInputStream(content.getBytes("UTF-8")),
                content.length(),
                "mgNkuembtIDdJeHwKEyFVQ==",
                "text/plain");

        assertNotNull(object);
        // retrieve once
        assertEquals("content", IOUtils.toString(object.getInputStream(), "UTF-8"));
        // retrieve a 2nd time to check that the input stream was not consumed
        assertEquals("content", IOUtils.toString(object.getInputStream(), "UTF-8"));
    }
}
