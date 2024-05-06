package br.com.fiap.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LojaRequest(
		
        @Size(min = 2, max = 255)
        @NotBlank
        @NotNull
        String nome

		) {

}
