package br.sigo.aplicacao.service.mapper;


import br.sigo.aplicacao.domain.*;
import br.sigo.aplicacao.service.dto.NormasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Normas} and its DTO {@link NormasDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NormasMapper extends EntityMapper<NormasDTO, Normas> {



    default Normas fromId(Long id) {
        if (id == null) {
            return null;
        }
        Normas normas = new Normas();
        normas.setId(id);
        return normas;
    }
}
