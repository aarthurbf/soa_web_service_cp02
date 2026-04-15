package com.fiap.reserva_equipamentos.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisições de criação e atualização de Equipamento.
 * Contém apenas os campos que podem ser enviados pelo cliente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para criar ou atualizar um equipamento")
public class EquipamentoRequestDTO {

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 3, max = 255, message = "Descrição deve ter entre 3 e 255 caracteres")
    @Schema(description = "Descrição do equipamento", example = "Datashow Epson PowerLite", required = true)
    private String descricao;

    @Schema(description = "Indica se o equipamento está ativo", example = "true", defaultValue = "true")
    private Boolean ativo = true;
}

