package br.com.projeto.estudos.servico.unidade.integracao;

import br.com.projeto.estudos.dto.UnidadeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnidadeServico {

    UnidadeDTO obterUnidadePorId(String id);

    Page<UnidadeDTO> listarUnidades(Pageable pageable);

    Page<UnidadeDTO> listarUnidadesPorNomeDeFantasia(String nomeFantasiaUnidade, Pageable pageable);

    UnidadeDTO salvarUnidade(UnidadeDTO unidadeDTO);

    UnidadeDTO alterarUnidade(UnidadeDTO unidadeDTO);

    void deletarUnidade(String id);

    void desativarUnidade(String id);
}