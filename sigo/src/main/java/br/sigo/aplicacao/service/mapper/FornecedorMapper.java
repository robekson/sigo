package br.sigo.aplicacao.service.mapper;


import br.sigo.aplicacao.domain.*;
import br.sigo.aplicacao.service.dto.FornecedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fornecedor} and its DTO {@link FornecedorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FornecedorMapper extends EntityMapper<FornecedorDTO, Fornecedor> {


    @Mapping(target = "forneces", ignore = true)
    @Mapping(target = "removeFornece", ignore = true)
    Fornecedor toEntity(FornecedorDTO fornecedorDTO);

    default Fornecedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);
        return fornecedor;
    }
}
