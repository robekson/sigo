package br.sigo.aplicacao.service.mapper;


import br.sigo.aplicacao.domain.*;
import br.sigo.aplicacao.service.dto.ConsultorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Consultor} and its DTO {@link ConsultorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConsultorMapper extends EntityMapper<ConsultorDTO, Consultor> {



    default Consultor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Consultor consultor = new Consultor();
        consultor.setId(id);
        return consultor;
    }
}
