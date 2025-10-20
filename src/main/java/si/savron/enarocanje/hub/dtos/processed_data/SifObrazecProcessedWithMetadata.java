package si.savron.enarocanje.hub.dtos.processed_data;

import static si.savron.enarocanje.hub.utils.JsonObjectUtil.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.json.JsonObject;

public class SifObrazecProcessedWithMetadata {
    public SifObrazecProcessedWithMetadata(JsonObject rawData){
        this.rawData = rawData;
    }
    public SifObrazecProcessedWithMetadata() {}

    @JsonIgnore
    private JsonObject rawData;
    private String cpvDesc; // should be set in code
    private SifObrazecProcessed content;

    // getters for important fields in raw data
    public Integer getNarociloId() {
        return getInteger(rawData, "id");
    }
    public Integer getDosjeId() {
        return getInteger(rawData, "idDosje");
    }
    public Integer getCpvId() {
        return getInteger(rawData, "idSifCpv");
    }
    public String getDocumentationUrl() {
        return getString(rawData, "razpisnaDokumentacijaURL");
    }
    public String getElektronskaPredlozitevUrl() {
        return getString(rawData, "elektronskaPredlozitevURL");
    }
    public String getNaslov() {
        return getString(rawData, "naslov");
    }
    public String getKratekOpis() {
        return getString(rawData, "kratekOpis");
    }
    public String getAdditionalInfo() {
        return getString(rawData, "dodatnaInfo");
    }
    // BUG read array as Object and got error at deserialization but only part of buffer was invalidated and not sent
    public String getNarocnikNaziv() {
        return getString(rawData.getJsonArray("narocnik").getJsonObject(0), "naziv");
    }
    public String getNarocnikGlavniUrl() {
        return getString(rawData.getJsonArray("narocnik").getJsonObject(0), "glavniURL");
    }
    // TODO print whole obrazec json to string
    public String toJsonString() {
        return rawData.toString(); // TODO check correctness
    }

    public SifObrazecProcessed getContent() {
        return content;
    }

    public void setContent(SifObrazecProcessed content) {
        this.content = content;
    }

    public JsonObject getRawData() {
        return rawData;
    }

    public void setRawData(JsonObject rawData) {
        this.rawData = rawData;
    }

    public String getCpvDesc() {
        return cpvDesc;
    }

    public void setCpvDesc(String cpvDesc) {
        this.cpvDesc = cpvDesc;
    }
}
