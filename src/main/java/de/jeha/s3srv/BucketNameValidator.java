package de.jeha.s3srv;

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
     * @return true if valid, otherwise false
     */
    public static boolean isValid(String bucket) {

        if (bucket == null || bucket.length() < MIN_BUCKET_LENGTH || bucket.length() > MAX_BUCKET_LENGTH) {
            return false;
        }

        if (IPV4_PATTERN.matcher(bucket).matches()) {
            return false;
        }

        return true;
    }

}
