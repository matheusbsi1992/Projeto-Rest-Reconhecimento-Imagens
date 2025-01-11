package br.com.projeto.estudos.repositorio;

import br.com.projeto.estudos.modelo.Unidade;
import br.com.projeto.estudos.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.usuario = :usuario")
    Optional<Usuario> buscarPorUsuario(@Param("usuario") String usuario);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Usuario u WHERE u.usuario = :usuario")
    boolean buscarUsuario(@Param("usuario") String usuario);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Usuario u WHERE u.usuario = :usuario AND u.id_usuario <> :id_usuario")
    boolean buscarUsuario(@Param("usuario") String usuario, @Param("id_usuario") String idUsuario);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome_usuario) LIKE LOWER(CONCAT('%', :nome_usuario, '%'))")
    List<Usuario> listarUsuarioPorNome(@Param("nome_usuario") String nomeUsuario);

    //    @Query("SELECT u FROM Usuario u JOIN FETCH u.permissaoList WHERE u.usuario = :usuario")
    //    Usuario buscarPorUsuario(@Param("usuario") String usuario);
}
