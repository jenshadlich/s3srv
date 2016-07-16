package de.jeha.s3srv.common.security;

import de.jeha.s3srv.model.S3User;

/**
 * @author jenshadlich@googlemail.com
 */
public class AuthorizationContext {

    private final S3User user;
    private final boolean signatureValid;

    public AuthorizationContext(S3User user, boolean signatureValid) {
        this.user = user;
        this.signatureValid = signatureValid;
    }

    public S3User getUser() {
        return user;
    }

    public boolean isSignatureValid() {
        return signatureValid;
    }

    public boolean isUserValid() {
        return user != null;
    }

}
