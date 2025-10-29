package si.savron.enarocanje.hub.common.fetching;

import static si.savron.enarocanje.hub.utils.StringDecodingUtils.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import si.savron.enarocanje.hub.dtos.enarocila.ZipDocumentation;
import si.savron.enarocanje.hub.models.DocumentEntity;
import si.savron.enarocanje.hub.models.NarociloEntity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FileReaderService {
    @Inject
    FileStorageService fileStorageService;
    @Inject EntityManager em;

    /**
     * Reads files and stores in object storage
     * @param zipFolder folder with files
     * @return entity with all data regarding stored files
     */
    @Transactional
    public NarociloEntity extractZipFolder(ZipDocumentation zipFolder, NarociloEntity narociloEntity) {
        List<DocumentEntity> files = new ArrayList<>();
        ZipEntryReader zipEntries = new ZipEntryReader(zipFolder.getZipStream());
        for (var zipEntry : zipEntries) {
            if (zipEntry.getName().endsWith(".zip")) { // TODO prefixes check to util class and util method to check filetype
                ZipDocumentation insideFolder = new ZipDocumentation();
                insideFolder.setZipStream(new ByteArrayInputStream(zipEntries.getCurrentFile().toByteArray()));
                insideFolder.setZipFolderName(zipEntry.getName());
                // recursively extract internal zips
                extractZipFolder(insideFolder, narociloEntity);
            } else {
                // TODO move creation of data to mapper
                DocumentEntity documentEntity = new DocumentEntity();
                documentEntity.setName(fixNonUtf8Encoding(zipEntry.getRawName()));
                documentEntity.setSource(zipFolder.getSource());
                documentEntity.setNarocilo(narociloEntity);
                em.persist(documentEntity);

                try {
                    fileStorageService.storeRawFile(zipEntries.getCurrentFile(), documentEntity.getS3Path());
                    documentEntity.setStored(true);
                } catch (Exception e) {
                    throw new RuntimeException("Could not store file: " + documentEntity.getId(), e);
                }
                files.add(
                        documentEntity
                        );
            }
        }
        if (narociloEntity.getDocuments() == null) {
            narociloEntity.setDocuments(new ArrayList<>());
        }
        narociloEntity.getDocuments().addAll(files);
        em.merge(narociloEntity);
        return narociloEntity;
    }
}
