package si.savron.enarocanje.hub.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "ai-hub.buckets")
public interface BucketConfig {
    // TODO can be map instead of single values, so can have one method for all applications
    //   should be updated to be used in a common configuration, understood if one bucket is enough

    @WithName("enarocila")
    String enarocila();
}
