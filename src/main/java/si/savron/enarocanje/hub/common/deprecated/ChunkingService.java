package si.savron.enarocanje.hub.common.deprecated;

import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@Deprecated
@ApplicationScoped
public class ChunkingService {

    private final DocumentByParagraphSplitter paragraphSplitter;

    // TODO add Config
    public ChunkingService() {
        this.paragraphSplitter = new DocumentByParagraphSplitter(24000, 200, new JtokkitTokenizer());

    }
    /**
     * Chunks by lenght
     * @return chunks of string
     */
    public List<String> simpleChunking(String source) {
        return List.of(paragraphSplitter.split(source));
    }
}
