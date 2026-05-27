package com.jhonatan.financeiro.controller;


import com.jhonatan.financeiro.model.Categoria;
import com.jhonatan.financeiro.model.TipoCategoria;
import com.jhonatan.financeiro.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }


    @PostMapping
    public Categoria criarCategoria(@RequestBody Categoria categoria){
        return categoriaService.criarCategoria(categoria);
    }

    @GetMapping
    public List<Categoria> listarCategorias(){
        return categoriaService.listarCategorias();
    }

    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Long id){
        return categoriaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Categoria atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria ){
        return categoriaService.atualizarCategoria(id, categoria);
    }

    @DeleteMapping("/{id}")
    public void desativarCategoria(@PathVariable Long id){
        categoriaService.desativarCategoria(id);
    }
    @GetMapping("/tipo/{tipo}")
    public List<Categoria> buscarPorTipo(@PathVariable TipoCategoria tipo){
        return categoriaService.buscarPorTipo(tipo);
    }

}
