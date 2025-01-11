package br.com.projeto.estudos.controler;

import br.com.projeto.estudos.dto.UnidadeDTO;
import br.com.projeto.estudos.dto.UnidadeFilialPessoaLocalDTO;
import br.com.projeto.estudos.servico.unidade.implementacao.UnidadeServicoImpl;
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
@RequestMapping("/api/unidade/v1")
@Tag(name = "Unidade", description = "EndPoint para o gerenciamento de Unidade")
public class UnidadeControler {

    @Autowired
    private UnidadeServicoImpl unidadeServico;

    // Endpoint para listar todas as unidades
    @PatchMapping("/listarUnidade")
    public ResponseEntity<Page<UnidadeDTO>> retornarUnidades(@RequestParam(defaultValue = "0", required = false) int pagina,
                                                             @RequestParam(defaultValue = "10", required = false) int itens) {
        Page<UnidadeDTO> unidades = unidadeServico.listarUnidades(PageRequest.of(pagina, itens));
        return ResponseEntity.ok(unidades);
    }

    @PatchMapping(value = "/listarUnidade/{nomeFantasiaUnidade}")
    public ResponseEntity<Page<UnidadeDTO>> listarUnidadesPorNomeDeFantasia(@RequestParam(defaultValue = "0", required = false) int pagina,
                                                                            @RequestParam(defaultValue = "10", required = false) int itens,
                                                                            @PathVariable String nomeFantasiaUnidade) {
        Page<UnidadeDTO> unidades = unidadeServico.listarUnidadesPorNomeDeFantasia(nomeFantasiaUnidade,PageRequest.of(pagina, itens));
        return ResponseEntity.ok(unidades);
    }

    // Endpoint para obter uma unidade por ID
    @GetMapping(value = "/buscarUnidadePorId/{idUnidade}")
    public ResponseEntity<?> obterUnidadePorId(@PathVariable String idUnidade) {
        UnidadeDTO unidade = unidadeServico.obterUnidadePorId(idUnidade);
        return ResponseEntity.ok(unidade);
    }

    // Endpoint para salvar uma nova unidade
    @PostMapping(value = "/inserirUnidade")
    @Operation(summary = "Inserir Unidade", description = "Inserir Unidade", tags = {"Unidade"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = UnidadeDTO.class))
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
    public ResponseEntity<?> salvarUnidade(@Valid @RequestBody UnidadeDTO unidadeDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }
        UnidadeDTO novaUnidade = unidadeServico.salvarUnidade(unidadeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaUnidade);
    }

    // Endpoint para atualizar uma unidade existente
    @PatchMapping(value = "/alterarUnidade")
    public ResponseEntity<?> alterarUnidade(@Valid @RequestBody UnidadeDTO unidadeDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }
        UnidadeDTO unidadeAtualizada = unidadeServico.alterarUnidade(unidadeDTO);
        return ResponseEntity.ok(unidadeAtualizada);
    }

    // Endpoint para deletar uma unidade por ID
    @DeleteMapping(value = "/deletarUnidade/{idUnidade}")
    @Operation(summary = "Deletar Unidade", description = "Deletar Unidade", tags = {"Unidade"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = UnidadeDTO.class))
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
    public ResponseEntity<?> deletarUnidade(@Valid @PathVariable String idUnidade) {
        unidadeServico.deletarUnidade(idUnidade);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para listar todas as Unidades com Filial de cada Pessoa
    @GetMapping(value = "/unidadeGeral", produces = "application/json")
    public ResponseEntity<Page<UnidadeFilialPessoaLocalDTO>> retornarUnidadeFilial(@RequestParam(defaultValue = "0", required = false) int pagina,
                                                                                   @RequestParam(defaultValue = "10", required = false) int itens) {
        return ResponseEntity.ok(unidadeServico.listarUnidadeFilialPessoaLocal(PageRequest.of(pagina, itens)));
    }

    @GetMapping(value = "/unidade", produces = "application/json")
    public ResponseEntity<Page<UnidadeFilialPessoaLocalDTO>> retornarUnidadeFilialPessoa(@RequestParam(defaultValue = "0", required = false) int pagina,
                                                                                         @RequestParam(defaultValue = "10", required = false) int itens) {
        return ResponseEntity.ok(unidadeServico.listarUnidadeFilialDaPessoaLocal(PageRequest.of(pagina, itens)));
    }

    @GetMapping(value = "/empresas", produces = "application/json")
    public ResponseEntity<Page<UnidadeFilialPessoaLocalDTO>> listarUnidadesFiliais(
            @RequestParam(defaultValue = "0", required = false) int pagina,
            @RequestParam(defaultValue = "10", required = false) int itens) {

        return ResponseEntity.ok(unidadeServico.listarConvenioUnidadeFilialDaPessoaLocal(PageRequest.of(pagina, itens)));
    }


}