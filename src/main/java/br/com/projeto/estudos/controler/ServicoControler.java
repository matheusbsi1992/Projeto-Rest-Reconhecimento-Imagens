package br.com.projeto.estudos.controler;

import br.com.projeto.estudos.dto.ServicoDTO;
import br.com.projeto.estudos.servico.servico.implementacao.ServicoImpl;
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

import java.util.List;

import static br.com.projeto.estudos.exceptions.utilexceptions.UtilMyBindingResult.resulTerror;

@RestController
@RequestMapping("/api/servico/v1")
@Tag(name = "Servico", description = "EndPoint para o gerenciamento de Serviço")
public class ServicoControler {

    @Autowired
    private ServicoImpl servico;

    // Endpoint para listar todas as Servicos
    @PatchMapping("/listarServico")
    public ResponseEntity<Page<ServicoDTO>> retornarServicos(@RequestParam(defaultValue = "0", required = false) int pagina,
                                                             @RequestParam(defaultValue = "5", required = false) int itens) {
        Page<ServicoDTO> Servicos = servico.listarServicos(PageRequest.of(pagina, itens));
        return ResponseEntity.ok(Servicos);
    }

    @PatchMapping(value = "/listarServico/{nomeFantasiaServico}")
    public ResponseEntity<List<ServicoDTO>> listarServicosPorNomeServico(@PathVariable String nomeServico) {
        List<ServicoDTO> Servicos = servico.listarServicosPorNomeDeServico(nomeServico);
        return ResponseEntity.ok(Servicos);
    }

    // Endpoint para obter uma Servico por ID
    @GetMapping(value = "/buscarServicoPorId/{idServico}")
    public ResponseEntity<?> obterServicoPorId(@PathVariable Long idServico) {
        ServicoDTO Servico = servico.obterServicoPorId(idServico);
        return ResponseEntity.ok(Servico);
    }

    // Endpoint para salvar uma nova Servico
    @PostMapping(value = "/inserirServico")
    @Operation(summary = "Inserir Servico", description = "Inserir Servico", tags = {"Servico"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = ServicoDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = {
                                    @Content
                            }),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = {
                                    @Content
                            }),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = {
                                    @Content
                            }),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = {@Content})
            })
    public ResponseEntity<?> salvarServico(@Valid @RequestBody ServicoDTO ServicoDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }
        ServicoDTO novaServico = servico.salvarServico(ServicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaServico);
    }

    // Endpoint para atualizar uma Servico existente
    @PatchMapping(value = "/alterarServico")
    public ResponseEntity<?> alterarServico(@Valid @RequestBody ServicoDTO ServicoDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }
        ServicoDTO ServicoAtualizada = servico.alterarServico(ServicoDTO);
        return ResponseEntity.ok(ServicoAtualizada);
    }

    // Endpoint para deletar uma Servico por ID
    @DeleteMapping(value = "/deletarServico/{idServico}")
    @Operation(summary = "Deletar Servico", description = "Deletar Servico", tags = {"Servico"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = ServicoDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = {
                                    @Content
                            }),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = {
                                    @Content
                            }),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = {
                                    @Content
                            }),
                    @ApiResponse(description = "Internal Error", responseCode = "500",
                            content = {
                                    @Content
                            })
            })
    public ResponseEntity<?> deletarServico(@Valid @PathVariable Long idServico) {
        servico.deletarServico((idServico));
        return ResponseEntity.noContent().build();
    }



}