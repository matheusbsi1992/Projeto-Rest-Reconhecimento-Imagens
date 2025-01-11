package br.com.projeto.estudos.dto;

import br.com.projeto.estudos.mapeamento.UsuarioMapeada;
import br.com.projeto.estudos.modelo.Paciente;
import br.com.projeto.estudos.modelo.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO {

    @JsonProperty("usuario")
    private UsuarioDTO usuario;

    private String cpfPaciente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private Double pesoPaciente;

    private String cargoPaciente;

    private String profissaoPaciente;

    private String setorPaciente;

    private String numeroCartaoSusPaciente;

    @NotEmpty(message = "O sexo do paciente deve ser selecionado")
    private String sexoPaciente;

    private String rgPaciente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private Double alturaPaciente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataNascimentoPaciente;

    private String nomeMaePaciente;



}