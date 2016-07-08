package de.jeha.s3srv.common.security;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author jenshadlich@googlemail.com
 */
public class Credentials {

    private final static String ACCESS_KEY_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static int ACCESS_KEY_LENGTH = 20;

    private final static String SECRET_KEY_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz/+";
    private final static int SECRET_KEY_LENGTH = 40;

    private final String accessKey;
    private final String secretKey;

    public Credentials(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Generates a random pair of access key and secret key in a pretty naive way.
     *
     * @return randomly generated credentials
     */
    public static Credentials generateRandom() {
        String accessKey = RandomStringUtils.random(ACCESS_KEY_LENGTH, ACCESS_KEY_CHARS);
        String secretKey = RandomStringUtils.random(SECRET_KEY_LENGTH, SECRET_KEY_CHARS);

        return new Credentials(accessKey, secretKey);
    }

}
