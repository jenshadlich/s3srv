package de.jeha.s3srv.common.s3;

import de.jeha.s3srv.common.http.Headers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author jenshadlich@googlemail.com
 */
public class RequestContextTest {

    @Test
    public void test() {
        final HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        when(mockedRequest.getServerName()).thenReturn("localhost");
        when(mockedRequest.getHeader(Headers.HOST)).thenReturn("");
        when(mockedRequest.getRequestURI()).thenReturn("/test-bucket/test/object");

        RequestContext context = RequestContext.build(mockedRequest);
        assertTrue(context.isPathStyle());
        assertEquals("test-bucket", context.getBucket());
        assertEquals("test/object", context.getKey());
    }

}
