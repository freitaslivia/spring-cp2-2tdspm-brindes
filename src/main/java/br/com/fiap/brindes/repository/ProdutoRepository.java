package br.com.fiap.brindes.repository;

import br.com.fiap.brindes.entity.Categoria;
import br.com.fiap.brindes.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	boolean existsByNomeAndCategoria(String nome, Categoria categoria);
}
