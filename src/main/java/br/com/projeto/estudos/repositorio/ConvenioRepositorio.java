package br.com.projeto.estudos.repositorio;

import br.com.projeto.estudos.modelo.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ConvenioRepositorio extends JpaRepository<Convenio,String> {

    /*@Query("SELECT c FROM convenio c WHERE ")
    List<Convenio> retornarConvenios(@Param("id_unidade")Long id_unidade);*/

    /*@Query("SELECT c FROM convenio c WHERE c.id_unidade = :id_unidade ")
    List<Convenio> listarConvenios(@Param("id_unidade") Long id_unidade);*/

}
