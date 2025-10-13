package si.savron.enarocanje.hub.services;

import static si.savron.enarocanje.hub.utils.StringDecodingUtils.*;
import static si.savron.enarocanje.hub.utils.FileConversionUtil.*;

import jakarta.enterprise.context.ApplicationScoped;
import si.savron.enarocanje.hub.common.ZipEntryReader;
import si.savron.enarocanje.hub.dtos.enarocila.NarocilaZipDocumentation;
import si.savron.enarocanje.hub.dtos.processed_data.TextFileWithMetadata;
import si.savron.enarocanje.hub.utils.DocumentUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FileReaderService {

    public List<TextFileWithMetadata> getFileNames(NarocilaZipDocumentation zipFolder) throws Exception {
        List<TextFileWithMetadata> files = new ArrayList<>(); // replace with list of text data from files, store raw data to minio
        // put file to a queue to process it (should be handled with separated service if big processing
        if (true){ // TODO check if it really is zip folder
            ZipEntryReader zipEntries = new ZipEntryReader(zipFolder.getZipStream());
            for (var zipEntry : zipEntries) {
                if (zipEntry.getName().endsWith(".zip")) { // TODO prefixes to util class and util method to check filetype
                    NarocilaZipDocumentation insideFolder = new NarocilaZipDocumentation();
                    insideFolder.setZipStream(new ByteArrayInputStream(zipEntries.getCurrentFile().toByteArray()));
                    insideFolder.setZipFolderName(zipEntry.getName());
                    files.addAll(getFileNames(insideFolder));
                } else {
                    TextFileWithMetadata fileWithMetadata = new TextFileWithMetadata();
                    fileWithMetadata.setFileName(zipEntry.getName());
                    fileWithMetadata.setFixedFileName(
                            fixNonUtf8Encoding(zipEntry.getRawName())
                    );
                    var currentFile = new ByteArrayInputStream(zipEntries.getCurrentFile().toByteArray());
                    fileWithMetadata.setContent(fileToText(currentFile).toString());
                    files.add(fileWithMetadata);
                }
            }
        }
        return files;
    }
}
