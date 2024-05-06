package br.com.fiap.brindes.repository;

import br.com.fiap.brindes.entity.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {

	boolean existsByNome(String nome);
}
