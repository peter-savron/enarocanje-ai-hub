package si.savron.enarocanje.hub.common.processing;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "ai-hub.processing.embedding")
public interface TextEmbeddingConfig {
    @WithName("openai-key")
    String openaiKey();
    @WithName("model")
    String model();
    @WithName("dimension")
    Long dimension();
}
