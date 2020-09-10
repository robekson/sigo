package br.sigo.aplicacao.service.mapper;


import br.sigo.aplicacao.domain.*;
import br.sigo.aplicacao.service.dto.ProdutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produto} and its DTO {@link ProdutoDTO}.
 */
@Mapper(componentModel = "spring", uses = {MateriaPrimaMapper.class, VendaMapper.class})
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto> {

    @Mapping(source = "materiaPrima.id", target = "materiaPrimaId")
    @Mapping(source = "venda.id", target = "vendaId")
    ProdutoDTO toDto(Produto produto);

    @Mapping(source = "materiaPrimaId", target = "materiaPrima")
    @Mapping(source = "vendaId", target = "venda")
    @Mapping(target = "compras", ignore = true)
    @Mapping(target = "removeCompra", ignore = true)
    Produto toEntity(ProdutoDTO produtoDTO);

    default Produto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produto produto = new Produto();
        produto.setId(id);
        return produto;
    }
}
