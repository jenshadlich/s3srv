package de.jeha.s3srv.common.errors;

public class BadDigestException extends Exception {

    public BadDigestException(String message) {
        super(message);
    }

}
