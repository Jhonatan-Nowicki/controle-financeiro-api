package com.jhonatan.financeiro.repository;

import com.jhonatan.financeiro.model.Categoria;
import com.jhonatan.financeiro.model.TipoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNome(String nome);
    List<Categoria> findByAtivoTrue();
    boolean existsByNomeAndIdNot(String nome, Long id);
    List<Categoria> findByTipo(TipoCategoria tipo);

}
