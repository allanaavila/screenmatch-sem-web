package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorio;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios 
                    3 - Listar séries buscadas          
                    0 - Sair
                    """;

            System.out.println("\nEscolha a opção deseja: \n" + menu);
            System.out.println("Digite a opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo....");
                    break;

                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i < dadosSerie.totalTemporadas(); i++) {
            var json = consumoApi.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void listarSeriesBuscadas() {
//        List<Serie> series = new ArrayList<>();
//        series = dadosSeries.stream()
//                        .map(d -> new Serie(d))
//                                .collect(Collectors.toList());

        List<Serie> series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}

        //buscando a serie
//        System.out.println("Digite o nome da série para a busca");
//        var nomeSerie = leitura.nextLine();
//        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
//
//        //mostrando total de temporadas
//        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
//        System.out.println(dados);
//
//        //exibindo dados das temporadas
//        List<DadosTemporada> temporadas = new ArrayList<>();
//        if (dados.totalTemporadas() != null) {
//            for (int i = 1; i <= dados.totalTemporadas(); i++) {
//                json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
//                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
//                temporadas.add(dadosTemporada);
//            }
//            temporadas.forEach(System.out::println);
//        } else {
//            System.out.println("Não foi possível obter o número total de temporadas.");
//        }
//
//       /*for(int i = 0; i < dados.totalTemporadas(); i++) {
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//       }*/
//
//       temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
//
//
//       //lista de dadosEpisodios adicionando dados em uma serie
//        List<DadosEpisodio> dadosEpisodios =  temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                .collect(Collectors.toList());   //usar quando for adicionar algo
//                //.toList(); //usando quando não precisar ser adicionado nada
//
//
////        System.out.println("\nTop 10 dos melhores episórios");
////        dadosEpisodios.stream()
////                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
////                .peek(e -> System.out.println("Primeiro filtro qual não é (N/A): " + e))
////                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
////                .peek(e -> System.out.println("Ordenação: " + e))
////                .limit(10)
////                .peek(e -> System.out.println("Limite: " + e))
////                .map(e -> e.titulo().toUpperCase())
////                .peek(e -> System.out.println("Mapeamento: " + e))
////                .forEach(System.out::println);
////
//        List<Episodios> episodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                        .map(d -> new Episodios(t.numero(), d))
//                ).collect(Collectors.toList());
//
//        episodios.forEach(System.out::println);
//
//        //filtrar por titulo da temporada
////        System.out.println("Digite um trecho do título do episódio: ");
////        var trechoTitulo = leitura.nextLine();
////
////        Optional<Episodios> episodioBuscado = episodios.stream()
////                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
////                .findFirst();
////        if(episodioBuscado.isPresent()) {
////            System.out.println("Episódio encontrado!");
////            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
////        } else {
////            System.out.println("Episódio não encontrado!");
////        }
////
////        //filtrar por ano
////        System.out.println("A partir de que ano você deseja ver os episódios? ");
////        var ano = leitura.nextInt();
////        leitura.nextLine();
////
////        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
////        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
////
////        episodios.stream()
////                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
////                .forEach(e -> System.out.println(
////                        "Temporada: " + e.getTemporada() +
////                                " Episódio: " + e.getTitulo() +
////                                " Data de lançamento: " + e.getDataLancamento().format(formatador)
////                ));
//
//        //criar uma avaliacao por temporada
//        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
//                //avaliando somente qual tem uma avaliacao
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.groupingBy(Episodios::getTemporada,
//                        Collectors.averagingDouble(Episodios::getAvaliacao)));
//
//        System.out.println(avaliacoesPorTemporada);
//
//        //resultados por estatistica atraves das avaliacoes
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodios::getAvaliacao));
//        System.out.println("Avaliação estatistica da serie: ");
//        System.out.println("Calculo da média da avaliação: " + est.getAverage());
//        System.out.println("Calculo do melhor episódio: " + est.getMax());
//        System.out.println("Calculo do pior episódio: " + est.getMin());
//        System.out.println("Quantidade de episódio que foram avaliados: " + est.getCount());

