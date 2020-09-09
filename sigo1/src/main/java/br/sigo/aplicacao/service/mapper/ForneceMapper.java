package br.sigo.aplicacao.service.mapper;


import br.sigo.aplicacao.domain.*;
import br.sigo.aplicacao.service.dto.ForneceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fornece} and its DTO {@link ForneceDTO}.
 */
@Mapper(componentModel = "spring", uses = {FornecedorMapper.class})
public interface ForneceMapper extends EntityMapper<ForneceDTO, Fornece> {

    @Mapping(source = "fornece.id", target = "forneceId")
    ForneceDTO toDto(Fornece fornece);

    @Mapping(target = "compras", ignore = true)
    @Mapping(target = "removeCompra", ignore = true)
    @Mapping(source = "forneceId", target = "fornece")
    Fornece toEntity(ForneceDTO forneceDTO);

    default Fornece fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fornece fornece = new Fornece();
        fornece.setId(id);
        return fornece;
    }
}
