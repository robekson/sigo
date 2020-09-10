package br.sigo.aplicacao.service.mapper;


import br.sigo.aplicacao.domain.*;
import br.sigo.aplicacao.service.dto.TipoProcessoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoProcesso} and its DTO {@link TipoProcessoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProcessoMapper.class})
public interface TipoProcessoMapper extends EntityMapper<TipoProcessoDTO, TipoProcesso> {

    @Mapping(source = "processo.id", target = "processoId")
    TipoProcessoDTO toDto(TipoProcesso tipoProcesso);

    @Mapping(source = "processoId", target = "processo")
    TipoProcesso toEntity(TipoProcessoDTO tipoProcessoDTO);

    default TipoProcesso fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoProcesso tipoProcesso = new TipoProcesso();
        tipoProcesso.setId(id);
        return tipoProcesso;
    }
}
