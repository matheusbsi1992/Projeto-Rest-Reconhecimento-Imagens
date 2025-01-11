package br.com.projeto.estudos.repositorio;


import br.com.projeto.estudos.modelo.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilialRepositorio extends JpaRepository<Filial, String> {

    @Query(value = "SELECT f.id_filial, " +
            "       f.id_unidade, " +
            "       f.id_endereco, " +  // Inclua o ID do endereço para evitar problemas de mapeamento
            "       f.documento_filial, " +
            "       f.email_filial, " +
            "       f.nome_fantasia_filial, " +
            "       f.setor_filial, " +
            "       f.razao_social_filial, " +
            "       f.telefone_filial, " +
            "       f.status_filial, " +
            "       f.data_cadastro_filial, " +
            "       f.data_atualizado_filial, " +
            "       u.razao_social_unidade, " +
            "       u.nome_fantasia_unidade " +
            "FROM estudos.filial f " +
            "INNER JOIN estudos.unidade u ON u.id_unidade = f.id_unidade", nativeQuery = true)
    List<Filial> listarFilials();

    @Query("SELECT u FROM Filial u WHERE LOWER(u.nome_fantasia_filial) LIKE LOWER(CONCAT('%', :nome_fantasia_filial, '%'))")
    List<Filial> listarFilialsPorNomeDeFantasia(@Param("nome_fantasia_filial") String nomeFantasiaFilial);

    @Query("SELECT u FROM Filial u WHERE u.id_filial = :id_filial")
    List<Filial> listarFilialsPorIdFilial(@Param("id_filial") String idFilial);

    //--identifica se e CNPJ ou CPF
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Filial u WHERE u.documento_filial = :documento_filial")
    boolean buscarDocumentoFilial(@Param("documento_filial") String documentoFilial);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Filial u WHERE u.razao_social_filial = :razao_social_filial")
    boolean buscarRazaoSocialFilial(@Param("razao_social_filial") String razaoSocialFilial);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Filial u WHERE u.status_filial = :status_filial")
    boolean buscarStatusFilial(@Param("status_filial") String statusFilial);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Filial u WHERE u.nome_fantasia_filial = :nome_fantasia_filial")
    boolean buscarNomeFantasiaFilial(@Param("nome_fantasia_filial") String nomeFantasiaFilial);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Filial u WHERE u.email_filial = :email_filial")
    boolean buscarEmailFilial(@Param("email_filial") String emailFilial);

    //--- para atualizacao da filial
    // Verifica se existe outro registro com o mesmo nome fantasia, exceto a filial atual
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Filial u WHERE u.nome_fantasia_filial = :nome_fantasia_filial AND u.id_filial <> :id_filial")
    boolean buscarNomeFantasiaFilial(@Param("nome_fantasia_filial") String nomeFantasia, @Param("id_filial") String idFilial);

    // Verifica se existe outro registro com a mesma razão social, exceto a filial atual
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Filial u WHERE u.razao_social_filial = :razao_social_filial AND u.id_filial <> :id_filial")
    boolean buscarRazaoSocialFilial(@Param("razao_social_filial") String razaoSocialFilial, @Param("id_filial") String idFilial);

    // Verifica se existe outro registro com o mesmo documento (CPF/CNPJ), exceto a filial atual
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Filial u WHERE u.documento_filial = :documento_filial AND u.id_filial <> :id_filial")
    boolean buscarDocumentoFilial(@Param("documento_filial") String documentoFilial, @Param("id_filial") String idFilial);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Filial u WHERE u.email_filial = :email_filial AND u.id_filial <> :id_filial")
    boolean buscarEmailFilial(@Param("email_filial") String emailFilial, @Param("id_filial") String idFilial);
}