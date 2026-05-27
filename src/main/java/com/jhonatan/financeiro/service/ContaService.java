package com.jhonatan.financeiro.service;

import com.jhonatan.financeiro.exception.ConflitoException;
import com.jhonatan.financeiro.exception.RecursoNaoEncontradoException;
import com.jhonatan.financeiro.model.Conta;
import com.jhonatan.financeiro.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository){
        this.contaRepository = contaRepository;
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
        Conta conta = buscarPorId(id);
        conta.setAtivo(false);
        contaRepository.save(conta);
    }

}
