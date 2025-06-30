package br.com.alura.screenmatch;

import br.com.alura.screenmatch.service.ConsumoAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sound.sampled.LineListener;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner { //Permite a criação de linhas de comando dentro do metodo main

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumo = new ConsumoAPI();
		var json = consumo.obterDados("http://www.omdbapi.com/?t=suits&apikey=e1a1dcbe");
		/*System.out.println(json);
		json = consumo.obterDados("https://coffee.alexflipnote.dev/random.json");*/
		System.out.println(json);

	}
}
