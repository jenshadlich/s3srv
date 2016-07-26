package de.jeha.s3srv.common;

import javax.validation.ValidationException;
import java.util.regex.Pattern;

/**
 * @author jenshadlich@googlemail.com
 */
public class BucketNameValidator {

    private static final int MIN_BUCKET_LENGTH = 3;
    private static final int MAX_BUCKET_LENGTH = 63;
    private static final Pattern IPV4_PATTERN = Pattern.compile("(\\d+\\.){3}\\d+");

    /**
     * Validate the given bucket name.
     *
     * @param bucket name of bucket
     * @return true if valid
     * @throws ValidationException if not valid
     */
    public static boolean isValid(String bucket) {
        return isValid(bucket, true);
    }

    /**
     * Validate the given bucket name.
     *
     * @param bucket    name of bucket
     * @param exception throw exception if true
     * @return true if valid
     * @throws ValidationException if not valid and exception is 'true'
     */
    public static boolean isValid(String bucket, boolean exception)
            throws ValidationException {

        if (bucket == null) {
            return throwValidationExceptionOrReturnFalse(exception, "bucket must not be null");
        }

        if (bucket.length() < MIN_BUCKET_LENGTH || bucket.length() > MAX_BUCKET_LENGTH) {
            final String message = "bucket length must be between " + MIN_BUCKET_LENGTH + " and " + MAX_BUCKET_LENGTH;
            return throwValidationExceptionOrReturnFalse(exception, message);
        }

        if (IPV4_PATTERN.matcher(bucket).matches()) {
            return throwValidationExceptionOrReturnFalse(exception, "bucket must no be formatted like an IP address");
        }

        int prev = -1;
        for (int i = 0; i < bucket.length(); ++i) {
            int next = bucket.charAt(i);

            if (Character.isUpperCase(next)) {
                return throwValidationExceptionOrReturnFalse(exception, "bucket must not contain uppercase characters");
            }

            if (Character.isWhitespace(next)) {
                return throwValidationExceptionOrReturnFalse(exception, "bucket must not contain whitespaces");
            }

            if (next == '.') {
                if (prev == -1) {
                    return throwValidationExceptionOrReturnFalse(exception, "bucket must not begin with a period");
                }
                if (prev == '.') {
                    return throwValidationExceptionOrReturnFalse(exception, "bucket must not contain two adjacent periods");
                }
                if (prev == '-') {
                    return throwValidationExceptionOrReturnFalse(exception, "bucket must not contain dashes next to periods");
                }
            } else {
                if (next == '-') {
                    if (prev == '.') {
                        return throwValidationExceptionOrReturnFalse(exception, "bucket must not contain dashes next to periods");
                    }
                    if (prev == -1) {
                        return throwValidationExceptionOrReturnFalse(exception, "bucket must not begin with a '-'");
                    }
                } else {
                    if (!Character.isDigit(next) && !Character.isLetter(next)) {
                        return throwValidationExceptionOrReturnFalse(exception, "bucket must not contain '" + (char) next + "'");
                    }
                }
            }

            prev = next;
        }

        if (prev == '.' || prev == '-') {
            return throwValidationExceptionOrReturnFalse(exception, "bucket must not end with '-' or '.'");
        }

        return true;
    }

    /**
     * @param exception if true throw a ValidationException with the given message otherwise return false
     * @param message   the exception message
     * @return always false
     */
    private static boolean throwValidationExceptionOrReturnFalse(boolean exception, String message) {
        if (exception) {
            throw new ValidationException(message);
        }
        return false;
    }

}
