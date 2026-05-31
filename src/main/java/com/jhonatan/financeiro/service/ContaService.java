package com.jhonatan.financeiro.service;

import com.jhonatan.financeiro.exception.ConflitoException;
import com.jhonatan.financeiro.exception.RecursoNaoEncontradoException;
import com.jhonatan.financeiro.exception.RegraDeNegocioException;
import com.jhonatan.financeiro.model.Conta;
import com.jhonatan.financeiro.model.TipoTransacao;
import com.jhonatan.financeiro.model.Transacao;
import com.jhonatan.financeiro.repository.ContaRepository;
import com.jhonatan.financeiro.repository.TransacaoRepository;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public ContaService(ContaRepository contaRepository, TransacaoRepository transacaoRepository){
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public Conta criarConta(Conta conta){
        if(contaRepository.existsByNome(conta.getNome())){
            throw new ConflitoException("Já existe uma conta cadastrada com esse nome.");
        }
        return contaRepository.save(conta);
    }

    public List<Conta> listarContas() {
        return contaRepository.findByAtivoTrue();
    }

    public Conta buscarPorId(Long id){
       return contaRepository.findById(id)
               .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada."));

    }
    public Conta atualizarConta(Long id, Conta contaAtualizada){
        Conta contaExistente = buscarPorId(id);
        if(contaRepository.existsByNomeAndIdNot(contaAtualizada.getNome(), id)){
            throw new ConflitoException("Já existe uma conta cadastrada com esse nome.");
        }
        contaExistente.setNome(contaAtualizada.getNome());
        contaExistente.setTipo(contaAtualizada.getTipo());
        contaExistente.setSaldoInicial(contaAtualizada.getSaldoInicial());

        return contaRepository.save(contaExistente);
    }

    public void desativarConta(long id){
        if (transacaoRepository.existsByContaId(id)) {
            throw new RegraDeNegocioException(
                    "Não é permitido excluir uma conta que possui transações vinculadas"
            );
        }
        Conta conta = buscarPorId(id);
        conta.setAtivo(false);
        contaRepository.save(conta);
    }

    public BigDecimal calcularSaldo(Long contaId) {

        Conta conta = buscarPorId(contaId);

        BigDecimal saldo = conta.getSaldoInicial();

        List<Transacao> transacoes =
                transacaoRepository.findByContaId(contaId);

        for (Transacao transacao : transacoes) {

            if (transacao.getTipo() == TipoTransacao.RECEITA) {
                saldo = saldo.add(transacao.getValor());
            } else {
                saldo = saldo.subtract(transacao.getValor());
            }
        }

        return saldo;
    }

}
