package si.savron.enarocanje.hub.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import si.savron.enarocanje.hub.dtos.enarocila.SifChildlessCodelistEntryRecord;
import si.savron.enarocanje.hub.dtos.enarocila.SifCodelistEntry;
import si.savron.enarocanje.hub.dtos.enarocila.SifCodelistEntryRecord;
import si.savron.enarocanje.hub.dtos.enarocila.SifCodelistSloEntry;
import si.savron.enarocanje.hub.mappers.sif.SifCodelistMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SifCodelistService {
    @Inject ObjectMapper objectMapper;
    @Inject SifCodelistMapper codelistMapper;
    private final String SIF_CODELIST = "sif_codelists/";

    Map<Integer, SifCodelistEntry> sifCpvCodemap;
    List<SifCodelistEntry> sifCpvCodelist;
    List<SifCodelistEntry> sifNarociloVrstaCodelist;
    List<SifCodelistEntry> sifPostopekFazaCodelist;

    @PostConstruct
    public void initializeCodelists() throws IOException {
        // TODO better handling of json filenames
        sifCpvCodelist = readCodelist(SIF_CODELIST + "sifCpv.json");
        sifNarociloVrstaCodelist = readCodelistSlo(SIF_CODELIST + "sifNarociloVrsta.json");
        sifPostopekFazaCodelist = readCodelistSlo(SIF_CODELIST + "sifPostopekFaza.json");

        sifCpvCodemap = createCodelistMap(sifCpvCodelist);
    }

    public List<SifChildlessCodelistEntryRecord> getBaseCpvCodelist(){
        return codelistMapper.toRecord(sifCpvCodelist);
    }

    public SifCodelistEntryRecord getCpvCodelistChildren(Integer cpvId){
        return codelistMapper.toExtendedRecord(sifCpvCodemap.get(cpvId));
    }

    public List<SifChildlessCodelistEntryRecord> getPostopekFazaCodelist(){
        return codelistMapper.toRecord(sifPostopekFazaCodelist);
    }

    public List<SifChildlessCodelistEntryRecord> getNarociloVrstaCodelist(){
        return codelistMapper.toRecord(sifNarociloVrstaCodelist);
    }

    private List<SifCodelistEntry> readCodelist(String codelistFileName) throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(codelistFileName)) {
            if (is == null) {
                throw new IOException("Resource file not found: " + codelistFileName);
            }
            return objectMapper.readValue(is, new TypeReference<List<SifCodelistEntry>>() {});
        } catch (Exception e) {
            // Log the error or handle it as necessary
            throw new IOException("Failed to read or parse JSON file: " + codelistFileName, e);
        }
    }

    private List<SifCodelistEntry> readCodelistSlo(String codelistFileName) throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(codelistFileName)) {
            if (is == null) {
                throw new IOException("Resource file not found: " + codelistFileName);
            }
            return codelistMapper.toEng(objectMapper.readValue(is, new TypeReference<List<SifCodelistSloEntry>>() {}));
        } catch (Exception e) {
            // Log the error or handle it as necessary
            throw new IOException("Failed to read or parse JSON file: " + codelistFileName, e);
        }
    }

    private Map<Integer, SifCodelistEntry> createCodelistMap(List<SifCodelistEntry> sourceList) {
        List<SifCodelistEntry> targetList = new ArrayList<>();
        fillCodelistRecursively(targetList, sourceList);
        Map<Integer, SifCodelistEntry> codelistMap = new HashMap<>();
        for (var codelist : targetList){
            codelistMap.put(codelist.getId(), codelist);
        }
        return codelistMap;
    }

    private void fillCodelistRecursively(List<SifCodelistEntry> target, List<SifCodelistEntry> source){
        for (var codelistEntry : source) {
            target.add(codelistEntry);
            if (codelistEntry.getChildren() != null) {
                fillCodelistRecursively(target, codelistEntry.getChildren());
            }
        }
    }
}
