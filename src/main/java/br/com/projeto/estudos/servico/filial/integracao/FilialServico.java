package br.com.projeto.estudos.servico.filial.integracao;

import br.com.projeto.estudos.dto.FilialDTO;

import java.util.List;

public interface FilialServico {

    FilialDTO obterFilialPorId(String  id);

    List<FilialDTO> listarFilials();

    List<FilialDTO> listarFilialsPorNomeDeFantasia(String nomeFantasiaFilial);

    FilialDTO salvarFilial(FilialDTO FilialDTO);

    FilialDTO alterarFilial(FilialDTO FilialDTO);

    void deletarFilial(String  id);

    void desativarFilial(String  id);
}

