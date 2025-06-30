package br.com.alura.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Mapeamento as propriedades da classe para campos JSON

//Serialização é a conversão de objetos java para json
//Dessesialização é a conversão de json para objetos java

@JsonIgnoreProperties(ignoreUnknown = true) //Ignora os objetos json que não foram mapeados
public record DadosSerie(@JsonAlias("Title") String title,
                         @JsonAlias("Year") String year,
                         @JsonAlias("Released") String released,
                         @JsonAlias("Runtime") String runtime,
                         @JsonAlias("totalSeasons") Integer totalSeasons) { //Records somente para pegar dados e representar
    //@JsonAlias tem a função de ler o Json  e na hora de escrever ele ultiliza o atributo original

    //@JsonAlias pode ser atribuido uma serie de parametros para serem buscados dentro da requisição

    //@JsonProperty quando é gerado o json ele ira ler e ultilizar o nome original da busca ou parametro
}
