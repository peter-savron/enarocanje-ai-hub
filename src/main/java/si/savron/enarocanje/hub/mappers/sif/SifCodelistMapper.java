package si.savron.enarocanje.hub.mappers.sif;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import si.savron.enarocanje.hub.dtos.enarocila.SifChildlessCodelistEntryRecord;
import si.savron.enarocanje.hub.dtos.enarocila.SifCodelistEntry;
import si.savron.enarocanje.hub.dtos.enarocila.SifCodelistEntryRecord;
import si.savron.enarocanje.hub.dtos.enarocila.SifCodelistSloEntry;
import si.savron.enarocanje.hub.mappers.MapstructConfig;

import java.util.List;

@Mapper(config = MapstructConfig.class, uses = {})
public interface SifCodelistMapper {
    SifCodelistEntryRecord toExtendedRecord(SifCodelistEntry entry);
    SifChildlessCodelistEntryRecord toRecord(SifCodelistEntry entry);
    List<SifChildlessCodelistEntryRecord> toRecord(List<SifCodelistEntry> entry);
    @Mapping(target = "label", source = "naziv")
    SifCodelistEntry toEng(SifCodelistSloEntry sifCodelistSloEntry);
    List<SifCodelistEntry> toEng(List<SifCodelistSloEntry> sifCodelistSloEntries);
}
