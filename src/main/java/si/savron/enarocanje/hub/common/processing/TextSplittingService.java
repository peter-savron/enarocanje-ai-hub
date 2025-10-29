package si.savron.enarocanje.hub.common.processing;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.ModelType;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentBySentenceSplitter;
import dev.langchain4j.data.segment.TextSegment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TextSplittingService {

    @Inject private TextSplittingConfig textSplittingConfig;

    private final DocumentSplitter sentenceSplitter;
    private final EncodingRegistry encodingRegistry;

    public TextSplittingService() {
        this.sentenceSplitter = new DocumentBySentenceSplitter(1000, 100);
        this.encodingRegistry = Encodings.newDefaultEncodingRegistry();
    }

    public List<String> splitPlaintext(String plaintext) {
        List<String> sentences = sentenceSplitter.split(Document.document(plaintext))
                .stream().map(TextSegment::text).toList();

        List<String> chunks = new ArrayList<>();
        Encoding encoding = encodingRegistry.getEncodingForModel(ModelType.TEXT_EMBEDDING_3_SMALL);
        StringBuilder currentChunk = new StringBuilder();
        int currentLength = 0;
        StringBuilder nextChunk = new StringBuilder();
        int nextLength = 0;
        for (String sentence : sentences) {
            int len = encoding.countTokens(sentence);
            if (currentLength + len >= textSplittingConfig.chunkSize()) {
                chunks.add(currentChunk.toString());
                currentChunk = nextChunk;
                currentLength = nextLength;
                nextChunk = new StringBuilder();
                nextLength = 0;
            } else if (currentLength + len >= textSplittingConfig.chunkSize() - textSplittingConfig.chunkOverlap()) {
                nextChunk.append(sentence);
                nextLength += len;
            }
            currentChunk.append(sentence);
            currentLength += len;
        }
        if (!currentChunk.isEmpty()) {
            chunks.add(currentChunk.toString());
        }
        return chunks;
    }
}
