package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.models.DadosEpisodio;
import br.com.alura.screenmatch.models.DadosSerie;
import br.com.alura.screenmatch.models.DadosTemporada;
import br.com.alura.screenmatch.models.Episodio;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConvertDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner sc = new Scanner(System.in);

    private final String ENDERECO = "https://www.omdbapi.com/?t="; //final as variaveis não terão um valor modificado no futuro
    private final String APIKEY = "&apikey=e1a1dcbe";

    private ConsumoAPI consumo = new ConsumoAPI();
    private ConvertDados conversor = new ConvertDados();

    public void exibeMenu(){
        System.out.println("Digite o nome da serie para busca: ");
        var nomeSerie = sc.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + APIKEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for(int i = 1; i<= dados.totalSeasons(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + APIKEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        temporadas.forEach(System.out::println);

        /*for(int i = 0;i< dados.totalSeasons(); i++){ //Usando a matrix bidimensional para coletar e armazenar todos os episodios das temporadas
            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios(); //Armazenando os episodios dentro de uma lista
            for (int j = 0; j<episodiosTemporada.size();j++){
                System.out.println(episodiosTemporada.get(j).titulo()); //Exibir os dados dos episodios
            }
        }*/

        //lambdas trabalhando com coleções se ultilizam parametros para acessar e manipular os dados das coleções
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo()))); //simplificando o array bidimensional

        //Quando você tem um paramentro e uma função com esse parametro podemos ultilizar os 4 pontinhos
        temporadas.forEach(System.out::println);

        // temporadas((parametros) -> expressao); cada um dos parametro implementados  são a representação de um episodio


        //Mexendo com streams

       /* //Streams: Streams em Java são uma forma de processar coleções de dados de maneira declarativa e funcional.
        List<String> nomes = Arrays.asList("Matheus", "Rodrigo","Fernando", "Paulo");

        nomes.stream()
                .sorted() //gera um novo fluxo de dados onde eu posso realizar uma nova ação em cima deste fluxo (operação intermediaria)
                .limit(3) //realizando uma operação sobre a outra
                .filter(n -> n.startsWith("P"))
                .map(String::toUpperCase)
                .forEach(System.out::println); //operação final*/

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList()); // Permite que você modifique essa lista, caso não precise use o tolist

        System.out.println("\nTop 10 melhores episodios: ");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro filtro(N/A)" + e)) //.peek() permite "espiar" os elementos de um stream enquanto ele é processado, sem modificar os elementos.
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenação" + e))
                .limit(10)
                .peek(e -> System.out.println("Definição do limite de resposta" + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Mapeamento " + e))
                .forEach(System.out::println);

        dadosEpisodios.forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.temporada(),d))).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episodios? ");
        var ano = sc.nextInt();
        sc.nextLine();

        LocalDate dataBusca = LocalDate.of(ano,1,1);


        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episodio: " + e.getTitulo() +
                                " Data lançamento: " + e.getDataLancamento().format(formatador)
                ));

        System.out.println("Digite um trecho do titulo do episodio: ");

        var trechoTitulo = sc.nextLine();

        // é um objeto container que pode ou não conter um valor não nulo
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst(); //Encontra a primeira referencia que esteja buscando

        if (episodioBuscado.isPresent())//isPresente verifica que se o valor é existente
        {
            System.out.println("Episodio Encontrado!");
            System.out.println("Temporada: " + episodioBuscado.get());
        }
        else {
            System.out.println("Episodio não encontrado!");
        }


        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)                      // mantém apenas episódios avaliados
                .collect(Collectors.groupingBy(
                        Episodio::getTemporada,                               // chave: temporada
                        Collectors.averagingDouble(Episodio::getAvaliacao)    // valor: média das notas
                ));

        System.out.println(avaliacoesPorTemporada);                   // imprime o resultado
    }
}
