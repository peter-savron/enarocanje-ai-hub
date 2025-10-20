package si.savron.enarocanje.hub.services;

import static si.savron.enarocanje.hub.utils.HeaderUtils.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import si.savron.enarocanje.hub.clients.enarocanje.EnarocanjeClient;
import si.savron.enarocanje.hub.common.services.DocumentFetchService;
import si.savron.enarocanje.hub.common.services.FileReaderService;
import si.savron.enarocanje.hub.dtos.enarocila.*;
import si.savron.enarocanje.hub.dtos.processed_data.SifGradnikPoljeProcessedData;
import si.savron.enarocanje.hub.dtos.processed_data.SifObrazecProcessed;
import si.savron.enarocanje.hub.dtos.processed_data.SifObrazecProcessedWithMetadata;
import si.savron.enarocanje.hub.dtos.rest.NarocilaQueryRecord;
import si.savron.enarocanje.hub.mappers.sif.SifMapper;
import si.savron.enarocanje.hub.models.NarociloEntity;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EnarocanjeRestService {
    private final Logger LOG = Logger.getLogger(EnarocanjeRestService.class);

    @Inject SifMapper sifMapper;
    @Inject ObjectMapper objectMapper;
    @Inject FileReaderService fileReaderService;
    @Inject DocumentFetchService documentFetchService;

    @RestClient
    private EnarocanjeClient enarocanjeClient;

    /**
     * Gets paginated narocila corresonding to query
     * @param queryRecord search parameters
     * @return paginated narocila that correspond to searc
     */
    public NarocilaQueryResponseDto queryNarocila(NarocilaQueryRecord queryRecord){
        NarocilaQueryRequestDto requestDto = new NarocilaQueryRequestDto();
        requestDto.setNarocnikNaziv(queryRecord.narocnikNaziv());
        requestDto.setNarocnikMaticna(queryRecord.narocnikMaticna());
        requestDto.setIdSifCpv(queryRecord.idsSifCpv() != null ? queryRecord.idsSifCpv() : List.of());
        if (queryRecord.datumRok() != null) {
            requestDto.setDatumRok(queryRecord.datumRok());
            requestDto.setPrejem(4); // od roka
        } else {
            requestDto.setPrejem(1); // rok se ni potekel
        }
        requestDto.setPodrejeniCpv(true);
        requestDto.setIdSifPostopekFaza(List.of(3)); // samo narocila
        requestDto.setDatumDd(-1); // ne glej dotumov

        requestDto.setPage(queryRecord.pageNumber());
        requestDto.setStartRow(queryRecord.pageSize() * (queryRecord.pageNumber() - 1));
        requestDto.setEndRow(queryRecord.pageSize() * queryRecord.pageNumber());

        NarocilaQueryRequestSortDto sortDto = new NarocilaQueryRequestSortDto();
        sortDto.setColId("objavaDejanskaDatum");
        sortDto.setSort("desc");
        requestDto.setSortModel(List.of(sortDto));
        try {
            LOG.info(objectMapper.writer().writeValueAsString(requestDto));
        } catch (Exception e) {
            LOG.info("error happened");
        }
        return enarocanjeClient.queryNarocila(requestDto, "application/json; charset=utf-8");
    }

    // TODO update razpis each time duplicate is old (once per day)

    /**
     * Returns base data about narocilo with metadatra
     * @return processed data (unneccessary fields cutted)
     */
    public SifObrazecProcessedWithMetadata processSifObrazec(Integer obrazecId){
        var obrazec = enarocanjeClient.obrazecGet(obrazecId);
        var sifObrazec = enarocanjeClient.sifObrazecGet(obrazecId);
        SifObrazecProcessedWithMetadata processedData = new SifObrazecProcessedWithMetadata(obrazec);
        SifObrazecProcessed content = sifMapper.fromSifObrazecDto(sifObrazec);
        content.setChildren(new ArrayList<>());
        for (var oddelek : sifObrazec.sifOddelek()){
            SifObrazecProcessed oddelekProcessed = sifMapper.fromSifOddelekDto(oddelek);
            oddelekProcessed.setChildren(
                    processGradnik(oddelek.sifGradnik(), obrazec)
            );
            content.getChildren().add(oddelekProcessed);
        }

        processedData.setContent(content);
        return processedData;
    }

    private List<SifObrazecProcessed> processGradnik(List<SifGradnikDto> gradniki, JsonObject obrazec){
        if (gradniki.isEmpty()) {
            return null;
        } else {
            List<SifObrazecProcessed> gradnikiProcessed = new ArrayList<>();
            // helper list to create stack for nested gradniki
            List<SifObrazecProcessed> stack = new ArrayList<>(16);
            stack.add(null); // fix - at beginning there should be an item to remove
            int baseNivo =  gradniki.getFirst().nivo();
            int previousNivo = baseNivo;
            for (var gradnik : gradniki) {
                // nivo se zmanjsa, popaj neka enot iz sklada
                int dif = previousNivo - gradnik.nivo();
                var gradnikProcessed = sifMapper.fromSifGradnikDto(gradnik);
                processData(gradnikProcessed, gradnik, obrazec);
                if (dif >= 0) { // we are mintaining or losing niveau, should remove some el on stack
                    for (int i = 0; i <= dif; i++){ // remove all el on stack with nivo same or higher
                        stack.removeLast();
                    }
                    if(gradnik.nivo() == baseNivo) { // touched bottom
                        gradnikiProcessed.add(gradnikProcessed);
                    } else {
                        stack.getLast().getChildren().add(gradnikProcessed);
                    }
                } else { // we dont need to clear stack, we build next niveau on it
                    // create children for top of stack and add firs child
                    var stackTop = stack.getLast();
                    stackTop.setChildren(new ArrayList<>(List.of(gradnikProcessed)));
                }
                // prepare data for next round
                stack.add(gradnikProcessed);
                previousNivo = gradnik.nivo();
            }

            return gradnikiProcessed;
        }
    }

    private void processData(SifObrazecProcessed processed, SifGradnikDto gradnik, JsonObject obrazec){
        // TODO code ids are not handled (dropdowns) et similar add codelists
        // TODO catch links and find the one with documentation type "URLUpload"
        List<SifGradnikPoljeProcessedData> data = new ArrayList<>();
        for (var polje :gradnik.sifObrazecGradnikPolje()) {
            var processedPolje = sifMapper.fromSifGradnikPoljeDto(polje);
            if (polje.objekt().equals("obrazec")) { // get directly from obrazec object
                if (obrazec.get(polje.stolpec()).getValueType() != JsonValue.ValueType.NULL) {
                    processedPolje.setVsebina(obrazec.get(polje.stolpec()).toString());
                    data.add(processedPolje);
                }
            } else { // get from child object
                var field = obrazec.get(polje.objekt());
                if (field == null) {
                    LOG.warn("field: " + polje.objekt() + " is null");
                    continue;
                }
                switch (field.getValueType()){
                    case OBJECT -> {
                        var objekt = obrazec.getJsonObject(polje.objekt());
                        if (objekt.get(polje.stolpec()).getValueType() != JsonValue.ValueType.NULL) {
                            processedPolje.setVsebina(objekt.get(polje.stolpec()).toString());
                            data.add(processedPolje);
                        }
                    }
                    case ARRAY -> { // TODO map arrays with multiple elements better - not urgent
                        if(!obrazec.getJsonArray(polje.objekt()).isEmpty()) {
                            var objekt = obrazec.getJsonArray(polje.objekt()).getJsonObject(0);
                            if (objekt.get(polje.stolpec()).getValueType() != JsonValue.ValueType.NULL) {
                                processedPolje.setVsebina(objekt.get(polje.stolpec()).toString());
                                data.add(processedPolje);
                            }
                        }
                    }
                    case null, default -> {}
                }
            }
        }
        processed.setData(data);
    }
}
