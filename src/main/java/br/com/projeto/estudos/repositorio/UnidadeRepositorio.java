package br.com.projeto.estudos.repositorio;


import br.com.projeto.estudos.dto.UnidadeFilialPessoaLocalDTO;
import br.com.projeto.estudos.modelo.Unidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadeRepositorio extends JpaRepository<Unidade, String> {

    @Query("SELECT u FROM Unidade u WHERE LOWER(u.nome_fantasia_unidade) LIKE LOWER(CONCAT('%', :nome_fantasia_unidade, '%'))")
    Page<Unidade> listarUnidadesPorNomeDeFantasia(@Param("nome_fantasia_unidade") String nomeFantasiaUnidade, Pageable pageable);

    @Query("SELECT u FROM Unidade u WHERE u.id_unidade = :id_unidade")
    List<Unidade> listarUnidadesPorIdUnidade(@Param("id_unidade") String idUnidade);

    //--identifica se e CNPJ ou CPF
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Unidade u WHERE u.documento_unidade = :documento_unidade")
    boolean buscarDocumentoUnidade(@Param("documento_unidade") String documentoUnidade);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Unidade u WHERE u.razao_social_unidade = :razao_social_unidade")
    boolean buscarRazaoSocialUnidade(@Param("razao_social_unidade") String razaoSocialUnidade);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Unidade u WHERE u.status_unidade = :status_unidade")
    boolean buscarStatusUnidade(@Param("status_unidade") String statusUnidade);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Unidade u WHERE u.nome_fantasia_unidade = :nome_fantasia_unidade")
    boolean buscarNomeFantasiaUnidade(@Param("nome_fantasia_unidade") String nomeFantasiaUnidade);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Unidade u WHERE u.email_unidade = :email_unidade")
    boolean buscarEmailUnidade(@Param("email_unidade") String emailUnidade);

    //--- para atualizacao da Unidade
    // Verifica se existe outro registro com o mesmo nome fantasia, exceto a unidade atual
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Unidade u WHERE u.nome_fantasia_unidade = :nome_fantasia_unidade AND u.id_unidade <> :id_unidade")
    boolean buscarNomeFantasiaUnidade(@Param("nome_fantasia_unidade") String nomeFantasia, @Param("id_unidade") String idUnidade);

    // Verifica se existe outro registro com a mesma razão social, exceto a unidade atual
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Unidade u WHERE u.razao_social_unidade = :razao_social_unidade AND u.id_unidade <> :id_unidade")
    boolean buscarRazaoSocialUnidade(@Param("razao_social_unidade") String razaoSocialUnidade, @Param("id_unidade") String idUnidade);

    // Verifica se existe outro registro com o mesmo documento (CPF/CNPJ), exceto a unidade atual
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Unidade u WHERE u.documento_unidade = :documento_unidade AND u.id_unidade <> :id_unidade")
    boolean buscarDocumentoUnidade(@Param("documento_unidade") String documentoUnidade, @Param("id_unidade") String idUnidade);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Unidade u WHERE u.email_unidade = :email_unidade AND u.id_unidade <> :id_unidade")
    boolean buscarEmailUnidade(@Param("email_unidade") String emailUnidade, @Param("id_unidade") String idUnidade);

    //------encerra para atualizacao---///

    //-----Identificar Filial e Unidade Local----//
    @Query(value = "SELECT \n" +
            "    P.id_pessoa AS \"idPessoa\", \n" +
            "    P.id_pessoa_unidade_principal AS \"unidadeRepresentativa\", \n" +
            "    CASE WHEN P.id_filial IS NOT NULL THEN F.nome_fantasia_filial ELSE U.nome_fantasia_unidade END AS \"nome\", \n" +
            "    CASE WHEN P.id_filial IS NOT NULL THEN F.razao_social_filial ELSE U.razao_social_unidade END AS \"razaoSocial\", \n" +
            "    CASE WHEN P.id_filial IS NOT NULL THEN F.documento_filial ELSE U.documento_unidade END AS \"documento\", \n" +
            "    CASE WHEN P.id_filial IS NOT NULL THEN F.telefone_filial ELSE U.telefone_unidade END AS \"telefone\", \n" +
            "    --TO_CHAR(USU.data_cadastro_usuario, 'DD/MM/YYYY HH24:MI:SS') AS \"dataCadastro\", \n" +
            "    USU.data_cadastro_usuario AS \"dataCadastro\", \n" +
            "    USU.usuario AS \"email\", \n" +
            "    P.local_tipo AS \"local\" \n" +
            "FROM \n" +
            "    estudos.PESSOA P \n" +
            "INNER JOIN \n" +
            "    estudos.USUARIO USU ON USU.id_usuario = P.id_pessoa \n" +
            "LEFT JOIN \n" +
            "    estudos.FILIAL F ON P.ID_FILIAL = F.ID_FILIAL \n" +
            "LEFT JOIN \n" +
            "    estudos.UNIDADE U ON P.ID_UNIDADE = U.ID_UNIDADE \n" +
            "ORDER BY \n" +
            "    \"razaoSocial\";\n",
            nativeQuery = true)
    Page<UnidadeFilialPessoaLocalDTO> listarUnidadeFilialPessoa(Pageable pageable);


    // ---idPessoa:null | idConvenio:null | unidadeRepresentativa:null
    @Query(value = "SELECT \n" +
            "    --P.id_pessoa AS \"idPessoa\", \n" +
            "    --P.id_pessoa_unidade_principal AS \"unidadeRepresentativa\", \n" +
            "    CASE WHEN P.id_filial IS NOT NULL THEN F.nome_fantasia_filial ELSE U.nome_fantasia_unidade END AS \"nome\", \n" +
            "    CASE WHEN P.id_filial IS NOT NULL THEN F.razao_social_filial ELSE U.razao_social_unidade END AS \"razaoSocial\", \n" +
            "    CASE WHEN P.id_filial IS NOT NULL THEN F.documento_filial ELSE U.documento_unidade END AS \"documento\", \n" +
            "    CASE WHEN P.id_filial IS NOT NULL THEN F.telefone_filial ELSE U.telefone_unidade END AS \"telefone\", \n" +
            "    --TO_CHAR(USU.data_cadastro_usuario, 'DD/MM/YYYY HH24:MI:SS') AS \"dataCadastro\", \n" +
            "    USU.data_cadastro_usuario AS \"dataCadastro\", \n" +
            "    USU.usuario AS \"email\", \n" +
            "    P.local_tipo AS \"local\" \n" +
            "FROM \n" +
            "    estudos.PESSOA P \n" +
            "INNER JOIN \n" +
            "    estudos.USUARIO USU ON USU.id_usuario = P.id_pessoa \n" +
            "LEFT JOIN \n" +
            "    estudos.FILIAL F ON P.ID_FILIAL = F.ID_FILIAL \n" +
            "LEFT JOIN \n" +
            "    estudos.UNIDADE U ON P.ID_UNIDADE = U.ID_UNIDADE \n" +
            "WHERE \n" +
            "    P.id_pessoa = :idPessoa \n" +
            "    OR P.id_pessoa_unidade_principal = :idPessoa \n" +
            "ORDER BY \n" +
            "    \"razaoSocial\";\n",
            nativeQuery = true)
    Page<UnidadeFilialPessoaLocalDTO> listarUnidadeFilialPessoaLocal(@Param("idPessoa") String idPessoa, Pageable pageable);

    @Query(value =
            "SELECT \n" +
                    "--  P.id_pessoa AS \"idPessoa\", \n" +
                    "--  P.id_pessoa_unidade_principal AS \"unidadeRepresentativa\", \n" +
                    "PC.id_convenio  AS \"idConvenio\", \n" +
                    "C.nome_fantasia_convenio AS \"nome\", \n" +
                    "C.razao_social_convenio AS \"razaoSocial\", \n" +
                    "C.cnpj_convenio AS \"documento\", \n" +
                    "C.telefone_convenio AS \"telefone\", \n" +
                    "--TO_CHAR(C.data_cadastro_convenio, 'DD/MM/YYYY HH24:MI:SS') AS \"dataCadastro\", \n" +
                    "USU.data_cadastro_usuario AS \"dataCadastro\", \n" +
                    "C.email_convenio AS \"email\", \n" +
                    "P.local_tipo AS \"local\" \n" +
                    "FROM \n" +
                    "estudos.PESSOA P \n" +
                    "INNER JOIN \n" +
                    "estudos.USUARIO USU ON USU.id_usuario = P.id_pessoa \n" +
                    "INNER JOIN \n" +
                    "estudos.pessoa_uniao_convenio PC ON PC.id_pessoa = P.id_pessoa \n" +
                    "INNER JOIN \n" +
                    "estudos.convenio C ON C.id_convenio = PC.id_convenio \n" +
                    "LEFT JOIN \n" +
                    "estudos.FILIAL F ON P.ID_FILIAL = F.ID_FILIAL \n" +
                    "LEFT JOIN \n" +
                    "estudos.UNIDADE U ON P.ID_UNIDADE = U.ID_UNIDADE \n" +
                    "WHERE \n" +
                    "    P.id_pessoa = :idPessoa \n" +
                    "    OR P.id_pessoa_unidade_principal = :idPessoa \n" +
                    "ORDER BY \n" +
                    "    \"nome\";\n",
            nativeQuery = true)
    Page<UnidadeFilialPessoaLocalDTO> listarConvenioUnidadeFilialPessoaLocal(@Param("idPessoa") String idPessoa, Pageable pageable);

    //@Query(value = "SELECT "
    //  P.id_pessoa 												AS "ID do Usuário",
    //  COALESCE(F.nome_fantasia_filial,U.nome_fantasia_unidade)    AS "Nome",
    //	COALESCE(F.razao_social_filial,U.razao_social_unidade)  	AS "Razão Social",
    //  COALESCE(F.documento_filial,U.documento_unidade) 			AS "CNPJ/CPF",
    //	COALESCE(F.telefone_filial,U.telefone_unidade)  			AS "Telefone",
    //	USU.usuario													AS "Email",
    //  --S.NOME_SERVICO 												AS "Nome do Serviço",
    //	USU.data_cadastro_usuario							 	    AS "Cadastro",
    //	COALESCE(F.email_filial,U.email_unidade) 					AS "Email Local",
    //  P.local_tipo 												AS "Local"
    //FROM
    //    estudos.PESSOA P
    //INNER JOIN
    //	estudos.USUARIO USU ON USU.id_usuario = P.id_pessoa
    //LEFT JOIN
    //    estudos.FILIAL F  ON P.ID_FILIAL = F.ID_FILIAL
    //LEFT JOIN
    //    estudos.UNIDADE U ON P.ID_UNIDADE = U.ID_UNIDADE
    //--INNER JOIN
    //  --  estudos.PESSOA_UNIAO_SERVICO PU ON P.id_pessoa = PU.ID_PESSOA
    //--INNER JOIN
    //    --estudos.SERVICO S ON PU.ID_SERVICO = S.ID_SERVICO
    //WHERE
    //    --S.NOME_SERVICO = 'ESPIROMETRIA'
    //    --AND
    //	(P.id_pessoa = 2 or P.id_unidade_principal = 2)--(SELECT  id_unidade_principal FROM estudos.pessoa WHERE id_pessoa = 3))
    //	--and
    //	--USU.
    //"ORDER BY F.razao_social_filial, U.razao_social_unidade;",nativeQuery = true)"
    //List<Object[]> listarUnidadeFilialPessoaLocal(@Param("idUsuario") Long idUsuario);
    ///---Encerrar Filial e Unidade Local---////
}