package de.jeha.s3srv.storage;

public interface HealthAware {

    /**
     * @return true if in healthy condition
     */
    boolean isHealthy();

}
