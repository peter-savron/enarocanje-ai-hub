package si.savron.enarocanje.hub.dtos.enarocila;

import java.util.List;

public record NarocilaQueryResponseDto (
            List<NarocilaQueryResponseElementDto> data,
            int lastRow
    ) {}