package si.savron.enarocanje.hub.dtos.enarocila;

public record SifGradnikPoljeDto(
        Integer id,
        Integer idSifPolje,
        Integer vrRed,
        String naziv,
        String napis,
        Integer dolzina,
        Boolean obvezno,
        Boolean readOnly,
        Integer sirina,
        Boolean lookup,
        Integer idSifPoljeTip,
        String sifPoljeTipNaziv,
        String stolpec,
        String objekt
) {}