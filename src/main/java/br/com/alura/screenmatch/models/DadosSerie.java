package br.com.alura.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;

//Mapeamento do Json para classe

public record DadosSerie(@JsonAlias("Title") String title,
                         @JsonAlias("Year") String year,
                         @JsonAlias("Released") String released,
                         @JsonAlias("Runtime") String runtime,
                         @JsonAlias("totalSeasons") Integer totalSeasons) { //Records somente para pegar dados e representar
    //@JsonAlias tem a função de ler o Json  e na hora de escrever ele ultiliza o atributo original

    //@JsonAlias pode ser atribuido uma serie de parametros para serem buscados dentro da requisição

    //@JsonProperty quando é gerado o json ele ira ler e ultilizar o nome original da busca ou parametro
}
