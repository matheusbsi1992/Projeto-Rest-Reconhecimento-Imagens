package br.com.projeto.estudos.mapeamento;


import br.com.projeto.estudos.dto.FilialDTO;

import br.com.projeto.estudos.dto.UnidadeDTO;
import br.com.projeto.estudos.modelo.Filial;

import br.com.projeto.estudos.modelo.Unidade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FilialMapeada {

    @Mappings({
            @Mapping(source = "id_filial", target = "idFilial"),
            @Mapping(source = "endereco.id_endereco", target = "endereco.idEndereco"),
            @Mapping(source = "endereco.bairro_endereco", target = "endereco.bairroEndereco"),
            @Mapping(source = "endereco.cep_endereco", target = "endereco.cepEndereco"),
            @Mapping(source = "endereco.cidade_endereco", target = "endereco.cidadeEndereco"),
            @Mapping(source = "endereco.complemento_endereco", target = "endereco.complementoEndereco"),
            @Mapping(source = "endereco.estado_endereco", target = "endereco.estadoEndereco"),
            @Mapping(source = "endereco.logradouro_endereco", target = "endereco.logradouroEndereco"),
            @Mapping(source = "endereco.numero_endereco", target = "endereco.numeroEndereco"),
            @Mapping(source = "documento_filial", target = "documentoFilial"),
            @Mapping(source = "email_filial", target = "emailFilial"),
            @Mapping(source = "nome_fantasia_filial", target = "nomeFantasiaFilial"),
            @Mapping(source = "setor_filial", target = "setorFilial"),
            @Mapping(source = "razao_social_filial", target = "razaoSocialFilial"),
            @Mapping(source = "telefone_filial", target = "telefoneFilial"),
            @Mapping(source = "status_filial", target = "statusFilial"),
            @Mapping(source = "unidade", target = "unidadeList", qualifiedByName = "mapSingleUnidadeToListUnidadeDTO")
    })
    FilialDTO filialParafilialDTO(Filial filial);

    @Mappings({
            @Mapping(source = "idFilial", target = "id_filial"),
            @Mapping(source = "endereco.idEndereco", target = "endereco.id_endereco"),
            @Mapping(source = "endereco.bairroEndereco", target = "endereco.bairro_endereco"),
            @Mapping(source = "endereco.cepEndereco", target = "endereco.cep_endereco"),
            @Mapping(source = "endereco.cidadeEndereco", target = "endereco.cidade_endereco"),
            @Mapping(source = "endereco.complementoEndereco", target = "endereco.complemento_endereco"),
            @Mapping(source = "endereco.estadoEndereco", target = "endereco.estado_endereco"),
            @Mapping(source = "endereco.logradouroEndereco", target = "endereco.logradouro_endereco"),
            @Mapping(source = "endereco.numeroEndereco", target = "endereco.numero_endereco"),
            @Mapping(source = "documentoFilial", target = "documento_filial"),
            @Mapping(source = "emailFilial", target = "email_filial"),
            @Mapping(source = "nomeFantasiaFilial", target = "nome_fantasia_filial"),
            @Mapping(source = "setorFilial", target = "setor_filial"),
            @Mapping(source = "razaoSocialFilial", target = "razao_social_filial"),
            @Mapping(source = "telefoneFilial", target = "telefone_filial"),
            @Mapping(source = "statusFilial", target = "status_filial"),
            @Mapping(source = "unidadeList", target = "unidade", qualifiedByName = "mapListUnidadeDTOToSingleUnidade")
    })
    Filial filialDTOParafilial(FilialDTO filialDTO);

    @Mappings({
            @Mapping(source = "id_unidade", target = "idUnidade"),
            @Mapping(source = "documento_unidade", target = "documentoUnidade"),
            @Mapping(source = "email_unidade", target = "emailUnidade"),
            @Mapping(source = "nome_fantasia_unidade", target = "nomeFantasiaUnidade"),
            @Mapping(source = "setor_unidade", target = "setorUnidade"),
            @Mapping(source = "razao_social_unidade", target = "razaoSocialUnidade"),
            @Mapping(source = "telefone_unidade", target = "telefoneUnidade"),
            @Mapping(source = "status_unidade", target = "statusUnidade")
    })
    UnidadeDTO mapUnidadeToUnidadeDTO(Unidade unidade);

    @Mappings({
            @Mapping(source = "idUnidade", target = "id_unidade"),
            @Mapping(source = "documentoUnidade", target = "documento_unidade"),
            @Mapping(source = "emailUnidade", target = "email_unidade"),
            @Mapping(source = "nomeFantasiaUnidade", target = "nome_fantasia_unidade"),
            @Mapping(source = "setorUnidade", target = "setor_unidade"),
            @Mapping(source = "razaoSocialUnidade", target = "razao_social_unidade"),
            @Mapping(source = "telefoneUnidade", target = "telefone_unidade"),
            @Mapping(source = "statusUnidade", target = "status_unidade")
    })
    Unidade mapUnidadeDTOToUnidade(UnidadeDTO unidadeDTO);

    // Converte uma única Unidade para uma lista de UnidadeDTO com um único elemento
    @Named("mapSingleUnidadeToListUnidadeDTO")
    default List<UnidadeDTO> mapSingleUnidadeToListUnidadeDTO(Unidade unidade) {
        if (unidade == null) {
            return null;
        }
        return List.of(mapUnidadeToUnidadeDTO(unidade));
    }

    // Converte uma lista de UnidadeDTO para uma única Unidade (pega o primeiro item da lista)
    @Named("mapListUnidadeDTOToSingleUnidade")
    default Unidade mapListUnidadeDTOToSingleUnidade(List<UnidadeDTO> unidadeDTOs) {
        if (unidadeDTOs == null || unidadeDTOs.isEmpty()) {
            return null;
        }
        return mapUnidadeDTOToUnidade(unidadeDTOs.get(0));
    }

    /*//Metodo para mapear Unidade para UnidadeDTO
    @Mapping(source = "id_unidade", target = "idUnidade")//,conditionQualifiedByName = "mapUnidadeToUnidadeDTO")
    //@Named("mapUnidadeToUnidadeDTO")
    UnidadeDTO mapUnidadeToUnidadeDTO(Unidade unidade);

    //Metodo para mapear UnidadeDTO para Unidade
    @Mapping(source = "idUnidade", target = "id_unidade")
    //@Named("mapUnidadeDTOToUnidade")
    Unidade mapUnidadeDTOToUnidade(UnidadeDTO unidadeDTO);

    //Metodo para mapear lista de Unidade para lista de UnidadeDTO
    @Named("mapUnidadeListToUnidadeDTOList")
    default List<UnidadeDTO> mapUnidadeListToUnidadeDTOList(List<Unidade> unidades) {
        if (unidades == null) {
            return null;
        }
        return unidades.stream()
                .map(this::mapUnidadeToUnidadeDTO)
                .collect(Collectors.toList());
    }

    //Metodo para mapear lista de UnidadeDTO para lista de Unidade
    @Named("mapUnidadeDTOListToUnidadeList")
    default List<Unidade> mapUnidadeDTOListToUnidadeList(List<UnidadeDTO> unidadeDTOs) {
        if (unidadeDTOs == null) {
            return null;
        }
        return unidadeDTOs.stream()
                .map(this::mapUnidadeDTOToUnidade)
                .collect(Collectors.toList());
    }*/

}