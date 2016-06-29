package de.jeha.s3srv.security;

import org.apache.commons.codec.digest.HmacUtils;

import javax.crypto.Mac;
import java.security.SignatureException;
import java.util.Base64;

/**
 * @author jenshadlich@googlemail.com
 */
public class Signature {

    /**
     * Computes RFC 2104-compliant HMAC signature.
     *
     * @param data The data to be signed.
     * @param key  The signing key.
     * @return The Base64-encoded RFC 2104-compliant HMAC signature.
     * @throws SignatureException if signing fails
     */
    public static String calculateHmacSha1(String data, String key) throws SignatureException {
        try {
            Mac mac = HmacUtils.getHmacSha1(key.getBytes());
            return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC: " + e.getMessage());
        }
    }
}
