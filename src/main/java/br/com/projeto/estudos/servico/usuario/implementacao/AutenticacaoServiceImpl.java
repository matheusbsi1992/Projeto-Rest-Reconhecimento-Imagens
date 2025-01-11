package br.com.projeto.estudos.servico.usuario.implementacao;

import br.com.projeto.estudos.dto.ContaCredencialDTO;
import br.com.projeto.estudos.dto.TokenDTO;
import br.com.projeto.estudos.repositorio.UsuarioRepositorio;
import br.com.projeto.estudos.seguranca.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AutenticacaoServiceImpl {

    private JwtTokenProvider jwtTokenProvider;

    private AuthenticationManager authenticationManager;

    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public AutenticacaoServiceImpl(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UsuarioRepositorio usuarioRepositorio) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public ResponseEntity<?> signin(ContaCredencialDTO contaCredencialDTO) {
        try {
            var userName = contaCredencialDTO.getUsuario();
            var passwordUser = contaCredencialDTO.getSenhaUsuario();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, passwordUser));

            var user = usuarioRepositorio.buscarPorUsuario(userName);

            var tokenResponse = new TokenDTO();

            if (user != null) {
                tokenResponse = jwtTokenProvider.criarToken(userName, user.get().getRegras());
            } else {
                throw new UsernameNotFoundException("Usuario " + userName + " não Encontrado!!");
            }

            return ResponseEntity.ok(tokenResponse);

        } catch (Exception exception) {
            throw new BadCredentialsException("Usuário/Senha Inválidos !!!");
        }

    }

    public ResponseEntity<?> refreshToken(String usuario, String refreshToken) {

        var user = usuarioRepositorio.buscarPorUsuario(usuario);

        var tokenResponse = new TokenDTO();

        if (user != null) {
            tokenResponse = jwtTokenProvider.atualizarToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Usuario " + usuario + " não Encontrado!!");
        }

        return ResponseEntity.ok(tokenResponse);

    }
}
