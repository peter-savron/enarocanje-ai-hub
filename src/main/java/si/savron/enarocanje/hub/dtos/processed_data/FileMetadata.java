package si.savron.enarocanje.hub.dtos.processed_data;

/**
 * Basic data about files
 */
public class FileMetadata {
    private String fileName;
    private String source;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
