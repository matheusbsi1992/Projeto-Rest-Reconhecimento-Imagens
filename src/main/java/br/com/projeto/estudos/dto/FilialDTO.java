package br.com.projeto.estudos.dto;

//import br.com.projeto.estudos.validacao.validadorFilial.ValidarErrorFilial;
import br.com.projeto.estudos.validacao.validadorFilial.ValidarErrorFilial;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ValidarErrorFilial
public class FilialDTO {

    private String  idFilial;

    //@Valid
    private EnderecoDTO endereco;

    //@Valid
    @JsonProperty("unidade")
    private List<UnidadeDTO> unidadeList;

    @NotBlank(message = "O CPF ou CNPJ é obrigatório")
    @Pattern(
            regexp = "(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})|(\\d{11})|(\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})|(\\d{14})",
            message = "CPF ou CNPJ inválido"
    )
    private String documentoFilial;

    @NotBlank(message = "O e-mail é obrigatório")
    @Pattern(
            regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "E-mail inválido"
    )
    private String emailFilial;

    @NotBlank(message = "O nome da fantasia da Filial é obrigatória")
    private String nomeFantasiaFilial;

    private String setorFilial;

    @NotBlank(message = "A razão social é obrigatória")
    private String razaoSocialFilial;

    //@Pattern(regexp = "\\d{n-1}", message = "informar necessidade de tetefone")
    private String telefoneFilial;

    private Boolean statusFilial;


    /*@JsonProperty("convenio")
    private List<ConvenioDTO> convenioList;
*/
    /*@JsonProperty("permissao")
    private List<PermissaoDTO> permissaoList;
*/
    /*private LocalDateTime dataCadastroFilial;

    private LocalDateTime dataAlterarFilial;

    @PrePersist
    public void prePersist() {
        this.dataCadastroFilial = LocalDateTime.now();  // Define a data de cadastro na criação
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAlterarFilial = LocalDateTime.now();  // Atualiza a data de modificação ao atualizar
    }*/

}