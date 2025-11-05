package si.savron.enarocanje.hub.common;

import io.milvus.pool.MilvusClientV2Pool;
import io.milvus.pool.PoolConfig;
import io.milvus.v2.client.ConnectConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;

@ApplicationScoped
public class MilvusClientProducer {
    @ConfigProperty(name = "milvus.host")
    String host;
    @ConfigProperty(name = "milvus.token")
    String token;

    @Produces
    @ApplicationScoped
    public MilvusClientV2Pool produceMilvusClientPool() {
        PoolConfig poolConfig = PoolConfig.builder()
                .maxIdlePerKey(10) // max idle clients per key
                .maxTotalPerKey(20) // max total(idle + active) clients per key
                .maxTotal(100) // max total clients for all keys
                .maxBlockWaitDuration(Duration.ofSeconds(5L)) // getClient() will wait 5 seconds if no idle client available
                .minEvictableIdleDuration(Duration.ofSeconds(10L)) // if number of idle clients is larger than maxIdlePerKey, redundant idle clients will be evicted after 10 seconds
                .build();
        ConnectConfig connectConfig = ConnectConfig.builder()
                .uri(host)
                .token(token)
                .build();
        try {
            return new MilvusClientV2Pool(poolConfig, connectConfig);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
