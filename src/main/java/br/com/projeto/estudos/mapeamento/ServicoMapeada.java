package br.com.projeto.estudos.mapeamento;


import br.com.projeto.estudos.dto.ServicoDTO;

import br.com.projeto.estudos.modelo.Servico;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ServicoMapeada {

    @Mappings({
            @Mapping(source = "id_servico", target = "idServico"),
            @Mapping(source = "nome_servico", target = "nomeServico"),
            @Mapping(source = "descricao_servico", target = "descricaoServico"),
            @Mapping(source = "status_servico", target = "statusServico")
    })
    ServicoDTO servicoParaServicoDTO(Servico servico);

    @Mappings({
            @Mapping(source = "idServico", target = "id_servico"),
            @Mapping(source = "nomeServico", target = "nome_servico"),
            @Mapping(source = "descricaoServico", target = "descricao_servico"),
            @Mapping(source = "statusServico", target = "status_servico")
    })
    Servico servicoDTOParaServico(ServicoDTO servicoDTO);
}