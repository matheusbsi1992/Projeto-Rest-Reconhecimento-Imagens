package br.com.projeto.estudos.mapeamento;

import br.com.projeto.estudos.dto.*;
import br.com.projeto.estudos.modelo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PessoaMapeada {

    //PessoaMapeada INSTANCE = Mappers.getMapper(PessoaMapeada.class);

    // Mapeamento para PessoaDTO sem obrigatoriedade de Convenios
    @Mappings({
            @Mapping(source = "usuario", target = "usuario"),
            @Mapping(source = "unidade", target = "unidade", qualifiedByName = "mapUnidadeListToUnidadeDTOList"),
            @Mapping(source = "filial", target = "filial", qualifiedByName = "mapFilialListToFilialDTOList"),
            @Mapping(source = "servico", target = "servicoDTOList", qualifiedByName = "mapServicoListToServicoDTOList"),
            @Mapping(source = "local_tipo", target = "localTipo")//,
           // @Mapping(source = "usuario", target = "usuarioDTOList",qualifiedByName = "mapSingleUsuarioToListUsuarioDTO")
            //@Mapping(source = "convenios", target = "convenioDTOList", ignore = true) // Ignorar convenios como não obrigatório
    })
    PessoaDTO pessoaParaPessoaDTO(Pessoa pessoa);

    // Mapeamento inverso
    @Mappings({
            @Mapping(source = "usuario", target = "usuario"),
            @Mapping(source = "unidade", target = "unidade", qualifiedByName = "mapUnidadeDTOListToUnidadeList"),
            @Mapping(source = "filial", target = "filial", qualifiedByName = "mapFilialDTOListToFilialList"),
            @Mapping(source = "servicoDTOList", target = "servico", qualifiedByName = "mapServicoDTOListToServicoList"),
            @Mapping(source = "localTipo", target = "local_tipo")//,
            //@Mapping(source = "usuarioDTOList", target = "usuario",qualifiedByName = "mapListUsuarioDTOToSingleUsuario")
            //@Mapping(source = "convenioDTOList", target = "convenios", ignore = true) // Ignorar convenios na inserção
    })
    Pessoa pessoaDTOParaPessoa(PessoaDTO pessoaDTO);


    //------------Relacao com a Unidade------------//

    //Método para mapear Unidade para UnidadeDTO
    @Mapping(source = "id_unidade", target = "idUnidade")//,conditionQualifiedByName = "mapUnidadeToUnidadeDTO")
    @Named("mapUnidadeToUnidadeDTO")
    UnidadeDTO mapUnidadeToUnidadeDTO(Unidade unidade);

    //Método para mapear UnidadeDTO para Unidade
    @Mapping(source = "idUnidade", target = "id_unidade")
    @Named(value = "mapUnidadeDTOToUnidade")
    Unidade mapUnidadeDTOToUnidade(UnidadeDTO unidadeDTO);

    //Método para mapear lista de Unidade para lista de UnidadeDTO
    @Named(value = "mapUnidadeListToUnidadeDTOList")
    default List<UnidadeDTO> mapUnidadeListToUnidadeDTOList(List<Unidade> unidades) {
        if (unidades == null) {
            return null;
        }
        return unidades.stream()
                .map(this::mapUnidadeToUnidadeDTO)
                .collect(Collectors.toList());
    }

    //Método para mapear lista de UnidadeDTO para lista de Unidade
    @Named("mapUnidadeDTOListToUnidadeList")
    default List<Unidade> mapUnidadeDTOListToUnidadeList(List<UnidadeDTO> unidadeDTOs) {
        if (unidadeDTOs == null) {
            return null;
        }
        return unidadeDTOs.stream()
                .map(this::mapUnidadeDTOToUnidade)
                .collect(Collectors.toList());
    }

    //------------Finaliza a Relacao com a Unidade------------//


    //------------Relacao com a FILIAL------------//

    //Método para mapear Filial para FilialDTO
    @Mapping(source = "id_filial", target = "idFilial")//,conditionQualifiedByName = "mapUnidadeToUnidadeDTO")
    @Named("mapFilialToFilialDTO")
    FilialDTO mapFilialToFilialDTO(Filial filial);

    //Método para mapear FilialDTO para Filial
    @Mapping(source = "idFilial", target = "id_filial")
    @Named(value = "mapFilialDTOToFilial")
    Filial mapFilialDTOToFilial(FilialDTO filialDTO);

    //Método para mapear lista de Filial para lista de FilialDTO
    @Named(value = "mapFilialListToFilialDTOList")
    default List<FilialDTO> mapFilialListToFilialDTOList(List<Filial> filials) {
        if (filials == null) {
            return null;
        }
        return filials.stream()
                .map(this::mapFilialToFilialDTO)
                .collect(Collectors.toList());
    }

    //Método para mapear lista de FilialDTO para lista de Filial
    @Named("mapFilialDTOListToFilialList")
    default List<Filial> mapFilialDTOListToFilialList(List<FilialDTO> filialDTOs) {
        if (filialDTOs == null) {
            return null;
        }
        return filialDTOs.stream()
                .map(this::mapFilialDTOToFilial)
                .collect(Collectors.toList());
    }
    //------------Finaliza a Relacao com a Filial------------//

    //------------Relacao com o SERVICO------------//
    //Método para mapear Servico para ServicoDTO
    @Mapping(source = "id_servico", target = "idServico")//,conditionQualifiedByName = "mapUnidadeToUnidadeDTO")
    @Named("mapServicoToServicoDTO")
    ServicoDTO mapServicoToServicoDTO(Servico servico);

    //Método para mapear ServicoDTO para Servico
    @Mapping(source = "idServico", target = "id_servico")
    @Named(value = "mapServicoDTOToServico")
    Servico mapServicoDTOToServico(ServicoDTO servicoDTO);

    //Método para mapear lista de Servico para lista de ServicoDTO
    @Named(value = "mapServicoListToServicoDTOList")
    default List<ServicoDTO> mapServicoListToServicoDTOList(List<Servico> servicos) {
        if (servicos == null) {
            return null;
        }
        return servicos.stream()
                .map(this::mapServicoToServicoDTO)
                .collect(Collectors.toList());
    }

    //Método para mapear lista de ServicoDTO para lista de Servico
    @Named("mapServicoDTOListToServicoList")
    default List<Servico> mapServicoDTOListToServicoList(List<ServicoDTO> servicoDTOs) {
        if (servicoDTOs == null) {
            return null;
        }
        return servicoDTOs.stream()
                .map(this::mapServicoDTOToServico)
                .collect(Collectors.toList());
    }
    //------------Finaliza a Relacao com o SERVICO------------//

    //------------Relacao com o USUARIO-----------------------//
    @Mappings({
            @Mapping(source = "id_usuario", target = "idUsuario"),
            @Mapping(source = "nome_usuario", target = "nomeUsuario")//,
           // @Mapping(source = "usuario", target = "usuario")
    })
    UsuarioDTO mapUsuarioToUsuarioDTO(Usuario usuario);

    @Mappings({
            @Mapping(source = "idUsuario", target = "id_usuario"),
            @Mapping(source = "nomeUsuario", target = "nome_usuario")//,
            //@Mapping(source = "usuario", target = "usuario")
    })
    Usuario mapUsuarioDTOToUsuario(UsuarioDTO usuarioDTO);

    // Converte uma única Usuario para uma lista de UsuarioDTO com um único elemento
    @Named("mapSingleUsuarioToListUsuarioDTO")
    default List<UsuarioDTO> mapSingleUnidadeToListUnidadeDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return List.of(mapUsuarioToUsuarioDTO(usuario));
    }

    // Converte uma lista de UsuarioDTO para uma única Usuario (pega o primeiro item da lista)
    @Named("mapListUsuarioDTOToSingleUsuario")
    default Usuario mapListUsuarioDTOToSingleUsuario(List<UsuarioDTO> usuarioDTOs) {
        if (usuarioDTOs == null || usuarioDTOs.isEmpty()) {
            return null;
        }
        return mapUsuarioDTOToUsuario(usuarioDTOs.get(0));
    }
    //------------Finaliza a Relacao com o USUARIO------------//
}