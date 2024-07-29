package br.com.alura.screenmatch.service;


import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {

    public static String obterTraducao(String texto) {
        try {
            String apiKey = "sk-proj-GAGTLJbVBEXeSHG9dSB0T3BlbkFJxcFU14sI1YMgviZbemBU";
            OpenAiService service = new OpenAiService(apiKey);

            CompletionRequest requisicao = CompletionRequest.builder()
                    .model("gpt-3.5-turbo-instruct")
                    .prompt("traduza para o português esse texto: " + texto)
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            var resposta = service.createCompletion(requisicao);
            return resposta.getChoices().get(0).getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao obter tradução: " + e.getMessage();
        }
    }
}
