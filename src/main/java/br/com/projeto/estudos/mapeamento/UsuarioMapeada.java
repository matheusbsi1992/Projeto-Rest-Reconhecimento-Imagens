package br.com.projeto.estudos.mapeamento;

import br.com.projeto.estudos.dto.PermissaoDTO;
import br.com.projeto.estudos.dto.UsuarioDTO;
import br.com.projeto.estudos.modelo.Permissao;
import br.com.projeto.estudos.modelo.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UsuarioMapeada {

    @Mappings({
            @Mapping(source = "id_usuario", target = "idUsuario"),
            //@Mapping(source = "unidade.id_unidade", target = "unidade.idUnidade"),
            //@Mapping(source = "unidade.endereco.id_endereco", target = "unidade.endereco.idEndereco"),
            /*@Mapping(source = "unidade.documento_unidade", target = "unidade.documentoUnidade"),
            @Mapping(source = "unidade.email_unidade", target = "unidade.emailUnidade"),
            @Mapping(source = "unidade.nome_fantasia_unidade", target = "unidade.nomeFantasiaUnidade"),
            @Mapping(source = "unidade.setor_unidade", target = "unidade.setorUnidade"),
            @Mapping(source = "unidade.razao_social_unidade", target = "unidade.razaoSocialUnidade"),
            @Mapping(source = "unidade.telefone_unidade", target = "unidade.telefoneUnidade"),
            @Mapping(source = "unidade.status_unidade", target = "unidade.statusUnidade"),
            */
            /*@Mapping(source = "endereco.id_endereco", target = "endereco.idEndereco"),
            @Mapping(source = "endereco.bairro_endereco", target = "endereco.bairroEndereco"),
            @Mapping(source = "endereco.cep_endereco", target = "endereco.cepEndereco"),
            @Mapping(source = "endereco.cidade_endereco", target = "endereco.cidadeEndereco"),
            @Mapping(source = "endereco.complemento_endereco", target = "endereco.complementoEndereco"),
            @Mapping(source = "endereco.estado_endereco", target = "endereco.estadoEndereco"),
            @Mapping(source = "endereco.logradouro_endereco", target = "endereco.logradouroEndereco"),
            @Mapping(source = "endereco.numero_endereco", target = "endereco.numeroEndereco"),
            */@Mapping(source = "nome_usuario", target = "nomeUsuario"),
            @Mapping(source = "usuario", target = "usuario"),
            @Mapping(source = "senha_usuario", target = "senhaUsuario"),
            @Mapping(source = "fone_usuario", target = "foneUsuario"),
            // Credenciais de Usuário
            @Mapping(source = "account_non_expired_usuario", target = "accountNonExpiredUsuario"),
            @Mapping(source = "account_non_locked_usuario", target = "accountNonLockedUsuario"),
            @Mapping(source = "credentials_non_expired_usuario", target = "credentialsNonExpiredUsuario"),

            @Mapping(source = "status_usuario", target = "statusUsuario"),
            @Mapping(source = "permissaoList", target = "permissaoDTOList", qualifiedByName = "mapPermissaoListToPermissaoDTOList")
    })
    UsuarioDTO usuarioParaUsuarioDTO(Usuario usuario);

    @Mappings({
            @Mapping(source = "idUsuario", target = "id_usuario"),
            //@Mapping(source = "unidade.idUnidade", target = "unidade.id_unidade"),
            //@Mapping(source = "unidade.endereco.idEndereco", target = "unidade.endereco.id_endereco"),
            /*@Mapping(source = "endereco.idEndereco", target = "endereco.id_endereco"),
            @Mapping(source = "endereco.bairroEndereco", target = "endereco.bairro_endereco"),
            @Mapping(source = "endereco.cepEndereco", target = "endereco.cep_endereco"),
            @Mapping(source = "endereco.cidadeEndereco", target = "endereco.cidade_endereco"),
            @Mapping(source = "endereco.complementoEndereco", target = "endereco.complemento_endereco"),
            @Mapping(source = "endereco.estadoEndereco", target = "endereco.estado_endereco"),
            @Mapping(source = "endereco.logradouroEndereco", target = "endereco.logradouro_endereco"),
            @Mapping(source = "endereco.numeroEndereco", target = "endereco.numero_endereco"),
            *//*@Mapping(source = "unidade.documentoUnidade", target = "unidade.documento_unidade"),
            @Mapping(source = "unidade.emailUnidade", target = "unidade.email_unidade"),
            @Mapping(source = "unidade.nomeFantasiaUnidade", target = "unidade.nome_fantasia_unidade"),
            @Mapping(source = "unidade.setorUnidade", target = "unidade.setor_unidade"),
            @Mapping(source = "unidade.razaoSocialUnidade", target = "unidade.razao_social_unidade"),
            @Mapping(source = "unidade.telefoneUnidade", target = "unidade.telefone_unidade"),
            @Mapping(source = "unidade.statusUnidade", target = "unidade.status_unidade"),
            */@Mapping(source = "nomeUsuario", target = "nome_usuario"),
            @Mapping(source = "usuario", target = "usuario"),
            @Mapping(source = "senhaUsuario", target = "senha_usuario"),
            @Mapping(source = "foneUsuario", target = "fone_usuario"),
            // Credenciais de usuario
            @Mapping(source = "accountNonExpiredUsuario", target = "account_non_expired_usuario"),
            @Mapping(source = "accountNonLockedUsuario", target = "account_non_locked_usuario"),
            @Mapping(source = "credentialsNonExpiredUsuario", target = "credentials_non_expired_usuario"),

            @Mapping(source = "statusUsuario", target = "status_usuario"),
            @Mapping(source = "permissaoDTOList", target = "permissaoList", qualifiedByName = "mapPermissaoDTOListToPermissaoList")
    })
    Usuario usuarioDTOParaUsuario(UsuarioDTO usuarioDTO);

    //Método para mapear lista de Unidade para lista de UnidadeDTO
    @Named(value = "mapPermissaoListToPermissaoDTOList")
    default List<PermissaoDTO> mapPermissaoListToPermissaoDTOList(List<Permissao> permissaoList) {
        if (permissaoList == null) {
            return null;
        }
        return permissaoList.stream()
                .map(this::mapPermissaoToPermissaoDTO)
                .collect(Collectors.toList());
    }

    //Método para mapear lista de UnidadeDTO para lista de Unidade
    @Named("mapPermissaoDTOListToPermissaoList")
    default List<Permissao> mapPermissaoDTOListToPermissaoList(List<PermissaoDTO> permissaoDTOS) {
        if (permissaoDTOS == null) {
            return null;
        }
        return permissaoDTOS.stream()
                .map(this::mapPermissaoDTOToPermissao)
                .collect(Collectors.toList());
    }

    @Mapping(source = "id_permissao", target = "idPermissao")//,conditionQualifiedByName = "mapUnidadeToUnidadeDTO")
    @Named("mapPermissaoToPermissaoDTO")
    PermissaoDTO mapPermissaoToPermissaoDTO(Permissao permissao);

    //Método para mapear UnidadeDTO para Unidade
    @Mapping(source = "idPermissao", target = "id_permissao")
    @Named(value = "mapPermissaoDTOToPermissao")
    Permissao mapPermissaoDTOToPermissao(PermissaoDTO permissaoDTO);

    /* //Metodo auxiliar para mapear uma lista de Permissao para uma lista de Strings (ex: nome da permissão)
    @Named("mapPermissaoToIdPermissao")
    default List<Long> mapPermissaoToIdPermissao(List<Permissao> permissaoList) {
        if (permissaoList == null) {
            return null;
        }
        return permissaoList.stream()
                .map(Permissao::getId_permissao)  // Aqui você pode definir o atributo da Permissao que deseja usar como String
                .collect(Collectors.toList());
    }

    @Named("mapIdPermissaoToPermissao")
    default List<Permissao> mapIdPermissaoToPermissao(List<Long> permissaoList) {
        if (permissaoList == null) {
            return null;
        }
        return permissaoList.stream()
                .map(Permissao::new)  // Aqui você cria o objeto Permissao a partir do nome
                .collect(Collectors.toList());
    }*/

}