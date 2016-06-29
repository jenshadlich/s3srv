package de.jeha.s3srv.security;

import org.junit.Test;

import java.security.SignatureException;

import static org.junit.Assert.assertEquals;

/**
 * @author jenshadlich@googlemail.com
 */
public class SignatureTest {

    @Test
    public void test() throws SignatureException {
        final String secretKey = "bar";
        final String stringToSign = "HEAD\n" +
                "\n" +
                "application/octet-stream\n" +
                "Wed, 29 Jun 2016 20:18:33 GMT\n" +
                "/test-bucket/";

        String hmac = Signature.calculateHmacSha1(stringToSign, secretKey);

        assertEquals("DuOzETffRFZL4izMkgOAdjMfORU=", hmac);
    }

}
