package si.savron.enarocanje.hub.dtos.processed_data;

import java.io.ByteArrayOutputStream;

public class NarociloDocumentation {
    private NarociloDocumentationMetadata metadata;
    private ByteArrayOutputStream file;


    public NarociloDocumentationMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(NarociloDocumentationMetadata metadata) {
        this.metadata = metadata;
    }
}
