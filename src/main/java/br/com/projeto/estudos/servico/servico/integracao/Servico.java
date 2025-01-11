package br.com.projeto.estudos.servico.servico.integracao;

import br.com.projeto.estudos.dto.ServicoDTO;

import java.util.List;

public interface Servico {

    ServicoDTO obterServicoPorId(Long id);

    List<ServicoDTO> listarServicos();

    List<ServicoDTO> listarServicosPorNomeDeServico(String nomeServico);

    ServicoDTO salvarServico(ServicoDTO ServicoDTO);

    ServicoDTO alterarServico(ServicoDTO ServicoDTO);

    void deletarServico(Long id);

    void desativarServico(Long id);
}