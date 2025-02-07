package br.com.joaopedro.objective.exception;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        String message,
        int status,
        LocalDateTime timestamp,
        String path
) {}
