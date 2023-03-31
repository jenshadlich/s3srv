package de.jeha.s3srv.common.security;

import org.junit.jupiter.api.Test;

import java.security.SignatureException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignatureUtilsTest {

    @Test
    public void test() throws SignatureException {
        final String secretKey = "bar";
        final String stringToSign = "HEAD\n" +      // HTTP verb
                "\n" +                              // Content-MD5, here: empty
                "application/octet-stream\n" +      // Content-Type
                "Wed, 29 Jun 2016 20:18:33 GMT\n" + // Date
                "/test-bucket/";                    // CanonicalizedResource, here: request uri

        String hmac = SignatureUtils.calculateHmacSha1(stringToSign, secretKey);

        assertEquals("DuOzETffRFZL4izMkgOAdjMfORU=", hmac);
    }

}
