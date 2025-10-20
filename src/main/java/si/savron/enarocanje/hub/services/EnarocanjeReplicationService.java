package si.savron.enarocanje.hub.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import si.savron.enarocanje.hub.common.services.DocumentFetchService;
import si.savron.enarocanje.hub.common.services.FileProcessingService;
import si.savron.enarocanje.hub.common.services.FileReaderService;
import si.savron.enarocanje.hub.dtos.processed_data.SifObrazecProcessedWithMetadata;
import si.savron.enarocanje.hub.mappers.sif.SifMapper;
import si.savron.enarocanje.hub.models.DocumentEntity;
import si.savron.enarocanje.hub.models.NarociloEntity;

import java.util.List;

/**
 * Serves the purpose of replicating enarocila and storing them in the ai-hub
 */
@ApplicationScoped
public class EnarocanjeReplicationService {
    @Inject EnarocanjeRestService enarocanjeRestService;
    @Inject SifMapper sifMapper;

    @Inject DocumentFetchService documentFetchService;
    @Inject FileReaderService fileReaderService;
    @Inject FileProcessingService fileProcessingService;
    @Inject EntityManager em;

    // TODO return base data about replicated entity, if needed
    public NarociloEntity replicateNarocilo(@NotNull  Integer narociloId){
        NarociloEntity narociloEntity = processNarocilo(narociloId);
        // convert narocilo json to file and store
        // TODO in fileProcessing service create method to process one file (vectorization + storage)

        // process documentation
        narociloEntity = processNarociloDocumentation(narociloEntity);

        // TODO return dto from entity
        return narociloEntity;
    }

    /**
     *
     * @param narociloId id narocila to be processed
     * @return
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public NarociloEntity processNarocilo(@NotNull Integer narociloId) {
        // TODO With NoSQL database when db type is set
        // check if already exists
        em.createNamedQuery("Narocilo.findByNarociloId", NarociloEntity.class)
                .setParameter("narociloId", narociloId)
                .getResultStream().findFirst()
                .ifPresent(n -> new RuntimeException("narocilo with id: " + narociloId + " alredy exists"));

        // populate with base data
        // TODO create narocilo in separate method with all required data if there will be more
        NarociloEntity narociloEntity = new NarociloEntity();
        narociloEntity.setNarociloId(narociloId);
        SifObrazecProcessedWithMetadata processedSifObrazec = enarocanjeRestService.processSifObrazec(narociloId);
        sifMapper.updateNarociloEntity(narociloEntity, processedSifObrazec);
        em.persist(narociloEntity);

        return narociloEntity;
    } // TODO make test to check mapping, utils to mock services responses

    private NarociloEntity processNarociloDocumentation(NarociloEntity narociloEntity){
        // TODO check if it is zip or not from header: later (should be handled by reader in commons)
        // TODO modify processing and remove fetching from here (give only url to file processing service - it will then handle data by itself (can be repeated for multiple sources)
        // TODO not urgent don't replicate already existing docs check how folders map in zip doc names so that full path is available also for inside zips)
        //  as there are no updates to obrazec once it is created
        // if can be done by commons library just link to commons
        narociloEntity = fileReaderService.extractZipFolder(
                documentFetchService.fetchZipStream(narociloEntity.getDocumentationUrl()),
                narociloEntity
        );

        // TODO use files gotten to convert and process data in minio
        narociloEntity = fileProcessingService.processFiles(narociloEntity);
        // TODO later should be done be separate endpoint that listens to query

        return narociloEntity;
    }
}
