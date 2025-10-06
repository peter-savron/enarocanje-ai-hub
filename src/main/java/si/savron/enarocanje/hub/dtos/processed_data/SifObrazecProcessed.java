package si.savron.enarocanje.hub.dtos.processed_data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SifObrazecProcessed {
    @JsonIgnore
    private String oznaka;
    @JsonIgnore
    private String naziv;
    @JsonIgnore
    private String pomoc; // not so interesting data could be included later
    private List<SifGradnikPoljeProcessedData> data;
    private List<SifObrazecProcessed> children;

    @JsonProperty(value = "naslov_sekcije", index = 1)
    public String getNaslovSekcije(){
        return String.join(" ", oznaka, naziv);
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void getOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public void getNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPomoc() {
        return pomoc;
    }

    public void setPomoc(String pomoc) {
        this.pomoc = pomoc;
    }

    public List<SifGradnikPoljeProcessedData> getData() {
        return data;
    }

    public void setData(List<SifGradnikPoljeProcessedData> data) {
        this.data = data;
    }

    public List<SifObrazecProcessed> getChildren() {
        return children;
    }

    public void setChildren(List<SifObrazecProcessed> children) {
        this.children = children;
    }
}
