package br.com.joaopedro.objective.dto;

import br.com.joaopedro.objective.model.PagamentoTipo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransacaoRequestDTO(
        @NotNull(message = "A forma de pagamento é obrigatória")
        PagamentoTipo forma_pagamento,
        @NotNull(message = "O número da conta é obrigatório")
        @Positive(message = "O número da conta deve ser positivo")
        Long numero_conta,
        @NotNull(message = "O valor da transação é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor da transação deve ser maior que zero")
        BigDecimal valor
) {
}
