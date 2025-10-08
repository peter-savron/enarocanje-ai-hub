package si.savron.enarocanje.hub.dtos.enarocila;

import java.time.LocalDateTime;
import java.util.List;

public record NarocilaQueryResponseElementDto(
                int idObrazec,
                int idDosje,
                String naslov,
                String narocnikNaziv,
                String narocnikMaticna,
                int idSifObrazec,
                String sifObrazecKoda,
                String sifObrazecOznaka,
                int idSifPostopekFaza,
                String sifPostopekFazaNaziv,
                String objavaIdent,
                LocalDateTime objavaDejanskaDatum,
                boolean noviObrazec,
                Integer idStariSistem,
                String objavaEU,
                boolean elektronskaPredlozitevObvezno,
                boolean dinamicniNabavniSistem,
                boolean okvirniSporazum,
                List<String> akcija,
                String validationStatus,
                String validationStatusOpis
        ) {}
