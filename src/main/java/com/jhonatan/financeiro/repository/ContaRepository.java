package com.jhonatan.financeiro.repository;

import com.jhonatan.financeiro.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    boolean existsByNome(String nome);
    List<Conta> findByAtivoTrue();
    boolean existsByNomeAndIdNot(String nome, Long id);
}