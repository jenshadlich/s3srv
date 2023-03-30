package de.jeha.s3srv.common.acl;

public enum CannedACLs {

    PRIVATE("private"),
    PUBLIC_READ("public-read"),
    PUBLIC_READ_WRITE("public-read-write"),
    AUTHENTICATED_READ("authenticated-read");

    private final String value;

    CannedACLs(String value) {
        this.value = value;
    }
}
