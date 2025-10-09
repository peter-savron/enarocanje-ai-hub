package si.savron.enarocanje.hub.dtos.processed_data;

public class NarociloDocumentationMetadata {
    private String fileName;
    private Integer obrazecId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getObrazecId() {
        return obrazecId;
    }

    public void setObrazecId(Integer obrazecId) {
        this.obrazecId = obrazecId;
    }
}
