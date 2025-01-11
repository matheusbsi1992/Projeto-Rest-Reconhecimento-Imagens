package br.com.projeto.estudos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "pessoa", schema = "estudos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @Column(name = "id_pessoa", nullable = false, columnDefinition = "CHAR(26) DEFAULT estudos.generate_ulid()")
    private String id_pessoa; // PK, será preenchido automaticamente pelo id_usuario

    @Column(name = "id_unidade", columnDefinition = "CHAR(26)")
    private String id_unidade; // FK, pode ser NULL

    @Column(name = "id_filial", columnDefinition = "CHAR(26)")
    private String id_filial; // FK, pode ser NULL

    @Column(name = "id_pessoa_unidade_principal")
    private String id_pessoa_unidade_principal; // Atributo identificado pelo id da UNIDADE correspondente

    @ManyToOne
    @MapsId // id_pessoa será o mesmo que id_usuario
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id_usuario")
    private Usuario usuario; // Paciente|Cliente|Usuario|Profissional

    @OneToMany
    @JoinColumn(name = "id_unidade", referencedColumnName = "id_unidade")
    //,insertable = false,updatable = false)
    private List<Unidade> unidade; // Se o local for uma unidade

    //@ManyToOne
    @OneToMany
    @JoinColumn(name = "id_filial", referencedColumnName = "id_filial") //insertable = false, updatable = false)
    private List<Filial> filial; // Se o local for uma filial

    @Column(name = "local_tipo")
    private String local_tipo; // 'filial' ou 'unidade'

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pessoa_uniao_servico",
            schema = "estudos",
            joinColumns = {@JoinColumn(name = "id_pessoa")},
            inverseJoinColumns = {@JoinColumn(name = "id_servico")})
    private List<Servico> servico;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pessoa_uniao_convenio",
            schema = "estudos",
            joinColumns = @JoinColumn(name = "id_pessoa"),
            inverseJoinColumns = @JoinColumn(name = "id_convenio")
    )
    private List<Convenio> convenios;
}
