package si.savron.enarocanje.hub.dtos.enarocila;

import java.io.InputStream;

public class ZipDocumentation {
    private InputStream zipStream;
    private String zipFolderName;
    private String source;

    public InputStream getZipStream() {
        return zipStream;
    }

    public void setZipStream(InputStream zipStream) {
        this.zipStream = zipStream;
    }

    public String getZipFolderName() {
        return zipFolderName;
    }

    public void setZipFolderName(String zipFolderName) {
        this.zipFolderName = zipFolderName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    // foldername
    // other metadata when needed
}
