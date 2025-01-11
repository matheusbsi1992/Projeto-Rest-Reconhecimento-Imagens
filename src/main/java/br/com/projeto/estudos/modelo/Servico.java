package br.com.projeto.estudos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "servico", schema = "estudos")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico")
    private Long id_servico;

    @Column(name = "nome_servico")
    private String nome_servico;

    @Column(name = "descricao_servico")
    private String descricao_servico;

    @Column(name = "status_servico")
    private Boolean status_servico;

    @PrePersist
    public void setStatus_servico() {
        this.status_servico = Boolean.TRUE; // insercao dos dados sobre o valor true
    }
}