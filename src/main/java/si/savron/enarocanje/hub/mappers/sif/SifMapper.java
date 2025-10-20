package si.savron.enarocanje.hub.mappers.sif;

import jakarta.json.JsonObject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import si.savron.enarocanje.hub.dtos.enarocila.SifGradnikDto;
import si.savron.enarocanje.hub.dtos.enarocila.SifGradnikPoljeDto;
import si.savron.enarocanje.hub.dtos.enarocila.SifObrazecDto;
import si.savron.enarocanje.hub.dtos.enarocila.SifOddelekDto;
import si.savron.enarocanje.hub.dtos.processed_data.SifGradnikPoljeProcessedData;
import si.savron.enarocanje.hub.dtos.processed_data.SifObrazecProcessed;
import si.savron.enarocanje.hub.dtos.processed_data.SifObrazecProcessedWithMetadata;
import si.savron.enarocanje.hub.mappers.MapstructConfig;
import si.savron.enarocanje.hub.models.NarociloEntity;

/**
 * children field should be mapped in code as it is not straightForward
 */
@Mapper(config = MapstructConfig.class, uses = {})
public interface SifMapper {
    SifObrazecProcessed fromSifObrazecDto(SifObrazecDto obrazec);
    SifObrazecProcessed fromSifOddelekDto(SifOddelekDto oddelek);
    SifObrazecProcessed fromSifGradnikDto(SifGradnikDto gradnik);
    SifGradnikPoljeProcessedData fromSifGradnikPoljeDto(SifGradnikPoljeDto polje);

    // remember: maps via getters (not fields)
    void updateNarociloEntity(@MappingTarget NarociloEntity narociloEntity, SifObrazecProcessedWithMetadata sifObrazecProcessedWithMetadata);
}
