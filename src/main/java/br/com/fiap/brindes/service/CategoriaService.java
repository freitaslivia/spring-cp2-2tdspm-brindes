package br.com.fiap.brindes.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.fiap.brindes.entity.Categoria;
import br.com.fiap.brindes.repository.CategoriaRepository;
import br.com.fiap.dto.request.CategoriaRequest;
import br.com.fiap.dto.response.CategoriaResponse;


@Service
public class CategoriaService implements ServiceDTO<Categoria, CategoriaRequest, CategoriaResponse>{
	   @Autowired
	    private CategoriaRepository repo;


	    @Override
	    public Collection<Categoria> findAll(Example<Categoria> example) {
	        return repo.findAll( example );
	    }

	    @Override
	    public Categoria findById(Long id) {
	        return repo.findById( id ).orElse( null );
	    }

	    @Override
	    public Categoria save(Categoria e) {
	    	
//	        if (repo.existsByNome(e.getNome())) {
//	            throw new ValidationError(e.getNome(), "Já existe uma categoria com o mesmo nome");
//	        }
	        if (repo.existsByNome(e.getNome())) {
	            throw new RuntimeException(e.getNome() + "Já existe uma categoria com o mesmo nome");
	        }
			
	        return repo.save( e );
	    }

	    @Override
	    public Categoria toEntity(CategoriaRequest dto) {

	        return Categoria.builder()
	                .nome( dto.nome() )
	                .build();
	    }

	    @Override
	    public CategoriaResponse toResponse(Categoria e) {

	        return CategoriaResponse.builder()
	                .id( e.getId() )
	                .nome( e.getNome() )
	                .build();
	    }

		@Override
		public Collection<Categoria> findAll() {
			// TODO Auto-generated method stub
			return null;
		}
	}