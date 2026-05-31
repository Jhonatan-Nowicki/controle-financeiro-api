package com.jhonatan.financeiro.service;

import com.jhonatan.financeiro.exception.ConflitoException;
import com.jhonatan.financeiro.exception.RecursoNaoEncontradoException;
import com.jhonatan.financeiro.exception.RegraDeNegocioException;
import com.jhonatan.financeiro.model.Categoria;
import com.jhonatan.financeiro.model.TipoCategoria;
import com.jhonatan.financeiro.repository.CategoriaRepository;
import com.jhonatan.financeiro.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final TransacaoRepository transacaoRepository;


    public CategoriaService(CategoriaRepository categoriaRepository, TransacaoRepository transacaoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.transacaoRepository = transacaoRepository;
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
        if (transacaoRepository.existsByCategoriaId(id)) {
            throw new RegraDeNegocioException(
                    "Não é permitido excluir uma categoria que possui transações vinculadas"
            );
        }
        Categoria categoria = buscarPorId(id);
        categoria.setAtivo(false);
        categoriaRepository.save(categoria);

    }


}
