package de.jeha.s3srv.model;

import de.jeha.s3srv.common.security.Credentials;

public class S3User {

    private final String id;
    private final String displayName;
    private final Credentials credentials;

    public S3User(String id, String displayName, Credentials credentials) {
        this.id = id;
        this.displayName = displayName;
        this.credentials = credentials;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Credentials getCredentials() {
        return credentials;
    }

}
