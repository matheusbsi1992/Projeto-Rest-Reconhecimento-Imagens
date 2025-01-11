package br.com.projeto.estudos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "unidade", schema = "estudos")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidade", nullable = false, columnDefinition = "CHAR(26) DEFAULT estudos.generate_ulid()")
    private String id_unidade;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @Transient
    /*@ManyToOne
    @JoinColumn(name = "id_filial", referencedColumnName = "id_filial")*/
    private Filial filial;

    /*@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)//{CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "unidade_uniao_convenio",
            schema = "estudos",
            joinColumns = {@JoinColumn(name = "id_unidade")},
            inverseJoinColumns = {@JoinColumn(name = "id_convenio")})
    private List<Convenio> convenios;
*/
    @Column(name = "documento_unidade")
    private String documento_unidade;

    @Column(name = "email_unidade")
    private String email_unidade;

    @Column(name = "nome_fantasia_unidade")
    private String nome_fantasia_unidade;

    @Column(name = "setor_unidade")
    private String setor_unidade;

    @Column(name = "razao_social_unidade")
    private String razao_social_unidade;

    @Column(name = "telefone_unidade")
    private String telefone_unidade;

    @Column(name = "status_unidade")
    private Boolean status_unidade;

    @Column(name = "data_cadastro_unidade")
    private LocalDateTime data_cadastro_unidade;

    @Column(name = "data_atualizado_unidade")
    private LocalDateTime data_atualizado_unidade;


    /*@PrePersist
    public void prePersist() {
        if (this.id_unidade == null) {
            this.id_unidade = new ULID().nextULID();
        }
        this.data_cadastro_unidade = LocalDateTime.now();
    }*/

    @PrePersist
    public void prePersist() {
        this.data_cadastro_unidade = LocalDateTime.now();  // Define a data de cadastro na criação
    }

    @PreUpdate
    public void preUpdate() {
        this.data_atualizado_unidade = LocalDateTime.now();  // Atualiza a data de modificação ao atualizar
    }

}