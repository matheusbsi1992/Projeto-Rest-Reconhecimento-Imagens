package br.com.projeto.estudos.servico.paciente.implementacao;

import br.com.projeto.estudos.dto.PacienteDTO;
import br.com.projeto.estudos.dto.PermissaoDTO;
import br.com.projeto.estudos.mapeamento.PacienteMapeada;
import br.com.projeto.estudos.mapeamento.UsuarioMapeada;
import br.com.projeto.estudos.modelo.Paciente;
import br.com.projeto.estudos.modelo.Pessoa;
import br.com.projeto.estudos.repositorio.PacienteRepositorio;
import br.com.projeto.estudos.repositorio.PermissaoRepositorio;
import br.com.projeto.estudos.seguranca.jwt.UsuarioAutenticado;
import br.com.projeto.estudos.servico.usuario.implementacao.UsuarioServicoImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static br.com.projeto.estudos.util.Util.gerarSenha;
import static br.com.projeto.estudos.util.Util.passwordEncryptEncoder;

@Service
public class PacienteServicoImpl {

    private Logger logger = Logger.getLogger(PacienteServicoImpl.class.getName());

    private PacienteMapeada pacienteMapeada;
    private Paciente paciente;
    private PacienteRepositorio pacienteRepositorio;

    private UsuarioAutenticado usuarioAutenticado;

    private UsuarioServicoImpl usuarioServico;
    private UsuarioMapeada usuarioMapeada;

    private PermissaoRepositorio permissaoRepositorio;

    private Pessoa pessoa;


    @Autowired
    public PacienteServicoImpl(
            UsuarioAutenticado usuarioAutenticado,
            UsuarioServicoImpl usuarioServico,
            UsuarioMapeada usuarioMapeada,
            PacienteMapeada pacienteMapeada,
            PacienteRepositorio pacienteRepositorio,
            PermissaoRepositorio permissaoRepositorio
    ) {
        this.pacienteMapeada = pacienteMapeada;
        this.pacienteRepositorio = pacienteRepositorio;

        this.usuarioAutenticado = usuarioAutenticado;
        this.usuarioServico = usuarioServico;
        this.usuarioMapeada = usuarioMapeada;

        this.permissaoRepositorio = permissaoRepositorio;

        this.pessoa = Objects.requireNonNull(new Pessoa());

        this.paciente = Objects.requireNonNull(new Paciente());
    }

    //@Transactional
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public PacienteDTO salvarPaciente(PacienteDTO pacienteDTO) {
        logger.info("Salvando Paciente..." + pacienteDTO);

        if (!usuarioAutenticado.temPermissao("ROLE_USER")) {

            // Converter DTO para entidade Paciente
            paciente = pacienteMapeada.pacienteDTOParaPaciente(pacienteDTO);

            // Identificar a permissão identificando a permissao
            // Configurar permissões para o usuário
            pacienteDTO.getUsuario().setPermissaoDTOList(List.of(new PermissaoDTO(permissaoRepositorio.buscarPorPermissao("ROLE_CUSTOMER"))));

            // Gerar senha para o paciente
            String senhaPaciente = gerarSenha(8);
            pacienteDTO.getUsuario().setSenhaUsuario(passwordEncryptEncoder(senhaPaciente));
            pacienteDTO.getUsuario().setUsuario("PACIENTE");

            // Salvar o usuário e associar ao paciente
            var usuarioDTO = usuarioServico.salvarUsuario(pacienteDTO.getUsuario());
            var usuario = usuarioMapeada.usuarioDTOParaUsuario(usuarioDTO);

            // Associar o usuário ao paciente (id_usuario será o mesmo para id_usuario_paciente)
            paciente.setUsuario(usuario);
            paciente.setId_usuario_paciente(usuario.getId_usuario());
            // Configurar a pessoa associada ao paciente
            pessoa.setId_pessoa(usuarioAutenticado.getUsuarioLogado().getId_usuario());
            paciente.setPessoa(pessoa);

            // Salvar o paciente
            pacienteRepositorio.save(paciente);

            return pacienteMapeada.pacienteParaPacienteDTO(paciente);
        }
        throw new SecurityException("Acesso negado para Salvar Paciente");

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
    public PacienteDTO alterarPaciente(PacienteDTO pacienteDTO) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN") || !usuarioAutenticado.temPermissao("ROLE_USER")) {

            // Mapeia o paciente DTO para entidade
            var paciente = pacienteMapeada.pacienteDTOParaPaciente(pacienteDTO);
            // Buscar o paciente existente para preservar a data de cadastro
            var pacienteExistente = pacienteRepositorio.buscarPacientePorID(paciente.getId_usuario_paciente())
                    .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado !!!"));
            // Mantém a data de cadastro do paciente existente
            pacienteExistente.getUsuario().setData_cadastro_usuario(pacienteExistente.getUsuario().getData_cadastro_usuario());
            // Define a data de atualização
            pacienteExistente.getUsuario().setData_atualizado_usuario(LocalDateTime.now());
            // Salva o paciente atualizado no repositório
            var pacienteSalva = pacienteRepositorio.save(pacienteExistente);
            // Mapeia o paciente salvo para o DTO e retorna os dados
            return pacienteMapeada.pacienteParaPacienteDTO(pacienteSalva);
        }
        throw new SecurityException("Acesso negado para Atualizar Paciente");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public PacienteDTO obterPacientePorId(String id) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN") || !usuarioAutenticado.temPermissao("ROLE_USER")) {

            var paciente = pacienteRepositorio.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
            return pacienteMapeada.pacienteParaPacienteDTO(paciente);
        }
        throw new SecurityException("Acesso negado para Buscar Paciente !!!");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_USER')")
    public Page<PacienteDTO> listarPacientes(Pageable pageable) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN") || !usuarioAutenticado.temPermissao("ROLE_USER")) {

            Page<Paciente> pacientes = pacienteRepositorio.findAll(pageable);
            return pacientes//.stream()
                    .map(pacienteMapeada::pacienteParaPacienteDTO);
            //.collect(Collectors.toList());
     /*   if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_USER")) {
            Optional<Unidade> unidadeUsuario = unidadeRepositorio.findById(usuarioAutenticado.getUsuarioLogado().getUnidade().getId_unidade());
            return unidadeUsuario.stream()
                    .map(unidadeMapper::unidadeParaUnidadeDTO)
                    .collect(Collectors.toList());
        }*/
        }
        throw new SecurityException("Acesso negado para Listar Pacientes");
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public Page<PacienteDTO> listarPacientesPorNome(String nomePaciente, Pageable pageable) {

        if (!usuarioAutenticado.temPermissao("ROLE_ADMIN")) {

            Page<Paciente> pacientes = pacienteRepositorio.listarPacientesPorNome(nomePaciente, pageable);
            return pacientes//.stream()
                    .map(pacienteMapeada::pacienteParaPacienteDTO);
            //.collect(Collectors.toList());
        }
        throw new SecurityException("Acesso negado para Listar Pacientes por Nome");
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public void deletarPaciente(String id) {

        if (!usuarioAutenticado.temPermissao("ROLE_USER")) {

            var paciente = pacienteRepositorio.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

            pacienteRepositorio.deleteById(paciente.getId_usuario_paciente());

        }
        throw new SecurityException("Acesso negado para Deletar o Paciente !!!");
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public void desativarPaciente(String id) {

        if (!usuarioAutenticado.temPermissao("ROLE_USER")) {

            var paciente = pacienteRepositorio.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

            paciente.setStatus_paciente(false);

            pacienteRepositorio.save(paciente);
        }
        throw new SecurityException("Acesso negado para Desativar o Paciente !!!");
    }


}