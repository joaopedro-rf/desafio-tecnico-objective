package br.com.joaopedro.objective.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ContaRequestDTO(
        @NotNull(message = "O número da conta é obrigatório")
        @Positive(message = "O número da conta deve ser positivo")
        Long numero_conta,
        @NotNull(message = "O saldo é obrigatório")
        @DecimalMin(value = "0.00", inclusive = true, message = "O saldo inicial não pode ser negativo")
        BigDecimal saldo
) {
}
