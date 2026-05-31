package com.jhonatan.financeiro.dto;

import java.time.LocalDateTime;

public record ErroRespostaDTO(
        int codigo,
        String mensagem,
        LocalDateTime timestamp
) {
}