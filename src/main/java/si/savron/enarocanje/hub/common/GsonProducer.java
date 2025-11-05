package si.savron.enarocanje.hub.common;

import com.google.gson.Gson;
import io.milvus.pool.MilvusClientV2Pool;
import io.milvus.pool.PoolConfig;
import io.milvus.v2.client.ConnectConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;

@ApplicationScoped
public class GsonProducer {

    @Produces
    @ApplicationScoped
    public Gson produceGson() {
        return new Gson();
    }
}
