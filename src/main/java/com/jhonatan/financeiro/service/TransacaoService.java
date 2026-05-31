package com.jhonatan.financeiro.service;

import com.jhonatan.financeiro.dto.ResumoFinanceiroDTO;
import com.jhonatan.financeiro.dto.TransacaoRequestDTO;
import com.jhonatan.financeiro.exception.RecursoNaoEncontradoException;
import com.jhonatan.financeiro.exception.RegraDeNegocioException;
import com.jhonatan.financeiro.model.Categoria;
import com.jhonatan.financeiro.model.Conta;
import com.jhonatan.financeiro.model.TipoTransacao;
import com.jhonatan.financeiro.model.Transacao;
import com.jhonatan.financeiro.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaService contaService;
    private final CategoriaService categoriaService;

    public Transacao criarTransacao(TransacaoRequestDTO dto) {

        Conta conta = contaService.buscarPorId(dto.contaId());

        Categoria categoria = categoriaService.buscarPorId(dto.categoriaId());

        if (!dto.tipo().name().equals(categoria.getTipo().name())) {
            throw new RegraDeNegocioException(
                    "O tipo da transação deve ser compatível com o tipo da categoria"
            );
        }

        Transacao transacao = new Transacao();

        transacao.setDescricao(dto.descricao());
        transacao.setValor(dto.valor());
        transacao.setTipo(dto.tipo());
        transacao.setData(dto.data());
        transacao.setConta(conta);
        transacao.setCategoria(categoria);
        transacao.setObservacao(dto.observacao());

        return transacaoRepository.save(transacao);
    }

    public List<Transacao> listarTransacoes() {
        return transacaoRepository.findAll();
    }

    public Transacao buscarPorId(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transação não encontrada"));
    }

    public List<Transacao> buscarPorConta(Long contaId) {
        contaService.buscarPorId(contaId);
        return transacaoRepository.findByContaId(contaId);
    }

    public List<Transacao> buscarPorCategoria(Long categoriaId) {
        categoriaService.buscarPorId(categoriaId);
        return transacaoRepository.findByCategoriaId(categoriaId);
    }

    public List<Transacao> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return transacaoRepository.findByDataBetween(dataInicio, dataFim);
    }

    public Transacao atualizarTransacao(Long id, TransacaoRequestDTO dto) {
        Transacao transacao = buscarPorId(id);

        Conta conta = contaService.buscarPorId(dto.contaId());
        Categoria categoria = categoriaService.buscarPorId(dto.categoriaId());

        if (!dto.tipo().name().equals(categoria.getTipo().name())) {
            throw new RegraDeNegocioException(
                    "O tipo da transação deve ser compatível com o tipo da categoria"
            );
        }

        transacao.setDescricao(dto.descricao());
        transacao.setValor(dto.valor());
        transacao.setTipo(dto.tipo());
        transacao.setData(dto.data());
        transacao.setConta(conta);
        transacao.setCategoria(categoria);
        transacao.setObservacao(dto.observacao());

        return transacaoRepository.save(transacao);
    }

    public void excluirTransacao(Long id) {
        Transacao transacao = buscarPorId(id);
        transacaoRepository.delete(transacao);
    }

    public ResumoFinanceiroDTO obterResumo(
            LocalDate dataInicio,
            LocalDate dataFim) {

        List<Transacao> transacoes =
                transacaoRepository.findByDataBetween(dataInicio, dataFim);

        BigDecimal totalReceitas = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;

        for (Transacao transacao : transacoes) {

            if (transacao.getTipo() == TipoTransacao.RECEITA) {
                totalReceitas = totalReceitas.add(transacao.getValor());
            } else {
                totalDespesas = totalDespesas.add(transacao.getValor());
            }
        }

        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        return new ResumoFinanceiroDTO(
                totalReceitas,
                totalDespesas,
                saldo
        );
    }

}
