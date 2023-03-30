package de.jeha.s3srv.tools;

import de.jeha.s3srv.common.security.Credentials;

public class GenerateRandomCredentials {

    public static void main(String... args) {
        final Credentials credentials = Credentials.generateRandom(false);
        credentials.print();
    }

}
