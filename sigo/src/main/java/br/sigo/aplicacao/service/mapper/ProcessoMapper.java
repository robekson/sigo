package br.sigo.aplicacao.service.mapper;


import br.sigo.aplicacao.domain.*;
import br.sigo.aplicacao.service.dto.ProcessoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Processo} and its DTO {@link ProcessoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessoMapper extends EntityMapper<ProcessoDTO, Processo> {

    @Mapping(source = "processo.id", target = "processoId")
    ProcessoDTO toDto(Processo processo);

    @Mapping(source = "processoId", target = "processo")
    @Mapping(target = "tipoProcessos", ignore = true)
    @Mapping(target = "removeTipoProcesso", ignore = true)
    @Mapping(target = "processoFilho", ignore = true)
    Processo toEntity(ProcessoDTO processoDTO);

    default Processo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Processo processo = new Processo();
        processo.setId(id);
        return processo;
    }
}
