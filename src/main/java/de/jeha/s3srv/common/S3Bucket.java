package de.jeha.s3srv.common;

import java.time.Instant;

/**
 * @author jenshadlich@googlemail.com
 */
public class S3Bucket {

    private final String name;
    private final Instant creationDate;

    public S3Bucket(String name) {
        this.name = name;
        this.creationDate = Instant.now();
    }

    public String getName() {
        return name;
    }

    public Instant getCreationDate() {
        return creationDate;
    }
    
}
