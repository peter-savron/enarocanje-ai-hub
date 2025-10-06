package si.savron.enarocanje.hub.dtos.enarocila;

import java.util.List;

public record SifOddelekDto(
        Integer id,
        String idSifObrazecVerzija,
        String oznaka,
        String naziv,
        Integer vrRed,
        List<SifGradnikDto> sifGradnik
) {}
