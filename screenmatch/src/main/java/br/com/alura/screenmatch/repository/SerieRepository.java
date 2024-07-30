package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//criando consultas dinamicas
public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    //buscando atores atraves da avaliacao
    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(
            String nomeAtor,
            Double avaliacao);

    //ordenar e colocar em formato decrescente
    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    //buscar por categoria
    List<Serie> findByGenero(Categoria categoria);
}
