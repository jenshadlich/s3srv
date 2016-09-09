package de.jeha.s3srv.resources;

import de.jeha.s3srv.common.http.Headers;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jenshadlich@googlemail.com
 */
public class S3Context {

    private final boolean pathStyle;
    private final String bucket;
    private final String key;

    private S3Context(boolean pathStyle, String bucket, String key) {
        this.pathStyle = pathStyle;
        this.bucket = bucket;
        this.key = key;
    }

    public static S3Context build(HttpServletRequest request) {
        final String serverName = request.getServerName();
        final String hostHeader = request.getHeader(Headers.HOST);
        final String requestUri = request.getRequestURI();

        final boolean pathStyle = !(hostHeader.startsWith(serverName) && serverName.endsWith(".localhost")); // TODO

        String bucket = null;
        String key = null;

        String[] parts = StringUtils.split(requestUri, '/');
        if (pathStyle) {
            if (parts.length >= 1) {
                bucket = parts[0];
            }
            if (parts.length >= 2) {
                key = parts[1];
            }
        } else {
            bucket = StringUtils.substringBefore(hostHeader, ".");
            if (parts.length >= 1) {
                key = parts[0];
            }
        }
        return new S3Context(pathStyle, bucket, key);
    }

    public boolean isPathStyle() {
        return pathStyle;
    }

    public String getBucket() {
        return bucket;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "S3Context{" +
                "pathStyle=" + pathStyle +
                ", bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
