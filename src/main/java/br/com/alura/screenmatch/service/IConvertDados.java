package br.com.alura.screenmatch.service;

public interface IConvertDados {

    //Referencia antes do metodo significa que você não sabe qual valor ira ser retornado
    //Ira receber um string dados e uma classe não especificada ainda
    <T> T obterDados(String json, Class<T> classe);


}
