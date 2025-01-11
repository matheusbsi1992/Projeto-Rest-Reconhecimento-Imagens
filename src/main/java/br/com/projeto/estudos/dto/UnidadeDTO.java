package br.com.projeto.estudos.dto;

import br.com.projeto.estudos.modelo.Convenio;
import br.com.projeto.estudos.modelo.Permissao;
import br.com.projeto.estudos.validacao.validadorUnidade.ValidarErrorUnidade;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ValidarErrorUnidade
public class UnidadeDTO {

    private String  idUnidade;

    @Valid
    private EnderecoDTO endereco;

    @NotBlank(message = "O CPF ou CNPJ é obrigatório")
    @Pattern(
            regexp = "(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})|(\\d{11})|(\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})|(\\d{14})",
            message = "CPF ou CNPJ inválido"
    )
    private String documentoUnidade;

    @NotBlank(message = "O e-mail é obrigatório")
    @Pattern(
            regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "E-mail inválido"
    )
    private String emailUnidade;

    @NotBlank(message = "O nome da fantasia da Unidade é obrigatória")
    private String nomeFantasiaUnidade;

    private String setorUnidade;

    @NotBlank(message = "A razão social é obrigatória")
    private String razaoSocialUnidade;

    //@Pattern(regexp = "\\d{n-1}", message = "informar necessidade de tetefone")
    private String telefoneUnidade;

    private Boolean statusUnidade;

    /*@JsonProperty("convenio")
    private List<ConvenioDTO> convenioList;
*/
    /*@JsonProperty("permissao")
    private List<PermissaoDTO> permissaoList;
*/
    /*private LocalDateTime dataCadastroUnidade;

    private LocalDateTime dataAlterarUnidade;

    @PrePersist
    public void prePersist() {
        this.dataCadastroUnidade = LocalDateTime.now();  // Define a data de cadastro na criação
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAlterarUnidade = LocalDateTime.now();  // Atualiza a data de modificação ao atualizar
    }*/

}