package si.savron.enarocanje.hub.models.vdb;

import java.util.List;

// TODO embedding validation
public class ChunkVdbEntity extends BaseVdbEntity {
    private String text;
    private List<Float> embedding;
    private String source_id;
    private String data_id;

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Float> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Float> embedding) {
        this.embedding = embedding;
    }
}
