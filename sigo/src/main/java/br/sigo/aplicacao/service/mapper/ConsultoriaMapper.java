package br.sigo.aplicacao.service.mapper;


import br.sigo.aplicacao.domain.*;
import br.sigo.aplicacao.service.dto.ConsultoriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Consultoria} and its DTO {@link ConsultoriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConsultoriaMapper extends EntityMapper<ConsultoriaDTO, Consultoria> {



    default Consultoria fromId(Long id) {
        if (id == null) {
            return null;
        }
        Consultoria consultoria = new Consultoria();
        consultoria.setId(id);
        return consultoria;
    }
}
