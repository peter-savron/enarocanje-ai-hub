package si.savron.enarocanje.hub.common.processing;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.embeddings.CreateEmbeddingResponse;
import com.openai.models.embeddings.Embedding;
import com.openai.models.embeddings.EmbeddingCreateParams;
import com.openai.models.embeddings.EmbeddingModel;
import io.smallrye.config.ConfigMapping;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Creates vector embeddings for text.
 */
@ApplicationScoped
public class TextEmbeddingService {
    @Inject TextEmbeddingConfig textEmbeddingConfig;

    private final OpenAIClient openAi;

    public TextEmbeddingService(@ConfigProperty(name = "ai-hub.processing.embedding.openai-key") String openAiApiKey) {
        this.openAi = OpenAIOkHttpClient.builder()
                .apiKey(openAiApiKey)
                .build();
    }

    public List<Float> embed(String text) {
        EmbeddingCreateParams request = EmbeddingCreateParams.builder()
                .model(textEmbeddingConfig.model())
                .input(text)
                .dimensions(textEmbeddingConfig.dimension())
                .build();
        CreateEmbeddingResponse response = openAi.embeddings().create(request);
        return response.data().stream().map(Embedding::embedding).findFirst()
                .orElseThrow(() -> new RuntimeException("No embedding gotten"));
    }

    public List<List<Float>> embedList(List<String> chunks) {
        EmbeddingCreateParams request = EmbeddingCreateParams.builder()
                .model(textEmbeddingConfig.model())
                .input(EmbeddingCreateParams.Input.ofArrayOfStrings(chunks))
                .dimensions(textEmbeddingConfig.dimension())
                .build();
        CreateEmbeddingResponse response = openAi.embeddings().create(request);
        return response.data().stream().map(Embedding::embedding).toList();
    }
}
