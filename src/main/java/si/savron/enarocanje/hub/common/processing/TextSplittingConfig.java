package si.savron.enarocanje.hub.common.processing;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "ai-hub.processing.text-splitting")
public interface TextSplittingConfig {
    @WithName("chunk-size")
    Integer chunkSize();
    @WithName("chunk-overlap")
    Integer chunkOverlap();
}
