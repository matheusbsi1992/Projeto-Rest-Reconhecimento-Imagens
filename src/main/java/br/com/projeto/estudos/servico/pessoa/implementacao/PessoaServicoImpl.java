package br.com.projeto.estudos.servico.pessoa.implementacao;

import br.com.projeto.estudos.dto.*;
import br.com.projeto.estudos.mapeamento.FilialMapeada;
import br.com.projeto.estudos.mapeamento.PessoaMapeada;
import br.com.projeto.estudos.mapeamento.UnidadeMapeada;
import br.com.projeto.estudos.mapeamento.UsuarioMapeada;
import br.com.projeto.estudos.modelo.*;
import br.com.projeto.estudos.repositorio.FilialRepositorio;
import br.com.projeto.estudos.repositorio.PessoaRepositorio;
import br.com.projeto.estudos.repositorio.ServicoRepositorio;
import br.com.projeto.estudos.repositorio.UsuarioRepositorio;
import br.com.projeto.estudos.seguranca.jwt.UsuarioAutenticado;
import br.com.projeto.estudos.servico.filial.implementacao.FilialServicoImpl;
import br.com.projeto.estudos.servico.unidade.implementacao.UnidadeServicoImpl;
import br.com.projeto.estudos.servico.usuario.implementacao.UsuarioServicoImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class PessoaServicoImpl {

    private Logger logger = Logger.getLogger(PessoaServicoImpl.class.getName());

    private FilialRepositorio filialRepositorio;
    private FilialMapeada filialMapeada;
    private FilialServicoImpl filialServico;

    private UsuarioAutenticado usuarioAutenticado;

    private UsuarioServicoImpl usuarioServico;
    private UsuarioMapeada usuarioMapper;
    private UsuarioRepositorio usuarioRepositorio;

    private Pessoa pessoa;
    private PessoaRepositorio pessoaRepositorio;
    private PessoaMapeada pessoaMapeada;

    private UnidadeMapeada unidadeMapper;
    private UnidadeServicoImpl unidadeServico;

    private ServicoRepositorio servicoRepositorio;

    @Autowired
    public PessoaServicoImpl(FilialRepositorio filialRepositorio,
                             UsuarioAutenticado usuarioAutenticado,
                             UsuarioServicoImpl usuarioServico,
                             UsuarioMapeada usuarioMapper,
                             UsuarioRepositorio usuarioRepositorio,
                             //Pessoa pessoa,
                             FilialMapeada filialMapeada,
                             FilialServicoImpl filialServico,
                             PessoaRepositorio pessoaRepositorio,
                             PessoaMapeada pessoaMapeada,
                             UnidadeMapeada unidadeMapper,
                             UnidadeServicoImpl unidadeServico,
                             ServicoRepositorio servicoRepositorio) {

        this.filialRepositorio = filialRepositorio;
        this.filialMapeada = filialMapeada;
        this.filialServico = filialServico;

        this.usuarioAutenticado = usuarioAutenticado;
        this.usuarioServico = usuarioServico;
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;

        this.pessoa = Objects.requireNonNull(new Pessoa());
        this.pessoaRepositorio = pessoaRepositorio;
        this.pessoaMapeada = pessoaMapeada;

        this.unidadeMapper = unidadeMapper;
        this.unidadeServico = unidadeServico;

        this.servicoRepositorio = servicoRepositorio;
    }

    //@Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public PessoaDTO salvarPessoaUnidadeFilial(PessoaDTO pessoaDTO) {

        logger.info("Salvando Usuario & (Unidade|Filial)" + pessoaDTO);

        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {

            if (pessoaDTO.getUsuario() != null) {

                var usuarioDTO  = usuarioServico.salvarUsuario(pessoaDTO.getUsuario());

                var usuario     = usuarioMapper.usuarioDTOParaUsuario(usuarioDTO);

                pessoa.setId_pessoa_unidade_principal(usuario.getId_usuario());

                pessoa.setId_pessoa(usuario.getId_usuario());

                pessoa.setUsuario(usuario);

                //usuario = null;
            }

            if (pessoaDTO.getLocalTipo().equalsIgnoreCase("FILIAL")) {

                pessoa.setLocal_tipo(pessoaDTO.getLocalTipo());

                /*
                var filialDTO = filialServico.salvarFilial(pessoaDTO.getFilial());
                var filial = filialMapeada.filialDTOParafilial(filialDTO);
                pessoa.setId_filial(filial.getId_filial());
                pessoa.setFilial(filial);
                pessoa.setLocal_tipo(pessoaDTO.getLocalTipo());
                */
                retornarUnidadeEOUFilial(pessoaDTO);

                //filial = null;
            }

            if (pessoaDTO.getLocalTipo().equalsIgnoreCase("UNIDADE")) {

                pessoa.setLocal_tipo(pessoaDTO.getLocalTipo());

                pessoa.setFilial(null);

                retornarUnidadeEOUFilial(pessoaDTO);

            }

            // Buscar os servicos existentes
            if (pessoaDTO.getServicoDTOList() != null && !pessoaDTO.getServicoDTOList().isEmpty()) {
                List<Servico> servicosExistentes = new ArrayList<>();
                for (ServicoDTO servicoDTO : pessoaDTO.getServicoDTOList()) {
                    Servico servicoExistente = servicoRepositorio.findById(servicoDTO.getIdServico())
                            .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));
                    servicosExistentes.add(servicoExistente);
                }
                pessoa.setServico(servicosExistentes);
            }

            var novaPessoa = pessoaRepositorio.save(pessoa);

            return pessoaMapeada.pessoaParaPessoaDTO(novaPessoa);
        }
        throw new SecurityException("Acesso negado para Salvar Usuário");
    }


    private void retornarUnidadeEOUFilial(PessoaDTO pessoaDTO) {

        List<Unidade> unidadeExistente = new ArrayList<>();

        List<Filial> filialExistente = new ArrayList<>();

        if (pessoaDTO.getLocalTipo().equalsIgnoreCase("UNIDADE")) {
            for (UnidadeDTO unidade : pessoaDTO.getUnidade()) {

                var unidadeEncontradoDTO = unidadeServico.obterUnidadePorId(unidade.getIdUnidade());

                unidadeExistente.add(pessoaMapeada.mapUnidadeDTOToUnidade(unidadeEncontradoDTO));

                pessoa.setUnidade(unidadeExistente);

                pessoa.setId_unidade(unidadeExistente.getFirst().getId_unidade());

                //pessoa.setId_pessoa_unidade_principal(unidadeExistente.getFirst().getId_unidade());
                unidade = null;
            }
        } else {

            // Buscar usuario respectivo a Unidade
            if (pessoaDTO.getUsuarioList() != null && !pessoaDTO.getUsuarioList().isEmpty()) {
                //List<Usuario> usuariosExistentes = new ArrayList<>();
                for (UsuarioDTO pessoaUnidadeDTO : pessoaDTO.getUsuarioList()) {
                    //  for (int i = 0; i < pessoaDTO.getUsuarioDTOList().size(); i++) {

                    Usuario usuarioExistente = usuarioRepositorio.findById(pessoaUnidadeDTO.getIdUsuario())
                            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
                    //usuariosExistentes.add(usuarioExistente);

                    pessoa.setId_pessoa_unidade_principal(usuarioExistente.getId_usuario());
                }
            }

            for (UnidadeDTO unidade : pessoaDTO.getUnidade()) {

                var unidadeEncontradoDTO = unidadeServico.obterUnidadePorId(unidade.getIdUnidade());

                unidadeExistente.add(pessoaMapeada.mapUnidadeDTOToUnidade(unidadeEncontradoDTO));

                pessoa.setUnidade(unidadeExistente);

                pessoa.setId_unidade(unidadeExistente.getFirst().getId_unidade());

                //pessoa.setId_pessoa_unidade_principal(unidadeExistente.getFirst().getId_unidade());

               // unidade = null;
            }
            for (FilialDTO filial : pessoaDTO.getFilial()) {

                var filialEncontradaDTO = filialServico.obterFilialPorId(filial.getIdFilial());

                filialExistente.add(pessoaMapeada.mapFilialDTOToFilial(filialEncontradaDTO));

                pessoa.setFilial(filialExistente);

                pessoa.setId_filial(filialExistente.getFirst().getId_filial());

                //filial = null;
            }
        }
    }


}


/*
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public UsuarioDTO alterarUsuario(UsuarioDTO usuarioDTO) {
    logger.info("Alterando Usuario..." + usuarioDTO);

    if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
        // Busca o usuário existente para preservar a data de cadastro
        Usuario usuarioExistente = usuarioRepositorio.findById(usuarioDTO.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // Mapeia o DTO para a entidade sem sobrescrever a data de cadastro
        Usuario usuarioAtualizado = usuarioMapper.usuarioDTOParaUsuario(usuarioDTO);

        // Mantém a data de cadastro do usuário existente
        usuarioAtualizado.setData_cadastro_usuario(usuarioExistente.getData_cadastro_usuario());

        // Define a data de atualização
        usuarioAtualizado.setData_atualizado_usuario(LocalDateTime.now());

        List<Permissao> permissoesExistentes = new ArrayList<>();
        for (Permissao permissao : usuarioAtualizado.getPermissaoList()) {
            Permissao permissaoExistente = permissaoRepositorio.findById(permissao.getId_permissao())
                    .orElseThrow(() -> new EntityNotFoundException("Permissão não encontrada"));
            permissoesExistentes.add(permissaoExistente);
        }
        usuarioAtualizado.setPermissaoList(permissoesExistentes);

        // Salva o usuário atualizado no repositório
        Usuario usuarioSalvo = usuarioRepositorio.save(usuarioAtualizado);
        return usuarioMapper.usuarioParaUsuarioDTO(usuarioSalvo);
    }
    throw new SecurityException("Acesso negado para Atualizar Usuário");
}


@PreAuthorize("hasAnyRole('ROLE_ADMIN')")

public UsuarioDTO obterUsuarioPorId(Long id) {
    Usuario usuario = usuarioRepositorio.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(("Usuário não encontrado")));
    return usuarioMapper.usuarioParaUsuarioDTO(usuario);
}

@PreAuthorize("hasAnyRole('ROLE_ADMIN')")

public List<UsuarioDTO> listarUsuarios() {
    if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios.stream()
                .map(usuarioMapper::usuarioParaUsuarioDTO)
                .collect(Collectors.toList());
    }
    throw new SecurityException("Acesso negado para Listar Usuários");
}

@PreAuthorize("hasAnyRole('ROLE_ADMIN')")

public List<UsuarioDTO> listarUsuariosPorNome(String nomeUsuario) {
    if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
        List<Usuario> usuarios = usuarioRepositorio.listarUsuarioPorNome(nomeUsuario);
        return usuarios.stream()
                .map(usuarioMapper::usuarioParaUsuarioDTO)
                .collect(Collectors.toList());
    }
    throw new SecurityException("Acesso negado para Listar Usuários por Nome");
}

@PreAuthorize("hasAnyRole('ROLE_ADMIN')")

public void deletarUsuario(Long id) {
    if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(("Usuário não encontrado")));
        usuarioRepositorio.deleteById(usuario.getId_usuario());
    }
    throw new SecurityException("Acesso negado para Deletar Usuário");
}


*/


