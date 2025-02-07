package br.com.joaopedro.objective.dto;

import java.math.BigDecimal;

public record TransacaoResponseDTO(
        Long numero_conta,
        BigDecimal saldo
) {
}
