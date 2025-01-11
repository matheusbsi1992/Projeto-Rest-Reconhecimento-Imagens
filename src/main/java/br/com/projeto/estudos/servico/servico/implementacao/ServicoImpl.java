package br.com.projeto.estudos.servico.servico.implementacao;


import br.com.projeto.estudos.dto.ServicoDTO;

import br.com.projeto.estudos.mapeamento.ServicoMapeada;

import br.com.projeto.estudos.modelo.Servico;
import br.com.projeto.estudos.repositorio.ServicoRepositorio;

import br.com.projeto.estudos.seguranca.jwt.UsuarioAutenticado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ServicoImpl {

    private ServicoRepositorio servicoRepositorio;

    private ServicoMapeada servicoMapper;

    @Autowired
    private UsuarioAutenticado usuarioAutenticado;


    @Autowired
    public ServicoImpl(ServicoRepositorio servicoRepositorio, ServicoMapeada servicoMapper) {
        Objects.requireNonNull(servicoMapper);
        Objects.requireNonNull(servicoRepositorio);
        this.servicoRepositorio = servicoRepositorio;
        this.servicoMapper = servicoMapper;
    }

    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ServicoDTO salvarServico(ServicoDTO servicoDTO) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Salvar Serviço !!!");
        }
            var servico = servicoMapper.servicoDTOParaServico(servicoDTO);
            //unidade.setData_cadastro_unidade(LocalDateTime.now());
            var servicoSalva = servicoRepositorio.save(servico);

            return servicoMapper.servicoParaServicoDTO(servicoSalva);
    }

    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ServicoDTO alterarServico(ServicoDTO servicoDTO) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Atualizar Serviço");
        }
            // Busca a unidade existente para preservar a data de cadastro
            var servicoExistente = servicoRepositorio.findById(servicoDTO.getIdServico())
                    .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));
            // Mapeia o DTO para a entidade sem sobrescrever a data de cadastro
            var servicoAtualizada = servicoMapper.servicoParaServicoDTO(servicoExistente);
            // Mantém a data de cadastro da unidade existente
            //unidadeAtualizada.setData_cadastro_unidade(unidadeExistente.getData_cadastro_unidade());
            // Define a data de atualização
            //unidadeAtualizada.setData_atualizado_unidade(LocalDateTime.now());
            var servicoAlterado = servicoMapper.servicoDTOParaServico(servicoAtualizada);
            // Salva a unidade atualizada no repositório
            var servicoSalva = servicoRepositorio.save(servicoAlterado);
            // Mapeia a unidade salva para o DTO e retorna
            return servicoMapper.servicoParaServicoDTO(servicoSalva);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ServicoDTO obterServicoPorId(Long id) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN") || !usuarioAutenticado.temPermissao("ROLE_USER")) {
            throw new SecurityException("Acesso negado para Buscar Serviço !!!");
        }
        var servico = servicoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        return servicoMapper.servicoParaServicoDTO(servico);
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Page<ServicoDTO> listarServicos(Pageable pageable) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Listar Serviço");
        }
        Page<Servico> servicos = servicoRepositorio.findAll(pageable);
            return servicos//.stream()
                    .map(servicoMapper::servicoParaServicoDTO);
                    //.collect(Collectors.toList());
     /*   if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_USER")) {
            Optional<Unidade> unidadeUsuario = unidadeRepositorio.findById(usuarioAutenticado.getUsuarioLogado().getUnidade().getId_unidade());
            return unidadeUsuario.stream()
                    .map(unidadeMapper::unidadeParaUnidadeDTO)
                    .collect(Collectors.toList());
        }*/
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<ServicoDTO> listarServicosPorNomeDeServico(String nomeServico) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Listar Serviço por Nome");
        }
            List<Servico> servicos = servicoRepositorio.listarServicosPorNomeDeServico(nomeServico);
            return servicos.stream()
                    .map(servicoMapper::servicoParaServicoDTO)
                    .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deletarServico(Long id) {
        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Deletar a Servico");
        }
            Servico servico = servicoRepositorio.findById(id)
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
            servicoRepositorio.deleteById(servico.getId_servico());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void desativarServico(Long id) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Desativar a Serviço !!!");
        }

            Servico servico = servicoRepositorio.findById(id)
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
            servico.setStatus_servico(false);
            servicoRepositorio.save(servico);
    }

}