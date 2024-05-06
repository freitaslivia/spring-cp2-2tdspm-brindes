package br.com.fiap.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProdutoRequest(
		
        @Size(min = 1, max = 255)
        @NotBlank
        @NotNull
        String nome,

        @Positive
        @NotNull
        Double preco,

        @Valid
        @NotNull
        AbstractRequest categoria
		
		) {

}
