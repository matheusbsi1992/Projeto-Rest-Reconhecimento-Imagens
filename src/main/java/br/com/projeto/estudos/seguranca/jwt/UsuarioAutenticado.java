package br.com.projeto.estudos.seguranca.jwt;


import br.com.projeto.estudos.modelo.Usuario;
import br.com.projeto.estudos.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAutenticado {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public boolean temPermissao(String permissao) {
        for (String permissaoNova : getUsuarioLogado().getRegras()) {
            if(permissaoNova.equalsIgnoreCase(permissao)) return true;
        }
        return false;
    }

    public Usuario getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();

            if (principal instanceof Usuario) {
                Usuario usuario = (Usuario) principal;

                return usuarioRepositorio.buscarPorUsuario(usuario.getUsuario())
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
            }
        }
        throw new SecurityException("Usuário não autenticado ou inválido.");
    }
}