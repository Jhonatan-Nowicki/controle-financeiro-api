package com.jhonatan.financeiro.controller;

import com.jhonatan.financeiro.dto.ResumoFinanceiroDTO;
import com.jhonatan.financeiro.dto.TransacaoRequestDTO;
import com.jhonatan.financeiro.model.Transacao;
import com.jhonatan.financeiro.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @GetMapping
    public List<Transacao> listarTransacoes() {
        return transacaoService.listarTransacoes();
    }

    @GetMapping("/{id}")
    public Transacao buscarPorId(@PathVariable Long id) {
        return transacaoService.buscarPorId(id);
    }

    @GetMapping("/conta/{contaId}")
    public List<Transacao> buscarPorConta(@PathVariable Long contaId) {
        return transacaoService.buscarPorConta(contaId);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Transacao> buscarPorCategoria(@PathVariable Long categoriaId) {
        return transacaoService.buscarPorCategoria(categoriaId);
    }

    @GetMapping("/periodo")
    public List<Transacao> buscarPorPeriodo(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {

        return transacaoService.buscarPorPeriodo(dataInicio, dataFim);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transacao criarTransacao(
            @Valid @RequestBody TransacaoRequestDTO dto) {

        return transacaoService.criarTransacao(dto);
    }

    @PutMapping("/{id}")
    public Transacao atualizarTransacao(
            @PathVariable Long id,
            @Valid @RequestBody TransacaoRequestDTO dto) {

        return transacaoService.atualizarTransacao(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirTransacao(@PathVariable Long id) {
        transacaoService.excluirTransacao(id);
    }

    @GetMapping("/resumo")
    public ResumoFinanceiroDTO obterResumo(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {

        return transacaoService.obterResumo(dataInicio, dataFim);
    }




}
