package br.com.projeto.estudos.controler;

import br.com.projeto.estudos.dto.UsuarioDTO;
import br.com.projeto.estudos.servico.usuario.implementacao.UsuarioServicoImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.projeto.estudos.exceptions.utilexceptions.UtilMyBindingResult.resulTerror;

@RestController
@RequestMapping("/api/usuario/v1")
@Tag(name = "Usuario", description = "EndPoint para o gerenciamento de Usuários")
public class UsuarioControler {

    @Autowired
    private UsuarioServicoImpl usuarioServico;

    // Endpoint para listar todos os usuários
    @GetMapping("/listarUsuarios")
    public ResponseEntity<Page<UsuarioDTO>> retornarUsuarios(@RequestParam(defaultValue = "0", required = false) int pagina,
                                                             @RequestParam(defaultValue = "5", required = false) int itens) {
        Page<UsuarioDTO> usuarios = usuarioServico.listarUsuarios(PageRequest.of(pagina, itens));
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/listarUsuariosPorNome/{nomeUsuario}")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosPorNome(@PathVariable String nomeUsuario) {
        List<UsuarioDTO> usuarios = usuarioServico.listarUsuariosPorNome(nomeUsuario);
        return ResponseEntity.ok(usuarios);
    }

    // Endpoint para obter um usuário por ID
    @GetMapping("/buscarUsuarioPorId/{idUsuario}")
    public ResponseEntity<?> obterUsuarioPorId(@PathVariable String  idUsuario) {
        UsuarioDTO usuario = usuarioServico.obterUsuarioPorId(idUsuario);
        return ResponseEntity.ok(usuario);
    }

    // Endpoint para salvar um novo usuário
    @PostMapping("/inserirUsuario")
    @Operation(summary = "Inserir Usuário", description = "Inserir Usuário", tags = {"Usuario"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = UsuarioDTO.class))
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
    public ResponseEntity<?> salvarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }
        UsuarioDTO novoUsuario = usuarioServico.salvarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    // Endpoint para alterar um novo usuário
    //@PostMapping("/alterarUsuario")
    @Operation(summary = "Alterar Usuário", description = "Alterar Usuário", tags = {"Usuario"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201",
                            content =
                            @Content(mediaType = "application/json"
                                    , schema = @Schema(implementation = UsuarioDTO.class))
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
    @PatchMapping("/alterarUsuario")
    public ResponseEntity<?> alterarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(resulTerror(result));
        }
        UsuarioDTO usuarioAtualizado = usuarioServico.alterarUsuario(usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // Endpoint para deletar um usuário por ID
    @DeleteMapping("/deletarUsuario/{idUsuario}")
    @Operation(summary = "Deletar Usuário", description = "Deletar Usuário", tags = {"Usuario"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "204",
                            content = @Content
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
    public ResponseEntity<?> deletarUsuario(@PathVariable String  idUsuario) {
        usuarioServico.deletarUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
