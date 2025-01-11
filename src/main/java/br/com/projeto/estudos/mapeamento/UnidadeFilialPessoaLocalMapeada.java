package br.com.projeto.estudos.mapeamento;

import br.com.projeto.estudos.dto.UnidadeFilialPessoaLocalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UnidadeFilialPessoaLocalMapeada {

    @Mapping(target = "idPessoa", source = "idPessoa")
    @Mapping(target = "idConvenio", source = "idConvenio")
    @Mapping(target = "unidadeRepresentativa", source = "unidadeRepresentativa")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    @Mapping(target = "documento", source = "documento")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "dataCadastro", source = "dataCadastro")
    @Mapping(target = "local", source = "local")
    UnidadeFilialPessoaLocalDTO toDTO(UnidadeFilialPessoaLocalDTO projection);
}
