package si.savron.enarocanje.hub.dtos.processed_data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SifGradnikPoljeProcessedData {
    private String naziv;
    private String napis;
    @JsonIgnore
    private Integer idSifPoljeTip; // to in tipNaziv sta spekularna, naziv je za AI agenta
    private String sifPoljeTipNaziv; //
    private String vsebina; // this is gotten from obrazecGet data

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getNapis() {
        return napis;
    }

    public void setNapis(String napis) {
        this.napis = napis;
    }

    public Integer getIdSifPoljeTip() {
        return idSifPoljeTip;
    }

    public void setIdSifPoljeTip(Integer idSifPoljeTip) {
        this.idSifPoljeTip = idSifPoljeTip;
    }

    public String getSifPoljeTipNaziv() {
        return sifPoljeTipNaziv;
    }

    public void setSifPoljeTipNaziv(String sifPoljeTipNaziv) {
        this.sifPoljeTipNaziv = sifPoljeTipNaziv;
    }

    public String getVsebina() {
        return vsebina;
    }

    public void setVsebina(String vsebina) {
        this.vsebina = vsebina;
    }
}
