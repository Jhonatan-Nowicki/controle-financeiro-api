package com.jhonatan.financeiro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;


@Getter
@Setter
@Entity
@Table(name = "contas")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private TipoConta tipo;

    @PositiveOrZero
    @Column(nullable = false)
    private BigDecimal saldoInicial = BigDecimal.ZERO;

    private Boolean ativo = true;
}
