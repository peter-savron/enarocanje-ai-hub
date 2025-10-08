package si.savron.enarocanje.hub.dtos.enarocila;

import java.util.List;

public record SifCodelistEntryRecord(
        Integer id,
        String label,
        List<SifChildlessCodelistEntryRecord> children
) {
}
