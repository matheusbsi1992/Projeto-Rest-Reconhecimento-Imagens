package br.com.projeto.estudos.controler;

import br.com.projeto.estudos.dto.PacienteDTO;
import br.com.projeto.estudos.servico.paciente.implementacao.PacienteServicoImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static br.com.projeto.estudos.exceptions.utilexceptions.UtilMyBindingResult.resulTerror;

@RestController
@RequestMapping("/api/paciente/v1")
@Tag(name = "Paciente", description = "EndPoint para o gerenciamento de Pacientes")
public class PacienteControler {

    @Autowired
    private PacienteServicoImpl pacienteServico;

    @PostMapping("/inserirPaciente")
    @Operation(summary = "Inserir Paciente", description = "Inserir Paciente", tags = {"Paciente"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = PacienteDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = {@Content}),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = {@Content})
            })
    public ResponseEntity<?> salvarPaciente(@Valid @RequestBody PacienteDTO pacienteDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }

        PacienteDTO novoPaciente = pacienteServico.salvarPaciente(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente);
    }

    @PutMapping("/alterarPaciente")
    @Operation(summary = "Alterar Paciente", description = "Alterar dados do Paciente", tags = {"Paciente"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = PacienteDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = {@Content}),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = {@Content})
            })
    public ResponseEntity<?> alterarPaciente(@Valid @RequestBody PacienteDTO pacienteDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }

        PacienteDTO pacienteAtualizado = pacienteServico.alterarPaciente(pacienteDTO);
        return ResponseEntity.ok(pacienteAtualizado);
    }

    @GetMapping("/obterPaciente/{id}")
    @Operation(summary = "Obter Paciente", description = "Obter paciente por ID", tags = {"Paciente"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = PacienteDTO.class))
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = {@Content}),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = {@Content})
            })
    public ResponseEntity<?> obterPacientePorId(@PathVariable String id) {
        PacienteDTO paciente = pacienteServico.obterPacientePorId(id);
        return ResponseEntity.ok(paciente);
    }

    @GetMapping("/listarPacientes")
    @Operation(summary = "Listar Pacientes", description = "Listar todos os pacientes", tags = {"Paciente"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = PacienteDTO.class))
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = {@Content}),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = {@Content})
            })
    public ResponseEntity<?> listarPacientes(@RequestParam(defaultValue = "0", required = false) int pagina,
                                             @RequestParam(defaultValue = "20", required = false) int itens) {
        Page<PacienteDTO> pacientes = pacienteServico.listarPacientes(PageRequest.of(pagina, itens));
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/listarPacientesPorNome")
    @Operation(summary = "Listar Pacientes por Nome", description = "Listar pacientes por nome", tags = {"Paciente"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = PacienteDTO.class))
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = {@Content}),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = {@Content})
            })
    public ResponseEntity<?> listarPacientesPorNome(@PathVariable String nomePaciente,
                                                    @RequestParam(defaultValue = "0", required = false) int pagina,
                                                    @RequestParam(defaultValue = "20", required = false) int itens) {
        Page<PacienteDTO> pacientes = pacienteServico.listarPacientesPorNome(nomePaciente, PageRequest.of(pagina,itens));
        return ResponseEntity.ok(pacientes);
    }

    @DeleteMapping("/deletarPaciente/{id}")
    @Operation(summary = "Deletar Paciente", description = "Deletar paciente por ID", tags = {"Paciente"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "204",
                            content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = {@Content}),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = {@Content})
            })
    public ResponseEntity<?> deletarPaciente(@PathVariable String id) {
        pacienteServico.deletarPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/desativarPaciente/{id}")
    @Operation(summary = "Desativar Paciente", description = "Desativar paciente por ID", tags = {"Paciente"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "204",
                            content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = {@Content}),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = {@Content})
            })
    public ResponseEntity<?> desativarPaciente(@PathVariable String id) {
        pacienteServico.desativarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
