package com.jhonatan.financeiro.service;

import com.jhonatan.financeiro.repository.ContaRepository;
import com.jhonatan.financeiro.repository.TransacaoRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.jhonatan.financeiro.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private ContaService contaService;

    @Test
    void deveCalcularSaldoCorretamente() {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setNome("Carteira");
        conta.setTipo(TipoConta.CARTEIRA);
        conta.setSaldoInicial(new BigDecimal("100.00"));
        conta.setAtivo(true);

        Transacao receita = new Transacao();
        receita.setTipo(TipoTransacao.RECEITA);
        receita.setValor(new BigDecimal("2500.00"));

        Transacao despesa = new Transacao();
        despesa.setTipo(TipoTransacao.DESPESA);
        despesa.setValor(new BigDecimal("200.00"));

        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(transacaoRepository.findByContaId(1L)).thenReturn(List.of(receita, despesa));

        BigDecimal saldo = contaService.calcularSaldo(1L);

        assertEquals(new BigDecimal("2400.00"), saldo);
    }

}
