package br.com.projeto.estudos.controler;

import br.com.projeto.estudos.dto.FilialDTO;

import br.com.projeto.estudos.servico.filial.implementacao.FilialServicoImpl;

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
@RequestMapping("/api/filial/v1")
@Tag(name = "Filial", description = "EndPoint para o gerenciamento de Filial")
public class FilialControler {

    @Autowired
    private FilialServicoImpl filialServico;

    // Endpoint para listar todas as Filial
    @PatchMapping("/listarFilial")
    public ResponseEntity<List<FilialDTO>> retornarFilials() {
        List<FilialDTO> filials = filialServico.listarFilials();
        return ResponseEntity.ok(filials);
    }

    @PatchMapping(value = "/listarFilial/{nomeFantasiaFilial}")
    public ResponseEntity<List<FilialDTO>> listarFilialsPorNomeDeFantasia(@PathVariable String nomeFantasiaFilial) {
        List<FilialDTO> filials = filialServico.listarFilialsPorNomeDeFantasia(nomeFantasiaFilial);
        return ResponseEntity.ok(filials);
    }

    // Endpoint para obter uma Filial por ID
    @GetMapping(value = "/buscarFilialPorId/{idFilial}")
    public ResponseEntity<?> obterFilialPorId(@PathVariable String  idFilial) {
        FilialDTO filial = filialServico.obterFilialPorId(idFilial);
        return ResponseEntity.ok(filial);
    }

    // Endpoint para salvar uma nova filial
    @PostMapping(value = "/inserirFilial")
    @Operation(summary = "Inserir Filial", description = "Inserir Filial", tags = {"Filial"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = FilialDTO.class))
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
    public ResponseEntity<?> salvarFilial(@Valid @RequestBody FilialDTO filialDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }
        FilialDTO novaFilial = filialServico.salvarFilial(filialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFilial);
    }

    // Endpoint para atualizar uma Filial existente
    @PatchMapping(value = "/alterarFilial")
    public ResponseEntity<?> alterarFilial(@Valid @RequestBody FilialDTO filialDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }
        FilialDTO filialAtualizada = filialServico.alterarFilial(filialDTO);
        return ResponseEntity.ok(filialAtualizada);
    }

    // Endpoint para deletar uma filial por ID
    @DeleteMapping(value = "/deletarFilial/{idFilial}")
    @Operation(summary = "Deletar Filial", description = "Deletar Filial", tags = {"Filial"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = FilialDTO.class))
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
    public ResponseEntity<?> deletarFilial(@Valid @PathVariable String  idFilial) {
        filialServico.deletarFilial((idFilial));
        return ResponseEntity.noContent().build();
    }

}