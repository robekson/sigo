package br.sigo.aplicacao.service.mapper;


import br.sigo.aplicacao.domain.*;
import br.sigo.aplicacao.service.dto.CompraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Compra} and its DTO {@link CompraDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProdutoMapper.class, ClienteMapper.class})
public interface CompraMapper extends EntityMapper<CompraDTO, Compra> {

    @Mapping(source = "cliente.id", target = "clienteId")
    CompraDTO toDto(Compra compra);

    @Mapping(target = "removeProduto", ignore = true)
    @Mapping(source = "clienteId", target = "cliente")
    Compra toEntity(CompraDTO compraDTO);

    default Compra fromId(Long id) {
        if (id == null) {
            return null;
        }
        Compra compra = new Compra();
        compra.setId(id);
        return compra;
    }
}
