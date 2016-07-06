package de.jeha.s3srv.common.security;

/**
 * @author jenshadlich@googlemail.com
 */
public class Credentials {

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

}
