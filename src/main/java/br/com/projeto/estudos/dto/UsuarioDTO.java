package br.com.projeto.estudos.dto;


import br.com.projeto.estudos.validacao.validadorUsuario.ValidarErrorUsuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ValidarErrorUsuario
public class UsuarioDTO {

    private String idUsuario;

    /*@Valid
    private UnidadeDTO unidade;*/

    /*@Valid
    private EnderecoDTO endereco;
*/
    @NotBlank(message = "O nome do usuário é obrigatório")
    @Size(message = "Quantidade de caracteres de ser entre 20 e 100")
    private String nomeUsuario;

    @NotBlank(message = "O e-mail é obrigatório")
    @Pattern(
            regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "E-mail inválido"
    )
    private String usuario;

    @NotBlank(message = "A senha do usuário é obrigatória")
    @Length(message = "Senha deve ter no mínimo 08 caracteres e no máximo 20 caracteres", min = 8, max = 20)
    private String senhaUsuario;

    @Column(name = "foneUsuario")
    private String foneUsuario;

    private Boolean statusUsuario;

    public Boolean accountNonExpiredUsuario;

    public Boolean accountNonLockedUsuario;

    public Boolean credentialsNonExpiredUsuario;

    @JsonProperty("permissao")
    private List<PermissaoDTO> permissaoDTOList;
}