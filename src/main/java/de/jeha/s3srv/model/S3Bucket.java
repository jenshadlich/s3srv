package de.jeha.s3srv.model;

import java.time.Instant;

public class S3Bucket {

    private final S3User owner;
    private final String name;
    private final Instant creationDate;

    public S3Bucket(S3User owner, String name) {
        this.owner = owner;
        this.name = name;
        this.creationDate = Instant.now();
    }

    public S3User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public boolean isOwnedBy(S3User user) {
        return owner.getId().equals(user.getId());
    }

}
