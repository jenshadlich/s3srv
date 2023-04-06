package de.jeha.s3srv.test;

import de.jeha.s3srv.common.http.Headers;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

public class TestHelper {

    public static HttpServletRequest getMockedRequest(String authorization) {
        final HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        when(mockedRequest.getHeader(Headers.AUTHORIZATION)).thenReturn(authorization);
        when(mockedRequest.getHeader(Headers.DATE)).thenReturn("Wed, 06 Jul 2016 15:53:17 GMT");
        when(mockedRequest.getMethod()).thenReturn("MOCK");

        return mockedRequest;
    }

}
