package de.jeha.s3srv.common.security;

/**
 * @author jenshadlich@googlemail.com
 */
public class AuthorizationContext {

    private final String accessKey;
    private final boolean signatureValid;
    private boolean accessKeyValid = true; // TODO

    public AuthorizationContext(String accessKey, boolean signatureValid) {
        this.accessKey = accessKey;
        this.signatureValid = signatureValid;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public boolean isSignatureValid() {
        return signatureValid;
    }

    public boolean isAccessKeyValid() {
        return accessKeyValid;
    }

}
