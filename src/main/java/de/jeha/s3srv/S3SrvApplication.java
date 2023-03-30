package de.jeha.s3srv;

import de.jeha.s3srv.common.security.Credentials;
import de.jeha.s3srv.config.S3SrvConfiguration;
import de.jeha.s3srv.health.StorageBackendHealthCheck;
import de.jeha.s3srv.resources.S3OperationsFacade;
import de.jeha.s3srv.storage.StorageBackend;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ServerProperties;

import java.util.Locale;
import java.util.logging.Logger;

public class S3SrvApplication extends Application<S3SrvConfiguration> {

    private static final String APPLICATION_NAME = "s3srv";

    public static void main(String... args) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        new S3SrvApplication().run(args);
    }

    @Override
    public String getName() {
        return APPLICATION_NAME;
    }

    @Override
    public void initialize(Bootstrap<S3SrvConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(S3SrvConfiguration configuration, Environment environment) {
        final Credentials credentials = configuration.buildCredentials();
        credentials.print();
        final StorageBackend storageBackend = configuration.getStorageBackend().build(credentials);

        environment.healthChecks().register("StorageBackend", new StorageBackendHealthCheck(storageBackend));

        environment.jersey().register(new S3OperationsFacade(storageBackend));

        if (configuration.isJerseyLoggingFilterEnabled()) {
            // print request / response headers
            // printEntity=false because printing consumes the input stream and breaks object creation (PUT Bucket)
            environment.jersey().register(new LoggingFilter(Logger.getLogger("InboundRequestResponse"), false));
        }

        environment.jersey().disable(ServerProperties.WADL_FEATURE_DISABLE);
    }

}
