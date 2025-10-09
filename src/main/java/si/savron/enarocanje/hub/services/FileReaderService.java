package si.savron.enarocanje.hub.services;

import jakarta.enterprise.context.ApplicationScoped;
import si.savron.enarocanje.hub.common.ZipEntryReader;
import si.savron.enarocanje.hub.utils.DocumentUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FileReaderService {

    public List<String> getFileNames(InputStream zipFolder) throws Exception {
        List<String> fileNames = new ArrayList<>();
        if (true){
            ZipEntryReader zipEntries = new ZipEntryReader(zipFolder);
            for (var zipEntry : zipEntries) {
                fileNames.add(zipEntry.getName());
            }
        }
        return fileNames;
    }
}
