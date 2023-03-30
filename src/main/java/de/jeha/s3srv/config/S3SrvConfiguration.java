package de.jeha.s3srv.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.jeha.s3srv.common.security.Credentials;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class S3SrvConfiguration extends Configuration {

    @JsonProperty
    @NotNull
    @Length(min = 20, max = 20)
    private String accessKey;

    @JsonProperty
    @NotNull
    @Length(min = 40, max = 40)
    private String secretKey;

    @NotNull
    @Valid
    private StorageBackendFactory storageBackend;

    @JsonProperty
    private boolean jerseyLoggingFilterEnabled = false;

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Credentials buildCredentials() {
        return new Credentials(accessKey, secretKey);
    }

    public StorageBackendFactory getStorageBackend() {
        return storageBackend;
    }

    public boolean isJerseyLoggingFilterEnabled() {
        return jerseyLoggingFilterEnabled;
    }

}
