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
@Table(name = "convenio", schema = "estudos")
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_convenio", nullable = false, columnDefinition = "CHAR(26) DEFAULT estudos.generate_ulid()")
    private String id_convenio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    // Many-to-Many com Unidade
    //@ManyToMany(mappedBy = "convenios",cascade = CascadeType.ALL)
    /*@ManyToMany
    @JoinTable(
            schema = "estudos",
            name = "unidade_uniao_convenio", // Nome da tabela intermediária
            inverseJoinColumns = @JoinColumn(name = "id_unidade"), // Coluna de chave estrangeira de Unidade
            joinColumns = @JoinColumn(name = "id_convenio") // Coluna de chave estrangeira de Convenio
    )
    private List<Unidade> unidades;*/

    // Many-to-Many com PessoaLocal
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            schema = "estudos",
            name = "pessoa_uniao_convenio", // Nome da tabela intermediária
            joinColumns = @JoinColumn(name = "id_convenio"), // Coluna de chave estrangeira de Convenio
            inverseJoinColumns = {
                    @JoinColumn(name = "id_pessoa")//,    // Pessoa associada
                    //@JoinColumn(name = "local_tipo"),   // Tipo de local ('filial' ou 'unidade')
                    //@JoinColumn(name = "local_id")   // ID da filial ou unidade
            }
    )
    private List<Pessoa> pessoas;

    @Column(name = "nome_fantasia_convenio")
    private String nome_fantasia_convenio;

    @Column(name = "razao_social_convenio")
    private String razao_social_convenio;

    @Column(name = "cnpj_convenio")
    private String cnpj_convenio;

    @Column(name = "email_convenio")
    private String email_convenio;

    @Column(name = "telefone_convenio")
    private String telefone_convenio;

    @Column(name = "setor_convenio")
    private String setor_convenio;

    @Column(name = "data_cadastro_convenio")
    private LocalDateTime data_cadastro_convenio;

    @Column(name = "data_atualizado_convenio")
    private LocalDateTime data_atualizado_convenio;

    @PrePersist
    public void prePersist() {
        this.data_cadastro_convenio = LocalDateTime.now();  // Define a data de cadastro na criação
    }

    @PreUpdate
    public void preUpdate() {
        this.data_atualizado_convenio = LocalDateTime.now();  // Atualiza a data de modificação ao atualizar
    }
}