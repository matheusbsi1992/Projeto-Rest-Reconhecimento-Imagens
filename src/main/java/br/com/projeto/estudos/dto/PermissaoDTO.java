package br.com.projeto.estudos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissaoDTO {

    private Long idPermissao;

    private String nomePermissao;

    public PermissaoDTO(Long idPermissao) {
        this.idPermissao = idPermissao;
    }

    public PermissaoDTO(String nomePermissao) {
        this.nomePermissao = nomePermissao;
    }
}
