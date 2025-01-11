package br.com.projeto.estudos.servico.filial.implementacao;


import br.com.projeto.estudos.dto.FilialDTO;

import br.com.projeto.estudos.dto.PermissaoDTO;
import br.com.projeto.estudos.dto.UnidadeDTO;
import br.com.projeto.estudos.mapeamento.FilialMapeada;

import br.com.projeto.estudos.modelo.Filial;

import br.com.projeto.estudos.modelo.Permissao;
import br.com.projeto.estudos.modelo.Unidade;
import br.com.projeto.estudos.repositorio.FilialRepositorio;

import br.com.projeto.estudos.repositorio.PermissaoRepositorio;
import br.com.projeto.estudos.repositorio.UnidadeRepositorio;
import br.com.projeto.estudos.seguranca.jwt.UsuarioAutenticado;
import br.com.projeto.estudos.servico.filial.integracao.FilialServico;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilialServicoImpl implements FilialServico {

    private FilialRepositorio filialRepositorio;

    private FilialMapeada filialMapper;

    @Autowired
    private UsuarioAutenticado usuarioAutenticado;

    private UnidadeRepositorio unidadeRepositorio;

    @Autowired
    public FilialServicoImpl(FilialRepositorio filialRepositorio, FilialMapeada filialMapper,UnidadeRepositorio unidadeRepositorio) {
        Objects.requireNonNull(filialMapper);
        Objects.requireNonNull(filialRepositorio);
        Objects.requireNonNull(unidadeRepositorio);
        this.filialRepositorio = filialRepositorio;
        this.unidadeRepositorio = unidadeRepositorio;
        this.filialMapper = filialMapper;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public FilialDTO salvarFilial(FilialDTO filialDTO) {

        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
            Filial filial = filialMapper.filialDTOParafilial(filialDTO);
            // Busca a Unidade já existente
           if (filialDTO.getUnidadeList() != null && !filialDTO.getUnidadeList().isEmpty()) {
                List<Unidade> unidadeExistentes = new ArrayList<>();
                for (UnidadeDTO unidadeDTO : filialDTO.getUnidadeList()) {
                    Unidade unidadeExistente = unidadeRepositorio.findById(unidadeDTO.getIdUnidade())
                            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));
                    unidadeExistentes.add(unidadeExistente);
                }
               filial.setUnidade(unidadeExistentes.getFirst());
           }
            Filial filialSalva = filialRepositorio.save(filial);

            return filialMapper.filialParafilialDTO(filialSalva);
        }
        throw new SecurityException("Acesso negado para Salvar filial !!!");
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public FilialDTO alterarFilial(FilialDTO filialDTO) {
        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
            // Busca a filial existente para preservar a data de cadastro
            Filial filialExistente = filialRepositorio.findById(filialDTO.getIdFilial())
                    .orElseThrow(() -> new EntityNotFoundException("filial não encontrada"));

            // Busca a Unidade já existente
            if (filialDTO.getUnidadeList() != null && !filialDTO.getUnidadeList().isEmpty()) {
                List<Unidade> unidadeExistentes = new ArrayList<>();
                for (UnidadeDTO unidadeDTO : filialDTO.getUnidadeList()) {
                    Unidade unidadeExistente = unidadeRepositorio.findById(unidadeDTO.getIdUnidade())
                            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));
                    unidadeExistentes.add(unidadeExistente);
                }
                filialExistente.setUnidade(unidadeExistentes.getFirst());
            }

            // Mapeia o DTO para a entidade sem sobrescrever a data de cadastro
            Filial filialAtualizada = filialMapper.filialDTOParafilial(filialDTO);

            // Mantém a data de cadastro da filial existente
            filialAtualizada.setData_cadastro_filial(filialExistente.getData_cadastro_filial());

            // Define a data de atualização
            filialAtualizada.setData_atualizado_filial(LocalDateTime.now());

            // Salva a filial atualizada no repositório
            Filial filialSalva = filialRepositorio.save(filialAtualizada);
            // Mapeia a filial salva para o DTO e retorna
            return filialMapper.filialParafilialDTO(filialSalva);
        }
        throw new SecurityException("Acesso negado para Atualizar filial");
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public FilialDTO obterFilialPorId(String  id) {

        Filial filial = filialRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("filial não encontrada"));
        return filialMapper.filialParafilialDTO(filial);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public List<FilialDTO> listarFilials() {

        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
            List<Filial> filials = filialRepositorio.findAll();
            return filials.stream()
                    .map(filialMapper::filialParafilialDTO)
                    .collect(Collectors.toList());
        }
     /*   if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_USER")) {
            Optional<filial> filialUsuario = filialRepositorio.findById(usuarioAutenticado.getUsuarioLogado().getfilial().getId_filial());
            return filialUsuario.stream()
                    .map(filialMapper::filialParafilialDTO)
                    .collect(Collectors.toList());
        }*/
        throw new SecurityException("Acesso negado para Listar filial");
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<FilialDTO> listarFilialsPorNomeDeFantasia(String nomeFantasiafilial) {

        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
            List<Filial> filials = filialRepositorio.listarFilialsPorNomeDeFantasia(nomeFantasiafilial);
            return filials.stream()
                    .map(filialMapper::filialParafilialDTO)
                    .collect(Collectors.toList());
        }
        throw new SecurityException("Acesso negado para Listar filial por Nome");
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deletarFilial(String  id) {
        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
            Filial filial = filialRepositorio.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("filial não encontrada"));
            filialRepositorio.deleteById(filial.getId_filial());
        }
        throw new SecurityException("Acesso negado para Deletar a filial");
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void desativarFilial(String  id) {
        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
            Filial filial = filialRepositorio.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("filial não encontrada"));
            filial.setStatus_filial(false);
            filialRepositorio.save(filial);
        }
        throw new SecurityException("Acesso negado para Desativar a filial");
    }
}