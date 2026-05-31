package com.jhonatan.financeiro.dto;

import com.jhonatan.financeiro.model.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoRequestDTO(

        @NotBlank
        String descricao,

        @NotNull
        @Positive
        BigDecimal valor,

        @NotNull
        TipoTransacao tipo,

        @NotNull
        @PastOrPresent
        LocalDate data,

        @NotNull
        Long contaId,

        @NotNull
        Long categoriaId,

        String observacao
) {
}