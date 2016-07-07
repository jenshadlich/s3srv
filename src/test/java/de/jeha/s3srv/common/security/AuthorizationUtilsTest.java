package de.jeha.s3srv.common.security;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author jenshadlich@googlemail.com
 */
public class AuthorizationUtilsTest {

    @Test
    public void testCheckAuthorization() {
        assertTrue(AuthorizationUtils.checkAuthorization(
                "AWS foo:opnPRxWyGAkna6soKrR8OhGRfxQ=",
                new Credentials("foo", "bar"),
                "PUT",
                null,
                null,
                "Wed, 06 Jul 2016 15:53:17 GMT",
                "/test-bucket/"));
    }

    @Test
    public void testExtractAccessKey() {
        assertEquals("foo", AuthorizationUtils.extractAccessKey("AWS foo:opnPRxWyGAkna6soKrR8OhGRfxQ="));
    }

}
