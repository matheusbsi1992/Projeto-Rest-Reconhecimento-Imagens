package br.com.projeto.estudos.modelo;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "endereco", schema = "estudos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long id_endereco;

    @Column(name = "bairro_endereco")
    private String bairro_endereco;

    @Column(name = "cep_endereco")
    private String cep_endereco;

    @Column(name = "cidade_endereco")
    private String cidade_endereco;

    @Column(name = "complemento_endereco")
    private String complemento_endereco;

    @Column(name = "estado_endereco")
    private String estado_endereco;

    @Column(name = "logradouro_endereco")
    private String logradouro_endereco;

    @Column(name = "numero_endereco")
    private String numero_endereco;

}
