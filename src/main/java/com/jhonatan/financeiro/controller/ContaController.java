package com.jhonatan.financeiro.controller;


import com.jhonatan.financeiro.model.Conta;
import com.jhonatan.financeiro.service.ContaService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService){
        this.contaService = contaService;
    }

    @PostMapping
    public Conta criarConta(@RequestBody Conta conta){
        return contaService.criarConta(conta);
    }

    @GetMapping
    public List<Conta> listarContas(){
        return contaService.listarContas();
    }

    @GetMapping("/{id}")
    public Conta buscarPorId(@PathVariable Long id){
        return contaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Conta atualizarConta(@PathVariable Long id, @RequestBody Conta conta ){
        return contaService.atualizarConta(id, conta);
    }

    @DeleteMapping("/{id}")
    public void desativarConta(@PathVariable Long id){
        contaService.desativarConta(id);
    }

    @GetMapping("/{id}/saldo")
    public BigDecimal consultarSaldo(@PathVariable Long id) {
        return contaService.calcularSaldo(id);
    }

}
