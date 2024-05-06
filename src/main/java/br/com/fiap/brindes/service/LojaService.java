package br.com.fiap.brindes.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.fiap.brindes.entity.Loja;
import br.com.fiap.brindes.entity.Produto;
import br.com.fiap.brindes.repository.LojaRepository;
import br.com.fiap.brindes.repository.ProdutoRepository;
import br.com.fiap.dto.request.LojaRequest;
import br.com.fiap.dto.response.LojaResponse;
import br.com.fiap.dto.response.ProdutoResponse;

@Service
public class LojaService implements ServiceDTO<Loja, LojaRequest, LojaResponse>{
   @Autowired
    private LojaRepository repo;
   
   @Autowired
   private ProdutoRepository pRepo;
  
   @Autowired
   private ProdutoService service;


    @Override
    public Collection<Loja> findAll(Example<Loja> example) {
        return repo.findAll( example );
    }

    @Override
    public Loja findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Loja save(Loja e) {
        if (repo.existsByNome(e.getNome())) {
            throw new RuntimeException(e.getNome() + "Já existe uma loja com o mesmo nome");
        }
		
        return repo.save( e );
    }

    @Override
    public Loja toEntity(LojaRequest dto) {

        return Loja.builder()
                .nome( dto.nome() )
                .build();
    }

    @Override
    public LojaResponse toResponse(Loja e) {

        List<ProdutoResponse> produtosComerci = null;
        if (e.getProdutosComercializados() != null) {
            produtosComerci = e.getProdutosComercializados().stream().map(service::toResponse).toList();
        }
    	
        return LojaResponse.builder()
                .id( e.getId() )
                .nome( e.getNome() )
                .produtosComercializados(produtosComerci)
                .build();
    }
    
    public Loja adicionarProdutoComercializado(Long lojaId, Long idProduto) {
       
        Loja loja = repo.findById(lojaId).orElseThrow(() -> new RuntimeException("Loja não encontrada com o ID: " + lojaId));
        
        Produto produto = pRepo.findById(idProduto).orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + idProduto));
        
        Set<Produto> p = loja.getProdutosComercializados();
        
        p.add(produto);
        
        loja.setProdutosComercializados(p);
        
        return repo.save(loja);
    }

	@Override
	public Collection<Loja> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
