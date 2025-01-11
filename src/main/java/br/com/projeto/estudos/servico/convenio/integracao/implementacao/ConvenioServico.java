package br.com.projeto.estudos.servico.convenio.integracao.implementacao;

import br.com.projeto.estudos.dto.ConvenioDTO;
import java.util.List;

public interface ConvenioServico {

    // Método para salvar um convênio
    ConvenioDTO salvarConvenio(ConvenioDTO convenioDTO);

    // Método para alterar um convênio existente
    ConvenioDTO alterarConvenio(ConvenioDTO convenioDTO);

    // Método para obter um convênio pelo ID
    ConvenioDTO obterConvenioPorId(String  id);

    // Método para listar todos os convênios
    List<ConvenioDTO> listarConvenios();

    // Método para deletar um convênio pelo ID
    void deletarConvenio(String  id);
}
