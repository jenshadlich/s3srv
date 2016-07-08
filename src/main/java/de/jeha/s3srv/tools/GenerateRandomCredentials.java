package de.jeha.s3srv.tools;

import de.jeha.s3srv.common.security.Credentials;

/**
 * @author jenshadlich@googlemail.com
 */
public class GenerateRandomCredentials {

    public static void main(String... args) {
        Credentials credentials = Credentials.generateRandom();

        System.out.println(credentials.getAccessKey());
        System.out.println(credentials.getSecretKey());
    }

}
