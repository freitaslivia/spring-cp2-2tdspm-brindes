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

import br.com.fiap.brindes.entity.Loja;
import br.com.fiap.brindes.service.LojaService;
import br.com.fiap.brindes.service.ProdutoService;
import br.com.fiap.dto.request.LojaRequest;
import br.com.fiap.dto.request.ProdutoRequest;
import br.com.fiap.dto.response.LojaResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/lojas")
public class LojaResource {
	
//	LojaResource com as seguintes ROTAS e VERBOS (2):
//
//	(0,25 Ponto) "localhost/lojas" - POST
//	(1 Ponto) "localhost/lojas" - GET by Example,
//	(0,25 Ponto) "logalhost/lojas/{id} - GET
//	(0,5 Ponto) "logalhost/lojas/{id}/produtos-comercializados" - POST
	
	
   @Autowired
    private LojaService service;
   
    @GetMapping
    public ResponseEntity<Collection<LojaResponse>> findAll(
            @RequestParam(name = "nome", required = false) final String nome
    ) {

        var loja = Loja.builder()
                .nome(nome)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher("nome", match -> match.contains())
                //  .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreNullValues();

        Example<Loja> example = Example.of(loja, matcher);

        var entity = service.findAll(example);

        if (Objects.isNull(entity) || entity.size() == 0) return ResponseEntity.notFound().build();

        var response = entity.stream().map(service::toResponse).toList();

        return ResponseEntity.ok(response);

    }


    @GetMapping("{id}")
    public ResponseEntity<LojaResponse> findById(@PathVariable final Long id) {
        var entity = service.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = service.toResponse(entity);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<LojaResponse> save(@RequestBody @Valid LojaRequest dto) {
        var entity = service.toEntity(dto);
        var saved = service.save(entity);
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        var response = service.toResponse(saved);

        return ResponseEntity.created(uri).body(response);
    }
    
    @Transactional
    @PostMapping("{id}/produtos-comercializados")
    public ResponseEntity<LojaResponse> save(@PathVariable final Long id, @RequestBody @Valid Long idProduto) {

        var saved = service.adicionarProdutoComercializado(id, idProduto);

        var response = service.toResponse(saved);

        return ResponseEntity.ok(response);
    }
}
