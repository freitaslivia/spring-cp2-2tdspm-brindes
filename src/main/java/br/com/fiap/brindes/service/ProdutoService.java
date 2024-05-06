package br.com.fiap.brindes.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.fiap.brindes.entity.Produto;
import br.com.fiap.brindes.repository.ProdutoRepository;
import br.com.fiap.dto.request.ProdutoRequest;
import br.com.fiap.dto.response.ProdutoResponse;

@Service
public class ProdutoService implements ServiceDTO<Produto, ProdutoRequest, ProdutoResponse>{
	   @Autowired
	    private ProdutoRepository repo;
	   
	    @Autowired
	    private CategoriaService categoriaService;


	    @Override
	    public Collection<Produto> findAll(Example<Produto> example) {
	        return repo.findAll( example );
	    }

	    @Override
	    public Produto findById(Long id) {
	        return repo.findById( id ).orElse( null );
	    }

	    @Override
	    public Produto save(Produto e) {
	        if (repo.existsByNomeAndCategoria(e.getNome(), e.getCategoria())) {
	            throw new RuntimeException("Já existe um produto com o mesmo nome e categoria");
	        }
			
	        return repo.save( e );
	    }

	    @Override
	    public Produto toEntity(ProdutoRequest dto) {
	    	var categoria = categoriaService.findById(dto.categoria().id());

	        return Produto.builder()
	                .nome( dto.nome() )
	                .preco(dto.preco())
	                .categoria(categoria)
	                .build();
	    }

	    @Override
	    public ProdutoResponse toResponse(Produto e) {
	    	var categoria = categoriaService.toResponse(e.getCategoria());
	    	
	        return ProdutoResponse.builder()
	                .id( e.getId() )
	                .nome( e.getNome() )
	                .categoria(categoria)
	                .build();
	    }

		@Override
		public Collection<Produto> findAll() {
			// TODO Auto-generated method stub
			return null;
		}
	}

