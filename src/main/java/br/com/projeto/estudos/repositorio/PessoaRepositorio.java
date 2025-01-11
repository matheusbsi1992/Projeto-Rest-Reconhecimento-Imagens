package br.com.projeto.estudos.repositorio;

import br.com.projeto.estudos.modelo.Pessoa;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PessoaRepositorio extends JpaRepository<Pessoa, String> {//, PessoaLocalId> {

    // Consultar os locais associados a um usuário/paciente
    @Query("SELECT pl FROM Pessoa pl WHERE pl.usuario.id_usuario = :idUsuario")
    Optional<Pessoa> findByPessoaId(@Param("idUsuario") String idUsuario);

    // Consultar os dados da pessoa usuário/paciente
    @Query("SELECT pl FROM Pessoa pl WHERE pl.usuario.id_usuario = :idUsuario")
    Pessoa buscarPessoa(@Param("idUsuario") String idUsuario) throws UsernameNotFoundException;

    
}