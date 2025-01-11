package br.com.projeto.estudos.repositorio;

import br.com.projeto.estudos.modelo.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, String> {

    @Query(value = "SELECT  u.nome_usuario, " +
            "p.sexo_paciente, " +
            "p.data_nascimento_paciente, " +
            "p.rg_paciente, " +
            "p.cpf_paciente,  " +
            "p.altura_paciente,  " +
            "p.peso_paciente,  " +
            "p.nome_mae_paciente, " +
            "p.profissao_paciente, cargo_paciente,  " +
            "p.setor_paciente,  " +
            "p.numero_cartao_sus_paciente, " +
            "p.status_paciente " +
            "FROM estudos.paciente p " +
            "INNER JOIN estudos.usuario u ON u.id_usuario = p.id_usuario_paciente " +
            "WHERE LOWER(u.nome_usuario) LIKE LOWER(CONCAT('%', :nome_usuario, '%'))")
    Page<Paciente> listarPacientesPorNome(@Param("nome_usuario") String nomeUsuario, Pageable pageable);

    @Query(value = "SELECT  u.nome_usuario, " +
            "p.sexo_paciente, " +
            "p.data_nascimento_paciente, " +
            "p.rg_paciente, " +
            "p.cpf_paciente,  " +
            "p.altura_paciente,  " +
            "p.peso_paciente,  " +
            "p.nome_mae_paciente, " +
            "p.profissao_paciente, cargo_paciente,  " +
            "p.setor_paciente,  " +
            "p.numero_cartao_sus_paciente, " +
            "p.status_paciente " +
            "FROM estudos.paciente p " +
            "INNER JOIN estudos.usuario u ON u.id_usuario = p.id_usuario_paciente " +
            "WHERE p.id_usuario_paciente = :id_usuario_paciente")
    Optional<Paciente> buscarPacientePorID(@Param("id_paciente") String idPaciente);

}