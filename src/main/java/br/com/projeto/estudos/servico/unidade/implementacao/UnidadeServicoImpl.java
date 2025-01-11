package br.com.projeto.estudos.servico.unidade.implementacao;


import br.com.projeto.estudos.dto.UnidadeDTO;
import br.com.projeto.estudos.dto.UnidadeFilialPessoaLocalDTO;
import br.com.projeto.estudos.mapeamento.UnidadeFilialPessoaLocalMapeada;
import br.com.projeto.estudos.mapeamento.UnidadeMapeada;
import br.com.projeto.estudos.modelo.Unidade;
import br.com.projeto.estudos.repositorio.UnidadeRepositorio;
import br.com.projeto.estudos.seguranca.jwt.UsuarioAutenticado;
import br.com.projeto.estudos.servico.unidade.integracao.UnidadeServico;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UnidadeServicoImpl implements UnidadeServico {

    private UnidadeRepositorio unidadeRepositorio;

    private UnidadeMapeada unidadeMapeada;

    private UnidadeFilialPessoaLocalMapeada unidadeFilialPessoaLocalMapeada;

    @Autowired
    private UsuarioAutenticado usuarioAutenticado;


    @Autowired
    public UnidadeServicoImpl(UnidadeRepositorio unidadeRepositorio, UnidadeMapeada unidadeMapeada, UnidadeFilialPessoaLocalMapeada unidadeFilialPessoaLocalMapeada) {
        Objects.requireNonNull(unidadeMapeada);
        Objects.requireNonNull(unidadeRepositorio);
        Objects.requireNonNull(unidadeFilialPessoaLocalMapeada);
        this.unidadeFilialPessoaLocalMapeada = unidadeFilialPessoaLocalMapeada;
        this.unidadeRepositorio = unidadeRepositorio;
        this.unidadeMapeada = unidadeMapeada;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public UnidadeDTO salvarUnidade(UnidadeDTO unidadeDTO) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Salvar Unidade !!!");
        }
        Unidade unidade = unidadeMapeada.unidadeDTOParaUnidade(unidadeDTO);
        //unidade.setData_cadastro_unidade(LocalDateTime.now());
        Unidade unidadeSalva = unidadeRepositorio.save(unidade);

        return unidadeMapeada.unidadeParaUnidadeDTO(unidadeSalva);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public UnidadeDTO alterarUnidade(UnidadeDTO unidadeDTO) {
        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Atualizar Unidade");
        }
        // Busca a unidade existente para preservar a data de cadastro
        Unidade unidadeExistente = unidadeRepositorio.findById(unidadeDTO.getIdUnidade())
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));

        // Mapeia o DTO para a entidade sem sobrescrever a data de cadastro
        Unidade unidadeAtualizada = unidadeMapeada.unidadeDTOParaUnidade(unidadeDTO);

        // Mantém a data de cadastro da unidade existente
        unidadeAtualizada.setData_cadastro_unidade(unidadeExistente.getData_cadastro_unidade());

        // Define a data de atualização
        unidadeAtualizada.setData_atualizado_unidade(LocalDateTime.now());

        // Salva a unidade atualizada no repositório
        Unidade unidadeSalva = unidadeRepositorio.save(unidadeAtualizada);
        // Mapeia a unidade salva para o DTO e retorna
        return unidadeMapeada.unidadeParaUnidadeDTO(unidadeSalva);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public UnidadeDTO obterUnidadePorId(String id) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Buscar Unidade");
        }

        Unidade unidade = unidadeRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
        return unidadeMapeada.unidadeParaUnidadeDTO(unidade);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Page<UnidadeDTO> listarUnidades(Pageable pageable) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Listar Unidade");
        }
        Page<Unidade> unidades = unidadeRepositorio.findAll(pageable);
        return unidades//.stream()
                .map(unidadeMapeada::unidadeParaUnidadeDTO);
        //.collect(Collectors.toList());
     /*   if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_USER")) {
            Optional<Unidade> unidadeUsuario = unidadeRepositorio.findById(usuarioAutenticado.getUsuarioLogado().getUnidade().getId_unidade());
            return unidadeUsuario.stream()
                    .map(unidadeMapeada::unidadeParaUnidadeDTO)
                    .collect(Collectors.toList());
        }*/

    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Page<UnidadeDTO> listarUnidadesPorNomeDeFantasia(String nomeFantasiaUnidade, Pageable pageable) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Listar Unidade por Nome");
        }

        Page<Unidade> unidades = unidadeRepositorio.listarUnidadesPorNomeDeFantasia(nomeFantasiaUnidade, pageable);
        return unidades//.stream()
                .map(unidadeMapeada::unidadeParaUnidadeDTO);
        //.collect(Collectors.toList());
    }


    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deletarUnidade(String id) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Deletar a Unidade");
        }
        Unidade unidade = unidadeRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
        unidadeRepositorio.deleteById(unidade.getId_unidade());
    }


    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void desativarUnidade(String id) {
        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para Desativar a Unidade");
        }
        /*if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {*/
        Unidade unidade = unidadeRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
        unidade.setStatus_unidade(false);
        unidadeRepositorio.save(unidade);
        //}
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Page<UnidadeFilialPessoaLocalDTO> listarUnidadeFilialPessoaLocal(Pageable pageable) {
        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            throw new SecurityException("Acesso negado para listar Unidade/Filial.");
        }

        Page<UnidadeFilialPessoaLocalDTO> unidadeFilialPessoaLocalDTOS = unidadeRepositorio.listarUnidadeFilialPessoa(pageable);
        //idConvenio:null
        return unidadeFilialPessoaLocalDTOS.map(UnidadeFilialPessoaLocalDTO::new);

    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public Page<UnidadeFilialPessoaLocalDTO> listarUnidadeFilialDaPessoaLocal(Pageable pageable) {
        if (!usuarioAutenticado.temPermissao("ROLE_USER")) {
            throw new SecurityException("Acesso negado para listar Unidade/Filial da Pessoa Responsável.");
        }

        Page<UnidadeFilialPessoaLocalDTO> unidadeFilialPessoaLocalDTOS =
                unidadeRepositorio.listarUnidadeFilialPessoaLocal(usuarioAutenticado.getUsuarioLogado().getId_usuario(), pageable);
        // ---idPessoa:null | idConvenio:null | unidadeRepresentativa:null
        return unidadeFilialPessoaLocalDTOS.map(UnidadeFilialPessoaLocalDTO::new);

    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public Page<UnidadeFilialPessoaLocalDTO> listarConvenioUnidadeFilialDaPessoaLocal(Pageable pageable) {
        if (!usuarioAutenticado.temPermissao("ROLE_USER")) {
            throw new SecurityException("Acesso negado para listar Convênio da Unidade/Filial da Pessoa Responsável.");
        }

        Page<UnidadeFilialPessoaLocalDTO> convenioUnidadeFilialPessoaLocalDTOS =
                unidadeRepositorio.listarConvenioUnidadeFilialPessoaLocal(usuarioAutenticado.getUsuarioLogado().getId_usuario(), pageable);

        //List<UnidadeFilialPessoaLocalDTO.UnidadeFilialPessoaLocalProjection> unidadeFilialPessoaLocalDTOS = convenioUnidadeFilialPessoaLocalDTOS.getContent();
        return convenioUnidadeFilialPessoaLocalDTOS.map(UnidadeFilialPessoaLocalDTO::new);

    }

}