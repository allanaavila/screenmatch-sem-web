package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodios;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    //buscando por total e temporadas e com avaliacao
//    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual (
//            Integer totalTemporadas, Double avaliacao);

    //buscando por total e temporadas e com avaliacao atraves do banco JPQL
    @Query("SELECT s FROM Serie s WHERE " +
            "s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao")
    List<Serie> seriePorTemporadasEAvaliacao(Integer totalTemporadas, Double avaliacao);

    //listar trecho de um episodio
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodios> episodiosPorTrecho(String trechoEpisodio);

    //listando por episodio por serie
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodios> topEpisodiosPorSerie(Serie serie);
}
