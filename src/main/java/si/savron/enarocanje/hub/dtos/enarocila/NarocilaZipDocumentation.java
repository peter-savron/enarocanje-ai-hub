package si.savron.enarocanje.hub.dtos.enarocila;

import java.io.InputStream;

public class NarocilaZipDocumentation {
    private InputStream zipStream;
    private String zipFolderName;

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
    // foldername
    // other metadata when needed
}
