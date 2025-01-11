package br.com.projeto.estudos.servico.usuario.integracao;

import br.com.projeto.estudos.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioServico {

    UsuarioDTO obterUsuarioPorId(String  id);

    Page<UsuarioDTO> listarUsuarios(Pageable pageable);

    List<UsuarioDTO> listarUsuariosPorNome(String nomeUsuario);

    UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO);

    UsuarioDTO alterarUsuario(UsuarioDTO usuarioDTO);

    void deletarUsuario(String  id);
}

