package de.jeha.s3srv.common.security;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SignatureException;
import java.util.Locale;

/**
 * @author jenshadlich@googlemail.com
 */
public class AuthorizationUtils {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationUtils.class);

    public static boolean checkAuthorization(String givenAuthorization, Credentials credentials,
                                             String httpVerb, String contentMD5, String contentType,
                                             String date, String resource) {
        final String stringToSign =
                String.format(Locale.US,
                        "%s\n%s\n%s\n%s\n%s",
                        httpVerb,
                        Strings.nullToEmpty(contentMD5),
                        Strings.nullToEmpty(contentType),
                        date,
                        resource);
        LOG.debug("String to sign = '{}'", stringToSign);

        final String hmac;
        try {
            hmac = SignatureUtils.calculateHmacSha1(stringToSign, credentials.getSecretKey());
            LOG.debug("Calculated HMAC-SHA1 = '{}'", hmac);
        } catch (SignatureException e) {
            LOG.warn("Could not calculate HMAC-SHA1", e);
            return false;
        }

        return givenAuthorization.equals("AWS " + credentials.getAccessKey() + ":" + hmac);
    }

    public static String extractAccessKey(String authorization) {
        final String accessKeySigPair = StringUtils.substringAfter(authorization, "AWS ");
        if (accessKeySigPair != null) {
            String parts[] = StringUtils.split(accessKeySigPair, ':');
            if (parts != null && parts.length == 2) {
                return parts[0];
            }
        }
        return null;
    }

}
