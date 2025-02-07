package br.com.joaopedro.objective.dto;

import java.math.BigDecimal;

public record ContaResponseDTO(
        Long numero_conta,
        BigDecimal saldo
) {
}
