package de.jeha.s3srv;

import de.jeha.s3srv.config.S3SrvConfiguration;
import de.jeha.s3srv.operations.buckets.CreateBucket;
import de.jeha.s3srv.operations.service.ListBuckets;
import de.jeha.s3srv.storage.StorageBackend;
import de.jeha.s3srv.storage.backends.InMemoryStorageBackend;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.ServerProperties;

import java.util.Locale;

/**
 * @author jenshadlich@googlemail.com
 */
public class S3SrvService extends Application<S3SrvConfiguration> {

    private static final String APPLICATION_NAME = "s3srv";

    public static void main(String... args) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        new S3SrvService().run(args);
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
        StorageBackend storageBackend = new InMemoryStorageBackend();

        environment.jersey().register(new CreateBucket(storageBackend));
        environment.jersey().register(new ListBuckets(storageBackend));

        environment.jersey().disable(ServerProperties.WADL_FEATURE_DISABLE);
    }

}
