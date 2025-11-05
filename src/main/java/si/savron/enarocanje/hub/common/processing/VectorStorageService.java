package si.savron.enarocanje.hub.common.processing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import si.savron.enarocanje.hub.models.vdb.ChunkVdbEntity;

import java.util.UUID;

@ApplicationScoped
public class VectorStorageService {
    @Inject MilvusStorageService milvusStorageService;
    @Inject TextEmbeddingService textEmbeddingService;

    public void storePlaintext(String plaintext) {
        var embedding = textEmbeddingService.embed(plaintext);
        ChunkVdbEntity chunkVdbEntity = new ChunkVdbEntity();
        chunkVdbEntity.setText(plaintext);
        chunkVdbEntity.setEmbedding(embedding);
        chunkVdbEntity.setId(UUID.randomUUID().toString());
        chunkVdbEntity.setData_id(UUID.randomUUID().toString());
        chunkVdbEntity.setSource_id(UUID.randomUUID().toString());

        milvusStorageService.store(chunkVdbEntity);
    }

}
