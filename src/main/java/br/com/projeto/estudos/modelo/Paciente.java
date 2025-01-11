package br.com.projeto.estudos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

@Entity
@Table(name = "paciente", schema = "estudos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_paciente", nullable = false)
    private String id_usuario_paciente;

    @ManyToOne
    @MapsId // id_usuario_paciente será o mesmo que id_usuario
    @JoinColumn(name = "id_usuario_paciente", referencedColumnName = "id_usuario")
    private Usuario usuario; // Paciente|Cliente|Usuario|Profissional

    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    /*@Column(name = "nome_paciente")
    private String nome_paciente;*/

    @Column(name = "cpf_paciente")
    private String cpf_paciente;

    @Column(name = "peso_paciente")
    private Double peso_paciente;

    @Column(name = "cargo_paciente")
    private String cargo_paciente;

    @Column(name = "profissao_paciente")
    private String profissao_paciente;

    @Column(name = "setor_paciente")
    private String setor_paciente;

    @Column(name = "numero_cartao_sus_paciente")
    private String numero_cartao_sus_paciente;

    @Column(name = "sexo_paciente")
    private String sexo_paciente;

    @Column(name = "rg_paciente")
    private String rg_paciente;

    @Column(name = "altura_paciente")
    private Double altura_paciente;

    @Column(name = "data_nascimento_paciente")
    private Date data_nascimento_paciente;

    @Column(name = "nome_mae_paciente")
    private String nome_mae_paciente;

    @Column(name = "status_paciente")
    private boolean status_paciente;

    @PrePersist
    public void prePersist() {
        this.status_paciente = Boolean.TRUE;
    }

}