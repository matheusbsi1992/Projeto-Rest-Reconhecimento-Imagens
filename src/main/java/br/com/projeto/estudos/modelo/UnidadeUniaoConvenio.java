/*
package br.com.projeto.estudos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "unidade_uniao_convenio", schema = "estudos")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeUniaoConvenio {


    @ManyToOne
    @JoinColumn(name = "id_convenio")
    private Convenio convenio;

    @ManyToOne
    @JoinColumn(name = "id_unidade")
    private Unidade unidade;

    // getters e setters
}*/
