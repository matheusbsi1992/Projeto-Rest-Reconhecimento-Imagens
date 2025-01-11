package br.com.projeto.estudos.dto;


import br.com.projeto.estudos.validacao.validadorUnidade.ValidarErrorUnidade;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnderecoDTO {

    private Long idEndereco;

    private String bairroEndereco;

    @Pattern(
            regexp = "\\d{5}-\\d{3}|\\d{8}",
            message = "CEP inválido. Formato correto: XXXXX-XXX ou XXXXXXXXX"
    )
    private String cepEndereco;

    private String cidadeEndereco;

    private String complementoEndereco;

    @Pattern(
            regexp = "AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO",
            message = "Estado inválido. Use uma sigla de estado brasileiro (ex: SP, RJ, MG, SE)."
    )
    private String estadoEndereco;

    private String logradouroEndereco;

    private String numeroEndereco;

}
