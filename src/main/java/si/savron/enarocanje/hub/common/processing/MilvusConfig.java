package si.savron.enarocanje.hub.common.processing;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "milvus")
public interface MilvusConfig {
    @WithName("host")
    String host();

    @WithName("token")
    String token();

    @WithName("database-name")
    String databaseName();
}
