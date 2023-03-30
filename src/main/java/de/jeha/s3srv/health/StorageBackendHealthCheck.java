package de.jeha.s3srv.health;

import com.codahale.metrics.health.HealthCheck;
import de.jeha.s3srv.storage.HealthAware;

public class StorageBackendHealthCheck extends HealthCheck {

    private final HealthAware storageBackend;

    public StorageBackendHealthCheck(HealthAware storageBackend) {
        this.storageBackend = storageBackend;
    }

    @Override
    protected Result check() throws Exception {
        return storageBackend.isHealthy()
                ? HealthCheck.Result.healthy()
                : HealthCheck.Result.unhealthy("storage backend is unhealthy");
    }

}
