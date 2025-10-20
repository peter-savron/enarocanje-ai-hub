package si.savron.enarocanje.hub.common.services;

import static si.savron.enarocanje.hub.utils.FileConversionUtil.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import si.savron.enarocanje.hub.common.ZipEntryReader;
import si.savron.enarocanje.hub.config.BucketConfig;
import si.savron.enarocanje.hub.dtos.enarocila.ZipDocumentation;
import si.savron.enarocanje.hub.models.DocumentEntity;
import si.savron.enarocanje.hub.models.NarociloEntity;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class FileProcessingService {
    @Inject FileReaderService fileReaderService;
    @Inject FileStorageService fileStorageService;
    @Inject EntityManager em;
    @Inject BucketConfig bucketConfig;
    @Inject S3Client s3Client;

    // TODO convert file to plaintext

    // TODO vectorize plaintext and store to milvus

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public NarociloEntity processFiles (NarociloEntity narociloEntity) {
        narociloEntity.getDocuments().forEach(this::processDocument);
        return em.merge(narociloEntity);
    }

    private void processDocument(DocumentEntity documentEntity) {
        if (!documentEntity.isProcessed()) {
            if (documentEntity.isStored()) {
                GetObjectRequest request = GetObjectRequest.builder()
                        .bucket(bucketConfig.enarocila())
                        .key(documentEntity.getS3Path())
                        .build();

                try {
                    fileStorageService.storeProcessedFile(
                            fileToText(s3Client.getObject(request)),
                            documentEntity.getProcessedS3Path()
                    );
                    documentEntity.setProcessed(true);
                } catch (Exception e) {
                    throw new RuntimeException("could not retrieve or store file with id: " + documentEntity.getId(), e);
                }
            } else {
                throw new RuntimeException("document is not stored id: " + documentEntity.getId());
            }
        }
    }
}
