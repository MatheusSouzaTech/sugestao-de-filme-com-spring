package br.com.alura.screenmatch;

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
		System.out.println("Projeto Spring sem web");
	}
}
