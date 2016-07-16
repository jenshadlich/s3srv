package de.jeha.s3srv.storage;

/**
 * @author jenshadlich@googlemail.com
 */
public interface HealthAware {

    /**
     * @return true if in healthy condition
     */
    boolean isHealthy();

}
