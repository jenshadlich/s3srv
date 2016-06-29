package de.jeha.s3srv.errors;

/**
 * @author jenshadlich@googlemail.com
 */
public enum ErrorCodes {

    BAD_DIGEST(
            "BadDigest",
            "The Content-MD5 you specified did not match what we received.",
            400
    ),
    BUCKET_ALREADY_EXISTS(
            "BucketAlreadyExists",
            "The requested bucket name is not available. The bucket namespace is shared by all users of the system. Please select a different name and try again.",
            409
    ),
    INVALID_BUCKET_NAME(
            "InvalidBucketName",
            "The specified bucket is not valid.",
            400
    );

    private final String code;
    private final String description;
    private final int statusCode;

    ErrorCodes(String code, String description, int statusCode) {
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
