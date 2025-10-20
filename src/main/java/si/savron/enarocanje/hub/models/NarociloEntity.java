package si.savron.enarocanje.hub.models;

import jakarta.persistence.*;

import java.util.List;

// TODO make narocilo more common use (also for other sources of data)
@Entity
@NamedQuery(
        name = "Narocilo.findByNarociloId",
        query = "SELECT n FROM NarociloEntity n WHERE n.narociloId = :narociloId"
)
public class NarociloEntity extends BaseEntity {
    @Column(name = "narocilo_id", nullable = false, unique = true)
    // this is ID in external system
    private Integer narociloId;
    @Column(name = "dosje_id")
    private Integer dosjeId;
    @Column(name = "cpv_id")
    // help to query via complex parameters
    private Integer cpvId;
    @Column(name = "documentation_url")
    private String documentationUrl;
    @Column(name = "elektronska_predlozitev_url")
    private String elektronskaPredlozitevUrl;
    @Column(name = "nalsov")
    private String naslov;
    @Column(name = "kratek_opis")
    private String kratekOpis;
    @Column(name = "additionalInfo")
    private String additionalInfo;
    @Column(name="narocnik_naziv")
    private String narocnikNaziv;
    @Column(name = "narocnik_url")
    private String narocnikGlavniUrl;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<DocumentEntity> documents;

    public Integer getNarociloId() {
        return narociloId;
    }

    public void setNarociloId(Integer narociloId) {
        this.narociloId = narociloId;
    }

    public Integer getDosjeId() {
        return dosjeId;
    }

    public void setDosjeId(Integer dosjeId) {
        this.dosjeId = dosjeId;
    }

    public Integer getCpvId() {
        return cpvId;
    }

    public void setCpvId(Integer cpvId) {
        this.cpvId = cpvId;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public String getElektronskaPredlozitevUrl() {
        return elektronskaPredlozitevUrl;
    }

    public void setElektronskaPredlozitevUrl(String elektronskaPredlozitevUrl) {
        this.elektronskaPredlozitevUrl = elektronskaPredlozitevUrl;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getKratekOpis() {
        return kratekOpis;
    }

    public void setKratekOpis(String kratekOpis) {
        this.kratekOpis = kratekOpis;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getNarocnikNaziv() {
        return narocnikNaziv;
    }

    public void setNarocnikNaziv(String narocnikNaziv) {
        this.narocnikNaziv = narocnikNaziv;
    }

    public String getNarocnikGlavniUrl() {
        return narocnikGlavniUrl;
    }

    public void setNarocnikGlavniUrl(String narocnikGlavniUrl) {
        this.narocnikGlavniUrl = narocnikGlavniUrl;
    }

    public List<DocumentEntity> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentEntity> documents) {
        this.documents = documents;
    }
}
