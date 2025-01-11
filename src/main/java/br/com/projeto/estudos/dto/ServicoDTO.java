package br.com.projeto.estudos.dto;

import br.com.projeto.estudos.validacao.validadorServico.ValidarErrorServico;
import br.com.projeto.estudos.validacao.validadorUnidade.ValidarErrorUnidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ValidarErrorServico
public class ServicoDTO {

    private Long idServico;

    @NotBlank(message = "O nome do serviço é obrigatório")
    private String nomeServico;

    @NotBlank(message = "A descrição do serviçõ é obrigatório")
    private String descricaoServico;

    private Boolean statusServico;
}