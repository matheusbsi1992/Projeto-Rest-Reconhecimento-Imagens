package br.com.projeto.estudos.mapeamento;

import br.com.projeto.estudos.dto.PacienteDTO;
import br.com.projeto.estudos.modelo.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PacienteMapeada {

    @Mappings({
            //@Mapping(source = "id_usuario", target = "idUsuario"),
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
            */
            @Mapping(source = "usuario.nome_usuario", target = "usuario.nomeUsuario"),
            @Mapping(source = "usuario.fone_usuario", target = "usuario.foneUsuario"),
            //@Mapping(source = "usuario", target = "usuario"),
            //@Mapping(source = "nome_paciente", target = "nomePaciente"),
            @Mapping(source = "cpf_paciente", target = "cpfPaciente"),
            @Mapping(source = "peso_paciente", target = "pesoPaciente"),
            @Mapping(source = "cargo_paciente", target = "cargoPaciente"),
            @Mapping(source = "profissao_paciente", target = "profissaoPaciente"),
            @Mapping(source = "setor_paciente", target = "setorPaciente"),
            @Mapping(source = "numero_cartao_sus_paciente", target = "numeroCartaoSusPaciente"),
            @Mapping(source = "sexo_paciente", target = "sexoPaciente"),
            @Mapping(source = "rg_paciente", target = "rgPaciente"),
            @Mapping(source = "altura_paciente", target = "alturaPaciente"),
            @Mapping(source = "data_nascimento_paciente", target = "dataNascimentoPaciente"),
            @Mapping(source = "nome_mae_paciente", target = "nomeMaePaciente")
            /*@Mapping(source = "account_non_expired_usuario", target = "accountNonExpiredUsuario"),
            @Mapping(source = "account_non_locked_usuario", target = "accountNonLockedUsuario"),
            @Mapping(source = "credentials_non_expired_usuario", target = "credentialsNonExpiredUsuario"),
            */
            //@Mapping(source = "usuario.status_usuario", target = "statusUsuario"),
            //@Mapping(source = "permissaoList",target = "permissaoDTOList", qualifiedByName = "mapPermissaoListToPermissaoDTOList")
    })
    PacienteDTO pacienteParaPacienteDTO(Paciente paciente);

    @Mappings({
            //@Mapping(source = "id_usuario", target = "idUsuario"),
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
            */
            @Mapping(source = "usuario.nomeUsuario", target = "usuario.nome_usuario"),
            @Mapping(source = "usuario.foneUsuario", target = "usuario.fone_usuario"),
            //@Mapping(source = "usuario", target = "usuario"),
            //@Mapping(source = "nomePaciente", target = "nome_paciente"),
            @Mapping(source = "cpfPaciente", target = "cpf_paciente"),
            @Mapping(source = "pesoPaciente", target = "peso_paciente"),
            @Mapping(source = "cargoPaciente", target = "cargo_paciente"),
            @Mapping(source = "profissaoPaciente", target = "profissao_paciente"),
            @Mapping(source = "setorPaciente", target = "setor_paciente"),
            @Mapping(source = "numeroCartaoSusPaciente", target = "numero_cartao_sus_paciente"),
            @Mapping(source = "sexoPaciente", target = "sexo_paciente"),
            @Mapping(source = "rgPaciente", target = "rg_paciente"),
            @Mapping(source = "alturaPaciente", target = "altura_paciente"),
            @Mapping(source = "dataNascimentoPaciente", target = "data_nascimento_paciente"),
            @Mapping(source = "nomeMaePaciente", target = "nome_mae_paciente")
            /*@Mapping(source = "account_non_expired_usuario", target = "accountNonExpiredUsuario"),
            @Mapping(source = "account_non_locked_usuario", target = "accountNonLockedUsuario"),
            @Mapping(source = "credentials_non_expired_usuario", target = "credentialsNonExpiredUsuario"),
            */
            //@Mapping(source = "usuario.status_usuario", target = "statusUsuario"),
            //@Mapping(source = "permissaoList",target = "permissaoDTOList", qualifiedByName = "mapPermissaoListToPermissaoDTOList")
    })
    Paciente pacienteDTOParaPaciente(PacienteDTO pacienteDTO);

}