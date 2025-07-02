package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.models.DadosEpisodio;
import br.com.alura.screenmatch.models.DadosSerie;
import br.com.alura.screenmatch.models.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConvertDados;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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


    }


}
