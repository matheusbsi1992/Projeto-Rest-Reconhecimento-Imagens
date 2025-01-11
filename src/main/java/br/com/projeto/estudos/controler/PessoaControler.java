package br.com.projeto.estudos.controler;

import br.com.projeto.estudos.dto.PessoaDTO;
import br.com.projeto.estudos.dto.ServicoDTO;
import br.com.projeto.estudos.dto.UsuarioDTO;
import br.com.projeto.estudos.servico.pessoa.implementacao.PessoaServicoImpl;
import br.com.projeto.estudos.servico.usuario.implementacao.UsuarioServicoImpl;
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
import java.util.Map;

import static br.com.projeto.estudos.exceptions.utilexceptions.UtilMyBindingResult.resulTerror;

@RestController
@RequestMapping("/api/pessoa/v1")
@Tag(name = "Pessoa", description = "EndPoint para o gerenciamento de Pessoas")
public class PessoaControler {

    @Autowired
    private PessoaServicoImpl pessoaServico;


    // Endpoint para inserirPessoa
    @PostMapping("/inserirPessoa")
    @Operation(summary = "Inserir Pessoa", description = "Inserir Pessoa", tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = PessoaDTO.class))
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
    public ResponseEntity<?> salvarPessoa(@Valid @RequestBody PessoaDTO pessoaDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }
        PessoaDTO novaPessoa = pessoaServico.salvarPessoaUnidadeFilial(pessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }



}