package br.sigo.aplicacao.service.mapper;


import br.sigo.aplicacao.domain.*;
import br.sigo.aplicacao.service.dto.FuncionarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Funcionario} and its DTO {@link FuncionarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FuncionarioMapper extends EntityMapper<FuncionarioDTO, Funcionario> {


    @Mapping(target = "vendas", ignore = true)
    @Mapping(target = "removeVenda", ignore = true)
    Funcionario toEntity(FuncionarioDTO funcionarioDTO);

    default Funcionario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        return funcionario;
    }
}
