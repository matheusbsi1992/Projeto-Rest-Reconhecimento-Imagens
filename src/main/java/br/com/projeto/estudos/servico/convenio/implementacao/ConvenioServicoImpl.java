package br.com.projeto.estudos.servico.convenio.implementacao;

import br.com.projeto.estudos.dto.ConvenioDTO;
import br.com.projeto.estudos.mapeamento.ConvenioMapeada;
import br.com.projeto.estudos.modelo.Convenio;
import br.com.projeto.estudos.modelo.Pessoa;
import br.com.projeto.estudos.repositorio.ConvenioRepositorio;
import br.com.projeto.estudos.repositorio.PessoaRepositorio;
import br.com.projeto.estudos.seguranca.jwt.UsuarioAutenticado;
import br.com.projeto.estudos.servico.convenio.integracao.implementacao.ConvenioServico;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ConvenioServicoImpl implements ConvenioServico {

    private ConvenioRepositorio convenioRepositorio;

    private ConvenioMapeada convenioMapper;

    //private UnidadeRepositorio unidadeRepositorio;

    @Autowired
    private PessoaRepositorio pessoaRepositorio;

    //private UnidadeUniaoConvenio unidadeUniaoConvenio;

    @Autowired
    private UsuarioAutenticado usuarioAutenticado;

    @Autowired
    public ConvenioServicoImpl(ConvenioRepositorio convenioRepositorio, ConvenioMapeada convenioMapper, PessoaRepositorio pessoaRepositorio //UnidadeRepositorio unidadeRepositorio//, UnidadeUniaoConvenio unidadeUniaoConvenio) {
    ) {
        Objects.requireNonNull(convenioMapper);
        Objects.requireNonNull(convenioRepositorio);
        Objects.requireNonNull(pessoaRepositorio);
        // Objects.requireNonNull(unidadeUniaoConvenio);
        this.convenioRepositorio = convenioRepositorio;
        this.convenioMapper = convenioMapper;
        this.pessoaRepositorio = pessoaRepositorio;
        //this.unidadeRepositorio = unidadeRepositorio;
        //this.unidadeUniaoConvenio = unidadeUniaoConvenio;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ConvenioDTO salvarConvenio(ConvenioDTO convenioDTO) {

        if (usuarioAutenticado.temPermissao("ROLE_ADMIN") || usuarioAutenticado.temPermissao("ROLE_USER")) {

            // Converter DTO para entidade
            Convenio convenio = convenioMapper.convenioDTOParaConvenio(convenioDTO);
            // Verificar e associar as unidades ao convênio
            /*List<Unidade> unidades = convenioDTO.getUnidade().stream()
                    .map(unidadeDTO -> unidadeRepositorio.findById(unidadeDTO.getIdUnidade())//usuarioAutenticado.getUsuarioLogado().getId_usuario())
                            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada: " + unidadeDTO.getIdUnidade())))
                    .toList();

            // Definir as unidades no convênio
            convenio.setUnidades(unidades);
        */

            List<Pessoa> pessoas = new ArrayList<>();

            pessoas.add(pessoaRepositorio
                    .findById(usuarioAutenticado.getUsuarioLogado().getId_usuario())
                    .orElseThrow(() -> new EntityNotFoundException("Pessoa não identifiacada !!!")));

            //---Usuario esta contido para a relacao de NULL
            // Definir as unidades no convênio
            convenio.setPessoas(pessoas);

            //convenio.getUnidades().add(unidades);

            // Para garantir a bidirecionalidade, adicione o convênio na lista de convênios de cada unidade
    /*        for (Unidade unidade : unidades) {
                if (unidade.getConvenios() == null) {
                    unidade.setConvenios(new ArrayList<Convenio>());
                }
                unidade.getConvenios().add(convenio);
            }*/

            // Salvar o convênio com suas unidades
            Convenio convenioSalvo = convenioRepositorio.save(convenio);

            // Retornar o DTO do convênio salvo
            return convenioMapper.convenioParaConvenioDTO(convenioSalvo);

        }
        throw new SecurityException("Acesso negado para Salvar Convênio !!!");
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ConvenioDTO alterarConvenio(ConvenioDTO convenioDTO) {
        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN") ||
                usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_USER")) {

            // Buscar o convênio existente no banco de dados
            Convenio convenioExistente = convenioRepositorio.findById(convenioDTO.getIdConvenio())
                    .orElseThrow(() -> new EntityNotFoundException("Convênio não encontrado"));

            // Atualizar campos individuais do convênio existente
            convenioExistente.setNome_fantasia_convenio(convenioDTO.getNomeFantasiaConvenio());
            convenioExistente.setRazao_social_convenio(convenioDTO.getRazaoSocialConvenio());
            convenioExistente.setCnpj_convenio(convenioDTO.getCnpjConvenio());
            convenioExistente.setEmail_convenio(convenioDTO.getEmailConvenio());
            convenioExistente.setTelefone_convenio(convenioDTO.getTelefoneConvenio());
            convenioExistente.setSetor_convenio(convenioDTO.getSetorConvenio());

            // Preservar a data de cadastro e definir a data de atualização
            convenioExistente.setData_cadastro_convenio(convenioExistente.getData_cadastro_convenio());
            convenioExistente.setData_atualizado_convenio(LocalDateTime.now());

            // Atualizar Unidades, caso necessário (substituindo as unidades existentes)
/*
            if (convenioDTO.getUnidade() != null && !convenioDTO.getUnidade().isEmpty()) {
                List<Unidade> novasUnidades = convenioDTO.getUnidade().stream()
                        .map(unidadeDTO -> unidadeRepositorio.findById(unidadeDTO.getIdUnidade())
                                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada: " + unidadeDTO.getIdUnidade())))
                        .toList();

                convenioExistente.setUnidades(novasUnidades);
            }
*/

            // Salvar o convênio atualizado no banco
            Convenio convenioSalvo = convenioRepositorio.save(convenioExistente);

            // Retornar o DTO do convênio salvo
            return convenioMapper.convenioParaConvenioDTO(convenioSalvo);
        }

        throw new SecurityException("Acesso negado para Atualizar Convenio");
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ConvenioDTO obterConvenioPorId(String id) {
        Convenio convenio = convenioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Convenio não encontrado"));
        return convenioMapper.convenioParaConvenioDTO(convenio);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public List<ConvenioDTO> listarConvenios() {
        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
            List<Convenio> convenios = convenioRepositorio.findAll();
            return convenios.stream()
                    .map(convenioMapper::convenioParaConvenioDTO)
                    .collect(Collectors.toList());
        }
        /*if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_USER")) {
            Optional<Convenio> convenio = convenioRepositorio.findById(usuarioAutenticado.getUsuarioLogado().getUnidade().getId_unidade());
            return convenio.stream()
                    .map(convenioMapper::convenioParaConvenioDTO)
                    .collect(Collectors.toList());
        }*/
        throw new SecurityException("Acesso negado para Listar Convenio");
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public void deletarConvenio(String id) {
        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN") || usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_USER")) {

      /*  List<Unidade> unidades = convenioDTO.getUnidade().stream()
                    .map(unidadeDTO -> unidadeRepositorio.findById(usuarioAutenticado.getUsuarioLogado().getId_usuario())
                            .orElseThrow(() -> new RuntimeException("Unidade não encontrada: " + unidadeDTO.getIdUnidade())))
                    .toList();

            // Definir as unidades no convênio
            convenio.setUnidade(unidades);
*/
            Convenio convenio = convenioRepositorio.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Convenio não encontrado"));

            /*convenio.setUnidade(unidade);*/

            convenioRepositorio.deleteById(convenio.getId_convenio());
        }
        throw new SecurityException("Acesso negado para Deletar Convenio");
    }
}