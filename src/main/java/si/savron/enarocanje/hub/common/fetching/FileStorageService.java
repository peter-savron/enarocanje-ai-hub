package si.savron.enarocanje.hub.common.fetching;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import si.savron.enarocanje.hub.config.BucketConfig;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

// TODO later (bucked config should be improved)
@ApplicationScoped
public class FileStorageService {
    @Inject S3Client s3Client;
    @Inject BucketConfig bucket;

    /**
     * Stores a file
     * @param file file to store
     * @param path path in S3 storage
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void storeRawFile(ByteArrayOutputStream file, String path) {
        // create request
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket.enarocila())
                .key(path)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .build();

        RequestBody requestBody = RequestBody.fromBytes(file.toByteArray());

        try {
            s3Client.putObject(putObjectRequest, requestBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Storest text file to minio
     * @param processedFile text with content
     * @param path path inside s3 storage
     */
    public void storeProcessedFile(StringWriter processedFile, String path) {

        // create request
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket.enarocila())
                .key(path)
                .contentType(MediaType.TEXT_PLAIN)
                .build();

        RequestBody requestBody = RequestBody.fromString(processedFile.toString(), StandardCharsets.UTF_8);

        try {
            s3Client.putObject(putObjectRequest, requestBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getBuckets() {
        return s3Client.listBuckets().buckets().stream().map(Bucket::name).toList();
    }
}
