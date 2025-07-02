package br.com.alura.screenmatch;

import br.com.alura.screenmatch.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner { //Permite a criação de linhas de comando dentro do metodo main

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal();
		principal.exibeMenu();

		/*var consumo = new ConsumoAPI();
		var json = consumo.obterDados("https://www.omdbapi.com/?t=suits&apikey=e1a1dcbe");
		var jsonEp = consumo.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=e1a1dcbe");

		*//**//*System.out.println(json);
		json = consumo.obterDados("https://coffee.alexflipnote.dev/random.json");*//**//*
		System.out.println("\n" + json + "\n");

		//Estrutura de modelagem de dados
		ConvertDados conversor = new ConvertDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

		System.out.println(dados +"\n");

		//Aplicando o generics na classe de dados do episodio
		DadosEpisodio dadosEpisodio = conversor.obterDados(jsonEp, DadosEpisodio.class);

		System.out.println(dadosEpisodio + "\n");*//*

		List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i = 1; i<= dados.totalSeasons(); i++){
			jsonEp = consumo.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=e1a1dcbe");
			DadosTemporada dadosTemporada = conversor.obterDados(jsonEp, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);*/

	}
}
