package br.com.projeto.estudos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaCredencialDTO {

    @Valid

    @NotBlank(message = "O usuário é obrigatório")
    @Pattern(
            regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Usuário/Senha Inválidos"
    )
    private String usuario;

    @NotBlank(message = "A senha é obrigatório")
    @Length(message = "Senha deve ter no mínimo 08 caracteres e no máximo 20 caracteres", min = 8, max = 20)
    /*@Pattern(
            regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "E-mail inválido"
    )*/
    private String senhaUsuario;

}
