package br.com.alura.screenmatch.service;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Traducao (@JsonAlias(value = "responseData")
                        DadosResposta dadosResposta){

}
