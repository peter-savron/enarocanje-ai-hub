package si.savron.enarocanje.hub.common.deprecated;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.ModelType;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.TokenCountEstimator;

@Deprecated
public class JtokkitTokenizer implements TokenCountEstimator {

    private final EncodingRegistry encodingRegistry;
    private final Encoding encoding;

    public JtokkitTokenizer(){
        encodingRegistry = Encodings.newDefaultEncodingRegistry();
        // TODO set in application properties
        encoding = encodingRegistry.getEncodingForModel(ModelType.TEXT_EMBEDDING_3_SMALL);
    }

    @Override
    public int estimateTokenCountInText(String s) {
        return encoding.countTokens(s);
    }

    @Override
    public int estimateTokenCountInMessage(ChatMessage chatMessage) {
        return 0;
    }

    @Override
    public int estimateTokenCountInMessages(Iterable<ChatMessage> iterable) {
        return 0;
    }
}
