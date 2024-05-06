package br.com.fiap.brindes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "TB_LOJA")
public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LOJA")
    @SequenceGenerator(name = "SQ_LOJA", sequenceName = "SQ_LOJA",   allocationSize = 1)
    @Column(name = "ID_LOJA")
    private Long id;
    
    @Column(name = "NM_LOJA")
    private String nome;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "TB_PRODUTO_LOJA",
            joinColumns = {
                    @JoinColumn(
                            name = "LOJA",
                            referencedColumnName = "ID_LOJA",
                            foreignKey = @ForeignKey(name = "FK_LOJA")
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "PRODUTO",
                            referencedColumnName = "ID_PRODUTO",
                            foreignKey = @ForeignKey(name = "FK_PRODUTO")
                    )
            }
    )
    private Set<Produto> produtosComercializados = new LinkedHashSet<>();

}
