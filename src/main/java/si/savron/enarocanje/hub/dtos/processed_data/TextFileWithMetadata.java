package si.savron.enarocanje.hub.dtos.processed_data;

public class TextFileWithMetadata {
    private String fileName;
    private String fixedFileName;
    private String content;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFixedFileName() {
        return fixedFileName;
    }

    public void setFixedFileName(String fixedFileName) {
        this.fixedFileName = fixedFileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
