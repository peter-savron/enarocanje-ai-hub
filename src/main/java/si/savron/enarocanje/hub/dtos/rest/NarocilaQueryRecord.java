package si.savron.enarocanje.hub.dtos.rest;

import java.time.LocalDateTime;
import java.util.List;

public record NarocilaQueryRecord(
    Integer pageNumber,
    Integer pageSize,
    LocalDateTime datumRok,
    List<Integer> idsSifCpv,
    String narocnikNaziv,
    String narocnikMaticna
) {
}
