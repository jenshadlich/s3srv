package de.jeha.s3srv.common.errors;

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
    BUCKET_NOT_EMPTY(
            "BucketNotEmpty",
            "The bucket you tried to delete is not empty.",
            409
    ),
    INVALID_ACCESS_KEY_ID(
            "InvalidAccessKeyId",
            "The AWS access key Id you provided does not exist in our records.",
            403
    ),
    INVALID_BUCKET_NAME(
            "InvalidBucketName",
            "The specified bucket is not valid.",
            400
    ),
    INTERNAL_ERROR(
            "InternalError",
            "We encountered an internal error. Please try again.",
            500
    ),
    SERVICE_UNAVAILABLE(
            "ServiceUnavailable",
            "Reduce your request rate.",
            503
    ),
    SIGNATURE_DOES_NOT_MATCH(
            "SignatureDoesNotMatch",
            "The request signature we calculated does not match the signature you provided. Check your secret access key and signing method.",
            403
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
