package de.jeha.s3srv.common.security;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author jenshadlich@googlemail.com
 */
public class AuthorizationTest {

    @Test
    public void test() {
        assertTrue(Authorization.checkAuthorization(
                "AWS foo:opnPRxWyGAkna6soKrR8OhGRfxQ=",
                "foo",
                "bar",
                "PUT",
                null,
                null,
                "Wed, 06 Jul 2016 15:53:17 GMT",
                "/test-bucket/"));
    }

}
