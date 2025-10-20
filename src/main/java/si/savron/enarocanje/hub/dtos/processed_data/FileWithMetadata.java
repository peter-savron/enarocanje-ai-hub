package si.savron.enarocanje.hub.dtos.processed_data;

import java.io.ByteArrayOutputStream;

public class FileWithMetadata {
    private String fileName;
    private String fixedFileName;
    private String source;
    private ByteArrayOutputStream content;

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

    public ByteArrayOutputStream getContent() {
        return content;
    }

    public void setContent(ByteArrayOutputStream content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
