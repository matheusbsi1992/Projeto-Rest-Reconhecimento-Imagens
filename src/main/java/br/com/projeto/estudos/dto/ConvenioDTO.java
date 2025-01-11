package br.com.projeto.estudos.dto;

import br.com.projeto.estudos.modelo.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConvenioDTO {

    private String idConvenio;

    //@Valid
    private EnderecoDTO endereco;

    //@NotBlank(message = "O nome fantasia é obrigatório")
    private String nomeFantasiaConvenio;

    //@NotBlank(message = "A razão social é obrigatória")
    private String razaoSocialConvenio;

    //@NotBlank(message = "O CNPJ é obrigatório")
    /*@Pattern(
            regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}",
            message = "CNPJ inválido"
    )*/
    private String cnpjConvenio;

    //@NotBlank(message = "O e-mail é obrigatório")
    //@Null(message = "")
    /*@Email(message = "E-mail inválido",regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")*/
    private String emailConvenio;

    /*@Pattern(
            regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}",
            message = "Telefone inválido"
    )*/
    private String telefoneConvenio;

    private String setorConvenio;

    //Unidade associada
    /*@JsonProperty(namespace = "unidade")
    private List<UnidadeDTO> unidade;*/

    //Pessoa associada
    @JsonProperty(namespace = "pessoa")
    @JsonIgnore()
    private List<PessoaDTO> pessoa;

}