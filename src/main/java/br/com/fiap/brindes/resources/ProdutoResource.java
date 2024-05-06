package br.com.fiap.brindes.resources;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.fiap.brindes.entity.Categoria;
import br.com.fiap.brindes.entity.Loja;
import br.com.fiap.brindes.entity.Produto;
import br.com.fiap.brindes.service.ProdutoService;
import br.com.fiap.dto.request.ProdutoRequest;
import br.com.fiap.dto.response.ProdutoResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

//	ProdutoResource com as seguintes ROTAS e VERBOS (1):
//
//	(0,25 Ponto) "localhost/produtos" - POST
//	(0,5 Ponto) "localhost/produtos" - GET by Example,
//	(0,25 Ponto) "logalhost/produtos/{id}" - GET
	
   @Autowired
    private ProdutoService service;

    @GetMapping
    public ResponseEntity<Collection<ProdutoResponse>> findAll(
            @RequestParam(name = "nome", required = false) final String nome,
            @RequestParam(name = "preco", required = false) final Double preco,
            @RequestParam(name = "categoria", required = false) final Categoria categoria
    ) {

        var produto = Produto.builder()
                .nome(nome)
                .preco(preco)
                .categoria(categoria)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher("nome", match -> match.contains())
                //  .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreNullValues();

        Example<Produto> example = Example.of(produto, matcher);

        var entity = service.findAll(example);

        if (Objects.isNull(entity) || entity.size() == 0) return ResponseEntity.notFound().build();

        var response = entity.stream().map(service::toResponse).toList();

        return ResponseEntity.ok(response);

    }


    @GetMapping("{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable final Long id) {
        var entity = service.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = service.toResponse(entity);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ProdutoResponse> save(@RequestBody @Valid ProdutoRequest dto) {
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
