package br.com.projeto.estudos.servico.usuario.implementacao;

import br.com.projeto.estudos.dto.PermissaoDTO;
import br.com.projeto.estudos.dto.UsuarioDTO;
import br.com.projeto.estudos.exceptions.ResourceNotFoundException;
import br.com.projeto.estudos.mapeamento.UsuarioMapeada;
import br.com.projeto.estudos.modelo.Permissao;
import br.com.projeto.estudos.modelo.Usuario;
import br.com.projeto.estudos.repositorio.PermissaoRepositorio;
import br.com.projeto.estudos.repositorio.UnidadeRepositorio;
import br.com.projeto.estudos.repositorio.UsuarioRepositorio;
import br.com.projeto.estudos.seguranca.jwt.UsuarioAutenticado;
import br.com.projeto.estudos.servico.usuario.integracao.UsuarioServico;
import br.com.projeto.estudos.util.Util;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UsuarioServicoImpl implements UserDetailsService, UsuarioServico {

    private Logger logger = Logger.getLogger(UsuarioServicoImpl.class.getName());

    private UsuarioRepositorio usuarioRepositorio;

    private UsuarioMapeada usuarioMapper;

    private UsuarioAutenticado usuarioAutenticado;

    //private UnidadeRepositorio unidadeRepositorio;

    private Util util;

    private PermissaoRepositorio permissaoRepositorio;

    @Autowired
    public UsuarioServicoImpl(UsuarioRepositorio usuarioRepositorio,
                              UsuarioMapeada usuarioMapper,
                              UsuarioAutenticado usuarioAutenticado,
                              UnidadeRepositorio unidadeRepositorio,
                              PermissaoRepositorio permissaoRepositorio,
                              Util util) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;
        this.usuarioAutenticado = usuarioAutenticado;
        //this.unidadeRepositorio = unidadeRepositorio;
        this.permissaoRepositorio = permissaoRepositorio;
        this.util = util;
    }


    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        logger.info("Procurando por Usuario..." + usuario);
        var usuarioIdentificado = this.usuarioRepositorio.buscarPorUsuario(usuario);
        if (usuarioIdentificado != null) {
            return usuarioIdentificado.get();
        } else {
            throw new UsernameNotFoundException("USUARIO NAO IDENTIFICADO: " + usuarioIdentificado);
        }
    }

    //@Transactional
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {

        logger.info("Salvando Usuario..." + usuarioDTO);

        if (usuarioAutenticado.temPermissao("ROLE_ADMIN") || usuarioAutenticado.temPermissao("ROLE_USER")) {

            var usuario = usuarioMapper.usuarioDTOParaUsuario(usuarioDTO);

            // Busca a unidade já existente
           /* if (usuarioDTO.getUnidade() != null) {

                var unidadeExistente = unidadeRepositorio.findById(usuario.getUnidade().getId_unidade())
                        .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));
                usuario.setUnidade(unidadeExistente);
            }*/

            // Busca as permissões já existentes
            if (usuarioDTO.getPermissaoDTOList() != null && !usuarioDTO.getPermissaoDTOList().isEmpty()) {
                List<Permissao> permissoesExistentes = new ArrayList<>();
                for (PermissaoDTO permissaoDTO : usuarioDTO.getPermissaoDTOList()) {
                   /* if (permissaoDTO.getNomePermissao().equalsIgnoreCase("ROLE_CUSTOMER")) {

                        Permissao novaPermissaoUsuario = permissaoRepositorio.buscarPorPermissao(permissaoDTO.getNomePermissao())
                                .orElseThrow(() -> new ResourceNotFoundException("Permissão não identificada"));
                        permissoesExistentes.add(novaPermissaoUsuario);
                    } else {*/
                    Permissao permissaoExistente = permissaoRepositorio.findById(permissaoDTO.getIdPermissao())
                            .orElseThrow(() -> new ResourceNotFoundException("Permissão não encontrada"));
                    permissoesExistentes.add(permissaoExistente);
                    /* }*/
                }
                usuario.setPermissaoList(permissoesExistentes);
            }

            // Salva o usuário
            var usuarioSalvo = usuarioRepositorio.save(usuario);

            return usuarioMapper.usuarioParaUsuarioDTO(usuarioSalvo);

        }
        throw new SecurityException("Acesso negado para Salvar Usuário");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
    public UsuarioDTO alterarUsuario(UsuarioDTO usuarioDTO) {
        logger.info("Alterando Usuario..." + usuarioDTO);

        if (usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
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
    @Override
    public UsuarioDTO obterUsuarioPorId(String id) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(("Usuário não encontrado")));
        return usuarioMapper.usuarioParaUsuarioDTO(usuario);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
    public Page<UsuarioDTO> listarUsuarios(Pageable pageable) {
        if (usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            Page<Usuario> usuarios = usuarioRepositorio.findAll(pageable);
            return usuarios//.stream()
                    .map(usuarioMapper::usuarioParaUsuarioDTO);
            //.collect(Collectors.toList());
        }
        throw new SecurityException("Acesso negado para Listar Usuários");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
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
    @Override
    public void deletarUsuario(String id) {
        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
            Usuario usuario = usuarioRepositorio.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(("Usuário não encontrado")));
            usuarioRepositorio.deleteById(usuario.getId_usuario());
        }
        throw new SecurityException("Acesso negado para Deletar Usuário");
    }

}