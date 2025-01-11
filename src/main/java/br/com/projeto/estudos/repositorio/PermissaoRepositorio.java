package br.com.projeto.estudos.repositorio;

import br.com.projeto.estudos.modelo.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PermissaoRepositorio extends JpaRepository<Permissao, Long> {

    @Query("SELECT id_permissao FROM Permissao p WHERE p.nome_permissao = :nome_permissao")
    Long buscarPorPermissao(@Param("nome_permissao") String nomePermissao);

}