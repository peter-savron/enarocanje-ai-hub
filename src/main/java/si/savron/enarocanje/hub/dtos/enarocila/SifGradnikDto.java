package si.savron.enarocanje.hub.dtos.enarocila;

import java.util.List;

public record SifGradnikDto(
        Integer id,
        String oznaka,
        Integer nivo, // vazno polje za dolocanj podskupin
        String naziv,
        Integer idSifGradnikVerzija,
        Integer verzija,
        String opomba, // Can be null in the JSON
        String pomoc, // to se v resnici lahko izpusti
        List<SifGradnikPoljeDto> sifObrazecGradnikPolje
) {}
