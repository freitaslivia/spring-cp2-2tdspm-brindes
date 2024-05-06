package br.com.fiap.brindes.repository;

import br.com.fiap.brindes.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	boolean existsByNome(String nome);
}
