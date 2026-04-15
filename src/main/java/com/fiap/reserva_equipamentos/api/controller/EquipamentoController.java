package com.fiap.reserva_equipamentos.api.controller;

import com.fiap.reserva_equipamentos.api.dto.EquipamentoRequestDTO;
import com.fiap.reserva_equipamentos.api.dto.EquipamentoResponseDTO;
import com.fiap.reserva_equipamentos.api.service.EquipamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de Equipamentos.
 * Implementa operações CRUD completas seguindo padrões REST.
 */
@RestController
@RequestMapping("/api/equipamentos")
@Tag(name = "Equipamentos", description = "API para gerenciamento de equipamentos")
public class EquipamentoController {
    
    private final EquipamentoService service;
    
    public EquipamentoController(EquipamentoService service) {
        this.service = service;
    }

    /**
     * Lista todos os equipamentos ativos.
     * GET /api/equipamentos/ativos
     */
    @GetMapping("/ativos")
    @Operation(summary = "Listar equipamentos ativos",
               description = "Retorna uma lista de todos os equipamentos com status ativo")
    @ApiResponse(responseCode = "200", description = "Lista de equipamentos ativos retornada com sucesso")
    public ResponseEntity<List<EquipamentoResponseDTO>> listarAtivos() {
        List<EquipamentoResponseDTO> equipamentos = service.listarAtivos();
        return ResponseEntity.ok(equipamentos);
    }

    /**
     * Busca equipamentos por termo ou lista todos.
     * GET /api/equipamentos?q=termo
     */
    @GetMapping
    @Operation(summary = "Buscar ou listar equipamentos",
               description = "Busca equipamentos por termo na descrição ou lista todos se nenhum termo for fornecido")
    @ApiResponse(responseCode = "200", description = "Lista de equipamentos retornada com sucesso")
    public ResponseEntity<List<EquipamentoResponseDTO>> buscar(
            @Parameter(description = "Termo de busca na descrição do equipamento")
            @RequestParam(required = false, defaultValue = "") String q) {
        
        if (q.isBlank()) {
            return ResponseEntity.ok(service.listarTodos());
        }
        return ResponseEntity.ok(service.buscar(q));
    }

    /**
     * Busca um equipamento por ID.
     * GET /api/equipamentos/{id}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar equipamento por ID",
               description = "Retorna um equipamento específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipamento encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Equipamento não encontrado",
                     content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<EquipamentoResponseDTO> buscarPorId(
            @Parameter(description = "ID do equipamento", required = true)
            @PathVariable Long id) {
        
        EquipamentoResponseDTO equipamento = service.buscarPorId(id);
        return ResponseEntity.ok(equipamento);
    }

    /**
     * Cria um novo equipamento.
     * POST /api/equipamentos
     */
    @PostMapping
    @Operation(summary = "Criar novo equipamento",
               description = "Cria um novo equipamento no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Equipamento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                     content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<EquipamentoResponseDTO> criar(
            @Parameter(description = "Dados do equipamento a ser criado", required = true)
            @Valid @RequestBody EquipamentoRequestDTO dto) {
        
        EquipamentoResponseDTO criado = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    /**
     * Atualiza um equipamento existente.
     * PUT /api/equipamentos/{id}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar equipamento",
               description = "Atualiza os dados de um equipamento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipamento atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Equipamento não encontrado",
                     content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                     content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<EquipamentoResponseDTO> atualizar(
            @Parameter(description = "ID do equipamento", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados do equipamento", required = true)
            @Valid @RequestBody EquipamentoRequestDTO dto) {
        
        EquipamentoResponseDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Deleta logicamente um equipamento (marca como inativo).
     * DELETE /api/equipamentos/{id}
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar equipamento",
               description = "Realiza a exclusão lógica do equipamento (marca como inativo)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Equipamento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Equipamento não encontrado",
                     content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do equipamento", required = true)
            @PathVariable Long id) {
        
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}