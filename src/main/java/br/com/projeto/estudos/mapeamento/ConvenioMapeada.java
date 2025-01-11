package br.com.projeto.estudos.mapeamento;

import br.com.projeto.estudos.dto.ConvenioDTO;
import br.com.projeto.estudos.modelo.Convenio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ConvenioMapeada {

    @Mappings({
            @Mapping(source = "id_convenio", target = "idConvenio"),
            @Mapping(source = "endereco.id_endereco", target = "endereco.idEndereco"),
            @Mapping(source = "endereco.bairro_endereco", target = "endereco.bairroEndereco"),
            @Mapping(source = "endereco.cep_endereco", target = "endereco.cepEndereco"),
            @Mapping(source = "endereco.cidade_endereco", target = "endereco.cidadeEndereco"),
            @Mapping(source = "endereco.complemento_endereco", target = "endereco.complementoEndereco"),
            @Mapping(source = "endereco.estado_endereco", target = "endereco.estadoEndereco"),
            @Mapping(source = "endereco.logradouro_endereco", target = "endereco.logradouroEndereco"),
            @Mapping(source = "endereco.numero_endereco", target = "endereco.numeroEndereco"),
            @Mapping(source = "nome_fantasia_convenio", target = "nomeFantasiaConvenio"),
            @Mapping(source = "razao_social_convenio", target = "razaoSocialConvenio"),
            @Mapping(source = "cnpj_convenio", target = "cnpjConvenio"),
            @Mapping(source = "email_convenio", target = "emailConvenio"),
            @Mapping(source = "telefone_convenio", target = "telefoneConvenio"),
            @Mapping(source = "setor_convenio", target = "setorConvenio")
    })
    ConvenioDTO convenioParaConvenioDTO(Convenio convenio);

    @Mappings({
            @Mapping(source = "idConvenio", target = "id_convenio"),
            @Mapping(source = "endereco.idEndereco", target = "endereco.id_endereco"),
            @Mapping(source = "endereco.bairroEndereco", target = "endereco.bairro_endereco"),
            @Mapping(source = "endereco.cepEndereco", target = "endereco.cep_endereco"),
            @Mapping(source = "endereco.cidadeEndereco", target = "endereco.cidade_endereco"),
            @Mapping(source = "endereco.complementoEndereco", target = "endereco.complemento_endereco"),
            @Mapping(source = "endereco.estadoEndereco", target = "endereco.estado_endereco"),
            @Mapping(source = "endereco.logradouroEndereco", target = "endereco.logradouro_endereco"),
            @Mapping(source = "endereco.numeroEndereco", target = "endereco.numero_endereco"),
            @Mapping(source = "nomeFantasiaConvenio", target = "nome_fantasia_convenio"),
            @Mapping(source = "razaoSocialConvenio", target = "razao_social_convenio"),
            @Mapping(source = "cnpjConvenio", target = "cnpj_convenio"),
            @Mapping(source = "emailConvenio", target = "email_convenio"),
            @Mapping(source = "telefoneConvenio", target = "telefone_convenio"),
            @Mapping(source = "setorConvenio", target = "setor_convenio")
    })
    Convenio convenioDTOParaConvenio(ConvenioDTO convenioDTO);

    /*// Mapeamento para Unidade e UnidadeDTO (separado)
    @Mappings({
            @Mapping(source = "id_unidade", target = "idUnidade"),
            @Mapping(source = "endereco.id_endereco", target = "endereco.idEndereco"),
            @Mapping(source = "documento_unidade", target = "documentoUnidade"),
            @Mapping(source = "email_unidade", target = "emailUnidade"),
            @Mapping(source = "nome_fantasia_unidade", target = "nomeFantasiaUnidade"),
            @Mapping(source = "setor_unidade", target = "setorUnidade"),
            @Mapping(source = "razao_social_unidade", target = "razaoSocialUnidade"),
            @Mapping(source = "telefone_unidade", target = "telefoneUnidade"),
            @Mapping(source = "status_unidade", target = "statusUnidade")
    })
    UnidadeDTO unidadeParaUnidadeDTO(Unidade unidade);

    @Mappings({
            @Mapping(source = "idUnidade", target = "id_unidade"),
            @Mapping(source = "endereco.idEndereco", target = "endereco.id_endereco"),
            @Mapping(source = "documentoUnidade", target = "documento_unidade"),
            @Mapping(source = "emailUnidade", target = "email_unidade"),
            @Mapping(source = "nomeFantasiaUnidade", target = "nome_fantasia_unidade"),
            @Mapping(source = "setorUnidade", target = "setor_unidade"),
            @Mapping(source = "razaoSocialUnidade", target = "razao_social_unidade"),
            @Mapping(source = "telefoneUnidade", target = "telefone_unidade"),
            @Mapping(source = "statusUnidade", target = "status_unidade")
    })
    Unidade unidadeDTOParaUnidade(UnidadeDTO unidadeDTO);*/
}