package br.com.fiap.brindes.resources;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.fiap.brindes.entity.Categoria;
import br.com.fiap.brindes.service.CategoriaService;
import br.com.fiap.dto.request.CategoriaRequest;
import br.com.fiap.dto.response.CategoriaResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
//	(0,25 Ponto) "localhost/categorias" - POST
//	(0,25 Ponto) "localhost/categorias" - GET by Example,
//	(0,25 Ponto) "logalhost/categorias/{id}" - GET
	
    @Autowired
    private CategoriaService service;

    @GetMapping
    public ResponseEntity<Collection<CategoriaResponse>> findAll(
            @RequestParam(name = "nome", required = false) final String nome
    ) {

        var categoria = Categoria.builder()
                .nome(nome)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher("nome", match -> match.contains())
                //  .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreNullValues();

        Example<Categoria> example = Example.of(categoria, matcher);

        var entity = service.findAll(example);

        if (Objects.isNull(entity) || entity.size() == 0) return ResponseEntity.notFound().build();

        var response = entity.stream().map(service::toResponse).toList();

        return ResponseEntity.ok(response);

    }


    @GetMapping("{id}")
    public ResponseEntity<CategoriaResponse> findById(@PathVariable final Long id) {
        var entity = service.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = service.toResponse(entity);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CategoriaResponse> save(@RequestBody @Valid CategoriaRequest dto) {
        var entity = service.toEntity(dto);
        var saved = service.save(entity);
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        var response = service.toResponse(saved);

        return ResponseEntity.created(uri).body(response);
    }


}

