package com.jhonatan.financeiro.service;

import com.jhonatan.financeiro.repository.TransacaoRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jhonatan.financeiro.dto.TransacaoRequestDTO;
import com.jhonatan.financeiro.exception.RegraDeNegocioException;
import com.jhonatan.financeiro.model.*;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ContaService contaService;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private TransacaoService transacaoService;

    @Test
    void deveLancarErroQuandoTipoTransacaoForDiferenteDoTipoCategoria() {

        Conta conta = new Conta();
        conta.setId(1L);

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setTipo(TipoCategoria.RECEITA);

        TransacaoRequestDTO dto = new TransacaoRequestDTO(
                "Mercado",
                new BigDecimal("100.00"),
                TipoTransacao.DESPESA,
                LocalDate.now(),
                1L,
                1L,
                "Teste"
        );

        when(contaService.buscarPorId(1L)).thenReturn(conta);
        when(categoriaService.buscarPorId(1L)).thenReturn(categoria);

        assertThrows(
                RegraDeNegocioException.class,
                () -> transacaoService.criarTransacao(dto)
        );
    }

}