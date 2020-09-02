package br.sigo.aplicacao.service.mapper;


import br.sigo.aplicacao.domain.*;
import br.sigo.aplicacao.service.dto.MateriaPrimaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MateriaPrima} and its DTO {@link MateriaPrimaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MateriaPrimaMapper extends EntityMapper<MateriaPrimaDTO, MateriaPrima> {


    @Mapping(target = "produto", ignore = true)
    MateriaPrima toEntity(MateriaPrimaDTO materiaPrimaDTO);

    default MateriaPrima fromId(Long id) {
        if (id == null) {
            return null;
        }
        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.setId(id);
        return materiaPrima;
    }
}
