package si.savron.enarocanje.hub.dtos.enarocila;

import java.util.List;

public record SifObrazecDto(
        Integer id,
        String koda,
        String oznaka,
        String naziv,
        String kratekNaziv,
        Integer idSifObrazecTip,
        String sifObrazecTip,
        Boolean zacetekPostopka,
        Boolean aktivnost,
        Boolean imaStrukturo,
        Object eFormsId, // Can be null, using Object for flexibility
        String eFormsShema,
        String eFormsFormType,
        String eFormsNoticeType,
        Boolean imaFaq,
        List<SifOddelekDto> sifOddelek
) {}