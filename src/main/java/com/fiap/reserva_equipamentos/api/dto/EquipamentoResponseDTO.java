package com.fiap.reserva_equipamentos.api.dto;

import com.fiap.reserva_equipamentos.api.domain.Equipamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respostas de Equipamento.
 * Contém todos os campos que devem ser retornados ao cliente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de um equipamento")
public class EquipamentoResponseDTO {

    @Schema(description = "ID único do equipamento", example = "1")
    private Long id;

    @Schema(description = "Descrição do equipamento", example = "Datashow Epson PowerLite")
    private String descricao;

    @Schema(description = "Indica se o equipamento está ativo", example = "true")
    private Boolean ativo;

    /**
     * Converte uma entidade Equipamento para DTO de resposta.
     */
    public static EquipamentoResponseDTO fromEntity(Equipamento equipamento) {
        return new EquipamentoResponseDTO(
            equipamento.getId(),
            equipamento.getDescricao(),
            equipamento.getAtivo()
        );
    }

    /**
     * Converte um DTO de requisição para entidade Equipamento.
     */
    public static Equipamento toEntity(EquipamentoRequestDTO dto) {
        Equipamento equipamento = new Equipamento();
        equipamento.setDescricao(dto.getDescricao());
        equipamento.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        return equipamento;
    }

    /**
     * Atualiza uma entidade existente com dados do DTO de requisição.
     */
    public static void updateEntity(Equipamento equipamento, EquipamentoRequestDTO dto) {
        equipamento.setDescricao(dto.getDescricao());
        if (dto.getAtivo() != null) {
            equipamento.setAtivo(dto.getAtivo());
        }
    }
}

