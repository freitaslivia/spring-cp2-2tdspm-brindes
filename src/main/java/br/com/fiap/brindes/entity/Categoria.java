package br.com.fiap.brindes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "TB_CATEGORIA")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CATEGORIA")
    @SequenceGenerator(name = "SQ_CATEGORIA", sequenceName = "SQ_CATEGORIA",   allocationSize = 1)
    @Column(name = "ID_CATEGORIA")
    private Long id;

    //Canetas
    //Camisetas
    //Canecas
    //Mochilas
    @Column(name = "NM_CATEGORIA")
    private String nome;

}
