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
    public static boolean isValid(String bucket)
            throws ValidationException {

        if (bucket == null) {
            throw new ValidationException("bucket must not be null");
        }

        if (bucket.length() < MIN_BUCKET_LENGTH || bucket.length() > MAX_BUCKET_LENGTH) {
            final String message = "bucket length must be between " + MIN_BUCKET_LENGTH + " and " + MAX_BUCKET_LENGTH;
            throw new ValidationException(message);
        }

        if (IPV4_PATTERN.matcher(bucket).matches()) {
            throw new ValidationException("bucket must no be formatted like an IP address");
        }

        int prev = -1;
        for (int i = 0; i < bucket.length(); ++i) {
            int next = bucket.charAt(i);

            if (Character.isUpperCase(next)) {
                throw new ValidationException("bucket must not contain uppercase characters");
            }

            if (Character.isWhitespace(next)) {
                throw new ValidationException("bucket must not contain whitespaces");
            }

            if (next == '.') {
                if (prev == -1) {
                    throw new ValidationException("bucket must not begin with a period");
                }
                if (prev == '.') {
                    throw new ValidationException("bucket must not contain two adjacent periods");
                }
                if (prev == '-') {
                    throw new ValidationException("bucket must not contain dashes next to periods");
                }
            } else {
                if (next == '-') {
                    if (prev == '.') {
                        throw new ValidationException("bucket must not contain dashes next to periods");
                    }
                    if (prev == -1) {
                        throw new ValidationException("bucket must not begin with a '-'");
                    }
                } else {
                    if (!Character.isDigit(next) && !Character.isLetter(next)) {
                        throw new ValidationException("bucket must not contain '" + (char) next + "'");
                    }
                }
            }

            prev = next;
        }

        if (prev == '.' || prev == '-') {
            throw new ValidationException("Bucket must not end with '-' or '.'");
        }

        return true;
    }

}
