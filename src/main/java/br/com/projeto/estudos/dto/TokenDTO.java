package br.com.projeto.estudos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    private String usuarioToken;
    private Boolean autenticadoToken;
    private Date criacaoToken;
    private Date expiradoToken;
    private String acessoToken;
    private String atualizarToken;

}
