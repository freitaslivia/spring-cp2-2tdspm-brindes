package br.com.fiap.dto.response;

import java.util.Collection;

import lombok.Builder;

@Builder
public record LojaResponse(
        Long id,

        String nome,

        Collection<ProdutoResponse> produtosComercializados

		) {

}
