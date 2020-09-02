package br.sigo.aplicacao.service.mapper;


import br.sigo.aplicacao.domain.*;
import br.sigo.aplicacao.service.dto.VendaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venda} and its DTO {@link VendaDTO}.
 */
@Mapper(componentModel = "spring", uses = {FuncionarioMapper.class})
public interface VendaMapper extends EntityMapper<VendaDTO, Venda> {

    @Mapping(source = "funcionario.id", target = "funcionarioId")
    VendaDTO toDto(Venda venda);

    @Mapping(target = "produtos", ignore = true)
    @Mapping(target = "removeProduto", ignore = true)
    @Mapping(source = "funcionarioId", target = "funcionario")
    Venda toEntity(VendaDTO vendaDTO);

    default Venda fromId(Long id) {
        if (id == null) {
            return null;
        }
        Venda venda = new Venda();
        venda.setId(id);
        return venda;
    }
}
