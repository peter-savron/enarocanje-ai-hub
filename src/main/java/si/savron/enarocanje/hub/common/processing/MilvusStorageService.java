package si.savron.enarocanje.hub.common.processing;

import com.google.gson.Gson;
import io.milvus.pool.MilvusClientV2Pool;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.service.vector.request.InsertReq;
import io.milvus.v2.service.vector.response.InsertResp;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import si.savron.enarocanje.hub.models.vdb.ChunkVdbEntity;

import java.util.List;

@ApplicationScoped
public class MilvusStorageService {

    @Inject MilvusConfig milvusConfig;
    @Inject MilvusClientV2Pool milvusClientV2Pool;

    @Inject Gson gson;

    public InsertResp store(ChunkVdbEntity chunkVdbEntity) {
        MilvusClientV2 milvusClient = milvusClientV2Pool.getClient(milvusConfig.host());
        InsertReq insertReq = InsertReq.builder()
                .databaseName(milvusConfig.databaseName())
                .collectionName("data") // TODO code in entity
                .data(List.of(gson.toJsonTree(chunkVdbEntity).getAsJsonObject()))
                .build();
        return milvusClient.insert(insertReq);
    }
}
