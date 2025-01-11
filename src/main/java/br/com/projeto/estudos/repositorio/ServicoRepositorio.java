package br.com.projeto.estudos.repositorio;


import br.com.projeto.estudos.modelo.Servico;
import br.com.projeto.estudos.modelo.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepositorio extends JpaRepository<Servico, Long> {

    @Query("SELECT s FROM Servico s WHERE LOWER(s.nome_servico) LIKE LOWER(CONCAT('%', :nome_servico, '%'))")
    List<Servico> listarServicosPorNomeDeServico(@Param("nome_servico") String nomeServico);

    @Query("SELECT s FROM Servico s WHERE s.id_servico = :id_servico")
    List<Servico> listarServicosPorIdServico(@Param("id_servico") Long idServico);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Servico s WHERE UPPER(s.nome_servico) = :nome_servico")
    boolean buscarNomeServico(@Param("nome_servico") String nomeServico);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Servico s WHERE s.status_servico = :status_servico")
    boolean buscarStatusServico(@Param("status_servico") String statusServico);

    //--- para atualizacao do Servico
    // Verifica se existe outro registro com o mesmo nome do servico
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Servico s WHERE s.nome_servico = :nome_servico AND s.id_servico <> :id_servico")
    boolean buscarNomeServico(@Param("nome_servico") String nomeServico, @Param("id_servico") Long idServico);
}