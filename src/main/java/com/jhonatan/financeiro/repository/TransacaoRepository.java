package com.jhonatan.financeiro.repository;

import com.jhonatan.financeiro.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByContaId(Long contaId);

    List<Transacao> findByCategoriaId(Long categoriaId);

    List<Transacao> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

    boolean existsByContaId(Long contaId);

    boolean existsByCategoriaId(Long categoriaId);


}
