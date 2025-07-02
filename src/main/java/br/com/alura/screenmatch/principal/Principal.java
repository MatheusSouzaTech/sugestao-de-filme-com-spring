package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.models.DadosSerie;
import br.com.alura.screenmatch.models.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConvertDados;

import java.util.ArrayList;
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
    }


}
