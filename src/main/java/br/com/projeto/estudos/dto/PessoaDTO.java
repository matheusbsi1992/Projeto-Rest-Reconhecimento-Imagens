package br.com.projeto.estudos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PessoaDTO  {

    @Valid
    @JsonProperty(namespace = "usuario")
    private UsuarioDTO usuario; // Paciente|Cliente|Usuario|Profissional

    //@Valid
    @JsonProperty(namespace = "unidade")
    private List<UnidadeDTO> unidade; // Se o local for uma unidade

    //@Valid
    @JsonProperty(namespace = "filial")
    private List<FilialDTO> filial; // Se o local for uma filial

    @NotBlank(message = "Informe o devido local: UNIDADE ou FILIAL")
    private String localTipo; // 'filial' ou 'unidade'

    @JsonProperty("servico")
    private List<ServicoDTO> servicoDTOList;

    //@NotBlank(message = "Informe o Usuário do Local - UNIDADE ou FILIAL")
    @JsonProperty(namespace = "usuariolocal")
    private List<UsuarioDTO> usuarioList; // --Identificar o ID correspodente do Usuario

    /*@JsonProperty(value = "Convenio", required = false)
    private List<ConvenioDTO> convenioDTOList; // Adiciona convenio
    */
}