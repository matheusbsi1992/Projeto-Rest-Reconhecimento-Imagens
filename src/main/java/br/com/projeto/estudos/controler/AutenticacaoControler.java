package br.com.projeto.estudos.controler;

import br.com.projeto.estudos.dto.ContaCredencialDTO;
import br.com.projeto.estudos.servico.usuario.implementacao.AutenticacaoServiceImpl;
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

import static br.com.projeto.estudos.exceptions.utilexceptions.UtilMyBindingResult.resulTerror;


@RestController
@RequestMapping("/auth")
@Tag(name = "Endpoint de Autenticacao")
public class AutenticacaoControler {

    @Autowired
    private AutenticacaoServiceImpl autenticacaoService;


    @PostMapping(value = "/signin"
    )
    @Operation(summary = "Signin", description = "Signin", tags = {"Endpoint de Autenticacao"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = ContaCredencialDTO.class))
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
    public ResponseEntity<?> signIn(@Valid @RequestBody ContaCredencialDTO contasCredencialVO, BindingResult result) {
        /*if (contasCredencialVO == null ||
                contasCredencialVO.getUsuario().isBlank() || contasCredencialVO.getUsuario().isEmpty() || contasCredencialVO.getUsuario() == null
                || contasCredencialVO.getSenhaUsuario().isBlank() || contasCredencialVO.getSenhaUsuario().isEmpty() || contasCredencialVO.getSenhaUsuario() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }*/
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }
        var token = autenticacaoService.signin(contasCredencialVO);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        } else {
            return token;
        }
    }

    @PutMapping(value = "/refresh/{username}")
    @Operation(summary = "Refresh", description = "Refresh", tags = {"Authentication Endpoint"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = ContaCredencialDTO.class))
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
    public ResponseEntity<?> refresh(@PathVariable("usuario") String usuario, @RequestHeader("Authorization") String refreshToken) {
        if (refreshToken == null ||
                refreshToken.isBlank() || refreshToken.isEmpty() || usuario == null
                || usuario.isBlank() || usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }
        System.out.println("refresh token --->>>" + refreshToken);
        var token = autenticacaoService.refreshToken(usuario, refreshToken);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        } else {
            return token;
        }
    }

}
