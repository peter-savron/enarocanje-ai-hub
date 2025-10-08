package si.savron.enarocanje.hub.dtos.enarocila;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

public class NarocilaQueryRequestDto {

    private int page; // se veca z starRow, endRow
    private List<Integer> idSifCpv; // TODO fetch all cpv
    private List<Integer> idSifPostopekFaza; // TODO fetch all postopek faza // 3 - narocilo
    private boolean podrejeniCpv;
    private int datumDd; // koliko mesecev v nazaj
    private LocalDateTime objavaDejanskaDatumOd;
    private int startRow;
    private int endRow;
    private List<NarocilaQueryRequestSortDto> sortModel;
    private LocalDateTime datumPonudbaDo; // datumi objave
    private LocalDateTime datumPonudbaOd; // datumi objave
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String objavaIdent;// stevilka javnega narocila
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer idSifNarociloVrsta; // vrsta narocila
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String narocnikNaziv; // Naziv narocnika
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String narocnikMaticna; // Maticna stevilka narocnika
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String naslov; // Naslov narocila
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sklopNaslov; // Naziv sklopa javnega narocila
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer prejem; // tip roka za prejem ponudbe 1 (se odprto), 2(poteklo), 3 (danes), 4 (po datumRok)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime datumRok; // datum roka
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String narociloNaslov; // naziv javnega narocila

    // Getters and Setters
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Integer> getIdSifCpv() {
        return idSifCpv;
    }

    public void setIdSifCpv(List<Integer> idSifCpv) {
        this.idSifCpv = idSifCpv;
    }

    public List<Integer> getIdSifPostopekFaza() {
        return idSifPostopekFaza;
    }

    public void setIdSifPostopekFaza(List<Integer> idSifPostopekFaza) {
        this.idSifPostopekFaza = idSifPostopekFaza;
    }

    public boolean isPodrejeniCpv() {
        return podrejeniCpv;
    }

    public void setPodrejeniCpv(boolean podrejeniCpv) {
        this.podrejeniCpv = podrejeniCpv;
    }

    public int getDatumDd() {
        return datumDd;
    }

    public void setDatumDd(int datumDd) {
        this.datumDd = datumDd;
    }

    public LocalDateTime getObjavaDejanskaDatumOd() {
        return objavaDejanskaDatumOd;
    }

    public void setObjavaDejanskaDatumOd(LocalDateTime objavaDejanskaDatumOd) {
        this.objavaDejanskaDatumOd = objavaDejanskaDatumOd;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public List<NarocilaQueryRequestSortDto> getSortModel() {
        return sortModel;
    }

    public void setSortModel(List<NarocilaQueryRequestSortDto> sortModel) {
        this.sortModel = sortModel;
    }

    public LocalDateTime getDatumPonudbaDo() {
        return datumPonudbaDo;
    }

    public void setDatumPonudbaDo(LocalDateTime datumPonudbaDo) {
        this.datumPonudbaDo = datumPonudbaDo;
    }

    public LocalDateTime getDatumPonudbaOd() {
        return datumPonudbaOd;
    }

    public void setDatumPonudbaOd(LocalDateTime datumPonudbaOd) {
        this.datumPonudbaOd = datumPonudbaOd;
    }

    public String getObjavaIdent() {
        return objavaIdent;
    }

    public void setObjavaIdent(String objavaIdent) {
        this.objavaIdent = objavaIdent;
    }

    public Integer getIdSifNarociloVrsta() {
        return idSifNarociloVrsta;
    }

    public void setIdSifNarociloVrsta(Integer idSifNarociloVrsta) {
        this.idSifNarociloVrsta = idSifNarociloVrsta;
    }

    public String getNarocnikNaziv() {
        return narocnikNaziv;
    }

    public void setNarocnikNaziv(String narocnikNaziv) {
        this.narocnikNaziv = narocnikNaziv;
    }

    public String getNarocnikMaticna() {
        return narocnikMaticna;
    }

    public void setNarocnikMaticna(String narocnikMaticna) {
        this.narocnikMaticna = narocnikMaticna;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getSklopNaslov() {
        return sklopNaslov;
    }

    public void setSklopNaslov(String sklopNaslov) {
        this.sklopNaslov = sklopNaslov;
    }

    public Integer getPrejem() {
        return prejem;
    }

    public void setPrejem(Integer prejem) {
        this.prejem = prejem;
    }

    public LocalDateTime getDatumRok() {
        return datumRok;
    }

    public void setDatumRok(LocalDateTime datumRok) {
        this.datumRok = datumRok;
    }

    public String getNarociloNaslov() {
        return narociloNaslov;
    }

    public void setNarociloNaslov(String narociloNaslov) {
        this.narociloNaslov = narociloNaslov;
    }
}
