package de.jeha.s3srv;

/**
 * @author jenshadlich@googlemail.com
 */
public class BucketNameValidator {

    public static boolean isValid(String bucket) {

        if (bucket == null || bucket.length() < 3 || bucket.length() > 63) {
            return false;
        }

        return false;
    }

}
