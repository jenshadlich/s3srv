package de.jeha.s3srv.common.s3;

import de.jeha.s3srv.common.http.Headers;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jenshadlich@googlemail.com
 */
public class RequestContext {

    private final boolean pathStyle;
    private final String bucket;
    private final String key;

    private RequestContext(boolean pathStyle, String bucket, String key) {
        this.pathStyle = pathStyle;
        this.bucket = bucket;
        this.key = key;
    }

    public static RequestContext build(HttpServletRequest request) {
        final String requestUri = request.getRequestURI();
        final String hostHeader = request.getHeader(Headers.HOST);
        final boolean pathStyle = detectPathStyle(request);

        String bucket = null;
        String key = null;

        String[] parts = StringUtils.split(requestUri, "/", 2);
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
        return new RequestContext(pathStyle, bucket, key);
    }

    private static boolean detectPathStyle(HttpServletRequest request) {
        final String serverName = request.getServerName();
        final String hostHeader = request.getHeader(Headers.HOST);

        return !(hostHeader.startsWith(serverName) && serverName.endsWith(".localhost"));
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
