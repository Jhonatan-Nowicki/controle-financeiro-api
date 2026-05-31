package com.jhonatan.financeiro.service;

import com.jhonatan.financeiro.exception.RegraDeNegocioException;
import com.jhonatan.financeiro.repository.CategoriaRepository;
import com.jhonatan.financeiro.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void deveLancarErroAoDesativarCategoriaComTransacoes() {

        when(transacaoRepository.existsByCategoriaId(1L))
                .thenReturn(true);

        assertThrows(
                RegraDeNegocioException.class,
                () -> categoriaService.desativarCategoria(1L)
        );
    }
}