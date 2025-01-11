package br.com.projeto.estudos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "filial", schema = "estudos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filial", nullable = false, columnDefinition = "CHAR(26) DEFAULT estudos.generate_ulid()")
    private String id_filial;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidade")
    private Unidade unidade;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @Column(name = "documento_filial")
    private String documento_filial;

    @Column(name = "email_filial")
    private String email_filial;

    @Column(name = "nome_fantasia_filial")
    private String nome_fantasia_filial;

    @Column(name = "setor_filial")
    private String setor_filial;

    @Column(name = "razao_social_filial")
    private String razao_social_filial;

    @Column(name = "telefone_filial")
    private String telefone_filial;

    @Column(name = "status_filial")
    private Boolean status_filial;

    //@Transient
    @Column(name = "data_cadastro_filial")
    private LocalDateTime data_cadastro_filial;

    //@Transient
    @Column(name = "data_atualizado_filial")
    private LocalDateTime data_atualizado_filial;

    @PrePersist
    public void prePersist() {
        this.data_cadastro_filial = LocalDateTime.now();  // Define a data de cadastro na criação
    }

    @PreUpdate
    public void preUpdate() {
        this.data_atualizado_filial = LocalDateTime.now();  // Atualiza a data de modificação ao atualizar
    }

}