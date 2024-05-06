package br.com.fiap.dto.response;

import lombok.Builder;

@Builder
public record ProdutoResponse(
		
        Long id,

        String nome,

        CategoriaResponse categoria
		) {

}
