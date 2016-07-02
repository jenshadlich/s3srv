package de.jeha.s3srv.common.errors;

/**
 * @author jenshadlich@googlemail.com
 */
public class BadDigestException extends Exception {

    public BadDigestException(String message) {
        super(message);
    }

}
