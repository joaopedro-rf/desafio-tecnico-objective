package br.com.joaopedro.objective.dto;

import java.math.BigDecimal;

public record TransacaoResponseDTO(
        Long numeroConta,
        BigDecimal saldo
) {
}
