package de.jeha.s3srv.model;

import de.jeha.s3srv.common.security.Credentials;

/**
 * @author jenshadlich@googlemail.com
 */
public class S3User {

    private final String id;
    private final Credentials credentials;

    public S3User(String id, Credentials credentials) {
        this.id = id;
        this.credentials = credentials;
    }

    public String getId() {
        return id;
    }

    public Credentials getCredentials() {
        return credentials;
    }

}
