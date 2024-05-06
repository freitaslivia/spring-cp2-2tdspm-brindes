package br.com.fiap.dto.response;

import lombok.Builder;

@Builder
public record CategoriaResponse(
		
        Long id,

        String nome
) {

}
