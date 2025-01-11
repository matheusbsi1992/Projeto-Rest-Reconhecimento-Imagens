package br.com.projeto.estudos.controler;

import br.com.projeto.estudos.dto.ConvenioDTO;
import br.com.projeto.estudos.servico.convenio.implementacao.ConvenioServicoImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.projeto.estudos.exceptions.utilexceptions.UtilMyBindingResult.resulTerror;

@RestController
@RequestMapping("/api/convenio/v1")
@Tag(name = "Convênio", description = "EndPoint para o gerenciamento de Convênios")
public class ConvenioControler {

    @Autowired
    private ConvenioServicoImpl convenioServico;

    // Endpoint para listar todos os convênios
    @GetMapping("/listarConvenio")
    public ResponseEntity<List<ConvenioDTO>> retornarConvenios() {
        List<ConvenioDTO> convenios = convenioServico.listarConvenios();
        return ResponseEntity.ok(convenios);
    }


    // Endpoint para obter um convênio por ID
    @GetMapping(value = "/buscarConvenioPorId/{idConvenio}")
    public ResponseEntity<?> obterConvenioPorId(@PathVariable String  idConvenio) {
        ConvenioDTO convenio = convenioServico.obterConvenioPorId(idConvenio);
        return ResponseEntity.ok(convenio);
    }

    // Endpoint para salvar um novo convênio
    @PostMapping(value = "/inserirConvenio")
    @Operation(summary = "Inserir Convênio", description = "Inserir Convênio", tags = {"Convênio"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = ConvenioDTO.class))
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
    public ResponseEntity<?> salvarConvenio(@Valid @RequestBody ConvenioDTO convenioDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }
        ConvenioDTO novoConvenio = convenioServico.salvarConvenio(convenioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoConvenio);
    }

    // Endpoint para atualizar um convênio existente
    @PatchMapping(value = "/alterarConvenio")
    public ResponseEntity<?> alterarConvenio(@Valid @RequestBody ConvenioDTO convenioDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }
        ConvenioDTO convenioAtualizado = convenioServico.alterarConvenio(convenioDTO);
        return ResponseEntity.ok(convenioAtualizado);
    }

    // Endpoint para deletar um convênio por ID
    @DeleteMapping(value = "/deletarConvenio/{idConvenio}")
    @Operation(summary = "Deletar Convênio", description = "Deletar Convênio", tags = {"Convênio"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = ConvenioDTO.class))
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
    public ResponseEntity<?> deletarConvenio(@Valid @PathVariable String  idConvenio) {
        convenioServico.deletarConvenio(idConvenio);
        return ResponseEntity.noContent().build();
    }
}