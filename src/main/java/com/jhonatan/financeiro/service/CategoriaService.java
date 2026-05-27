package com.jhonatan.financeiro.service;

import com.jhonatan.financeiro.exception.ConflitoException;
import com.jhonatan.financeiro.exception.RecursoNaoEncontradoException;
import com.jhonatan.financeiro.model.Categoria;
import com.jhonatan.financeiro.model.TipoCategoria;
import com.jhonatan.financeiro.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;


    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria criarCategoria(Categoria categoria){
        if(categoriaRepository.existsByNome(categoria.getNome())){
            throw new ConflitoException("Já existe uma categoria cadastrada com esse nome.");
        }
        return categoriaRepository.save(categoria);

    }

    public List<Categoria> listarCategorias(){
        return categoriaRepository.findByAtivoTrue();
    }

    public Categoria buscarPorId(Long id){
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
    }

    public List<Categoria> buscarPorTipo(TipoCategoria tipo){
        return categoriaRepository.findByTipo(tipo);
    }

    public Categoria atualizarCategoria(Long id, Categoria categoriaAtualizada){
        Categoria categoriaExistente = buscarPorId(id);
        if(categoriaRepository.existsByNomeAndIdNot(categoriaAtualizada.getNome(), id)){
            throw new ConflitoException("Já existe uma categoria cadastrada com esse nome. ");
        }
        categoriaExistente.setNome(categoriaAtualizada.getNome());
        categoriaExistente.setTipo(categoriaAtualizada.getTipo());

        return categoriaRepository.save(categoriaExistente);
    }
    public void desativarCategoria(Long id){
        Categoria categoria = buscarPorId(id);
        categoria.setAtivo(false);
        categoriaRepository.save(categoria);

    }


}
