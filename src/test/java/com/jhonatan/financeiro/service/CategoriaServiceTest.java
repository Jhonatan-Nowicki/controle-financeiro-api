package com.jhonatan.financeiro.service;

import com.jhonatan.financeiro.exception.RegraDeNegocioException;
import com.jhonatan.financeiro.model.Categoria;
import com.jhonatan.financeiro.model.TipoCategoria;
import com.jhonatan.financeiro.repository.CategoriaRepository;
import com.jhonatan.financeiro.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

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
    @Test
    void deveCriarCategoriaComSucesso() {

        Categoria categoria = new Categoria();
        categoria.setNome("Mercado");
        categoria.setTipo(TipoCategoria.DESPESA);
        categoria.setAtivo(true);

        when(categoriaRepository.existsByNome("Mercado"))
                .thenReturn(false);

        when(categoriaRepository.save(any(Categoria.class)))
                .thenReturn(categoria);

        Categoria resultado = categoriaService.criarCategoria(categoria);

        assertEquals("Mercado", resultado.getNome());
        assertEquals(TipoCategoria.DESPESA, resultado.getTipo());
    }

}