package si.savron.enarocanje.hub.common.processing;

import static si.savron.enarocanje.hub.utils.FileConversionUtil.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import si.savron.enarocanje.hub.common.fetching.FileStorageService;
import si.savron.enarocanje.hub.config.BucketConfig;
import si.savron.enarocanje.hub.models.DocumentEntity;
import si.savron.enarocanje.hub.models.NarociloEntity;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.StringWriter;

/**
 * Scope of this class is to retrieve raw document and to process it to most optimal size and form
 * for feeding to LLM models.
 */
@ApplicationScoped
public class FileProcessingService {
    @Inject FileStorageService fileStorageService;
    @Inject TextEmbeddingService textEmbeddingService;
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
                    StringWriter processedText = fileToText(s3Client.getObject(request));
                    fileStorageService.storeProcessedFile(
                            processedText,
                            documentEntity.getProcessedS3Path()
                    );
                    // TODO chunk and store text
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
