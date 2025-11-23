package sdis.projeto;

import java.net.URI;
import java.net.URISyntaxException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import java.io.IOException;

import com.google.gson.Gson;

public class IdHandler{

	private final Gson gson;
	private final HttpClient httpClient;
    private final String apiKey;
    private Audio audio;

    public IdHandler(Gson gson, HttpClient httpClient, String apiKey, Audio audio){
    	this.gson = gson;
    	this.httpClient = httpClient;
    	this.apiKey = apiKey;
    	this.audio = audio;
    }

	public void generateId(Audio audio, String endpoint){

		String jsonRequest = gson.toJson(audio);
		HttpRequest postRequest = createPostRequest(endpoint, jsonRequest);
        HttpResponse<String> postResponse = sendRequest(postRequest);

        Audio result = gson.fromJson(postResponse.body(), Audio.class);

        if (result == null || result.getId() == null) {
            throw new IllegalStateException("API não retornou ID válido");
        }

        audio.setId(result.getId());

    }

	public HttpRequest createPostRequest(String endpoint, String jsonRequest){
        String apiKey = System.getenv("API_KEY");
		try{
			return HttpRequest.newBuilder()
	            .uri(new URI("https://api.assemblyai.com/v2/" + endpoint))
	            .header("Authorization", "609928dcd52c422e8b8ce37cc020a4ea")
	            .header("Content-Type", "application/json")
	            .POST(BodyPublishers.ofString(jsonRequest))
	            .build();
	     } catch (URISyntaxException e){
	     	throw new RuntimeException("Id gerado inválido: " + endpoint, e);
	     }
	}

	public HttpRequest createGetRequest(String endpoint) {
        try {
            return HttpRequest.newBuilder()
                .uri(new URI("https://api.assemblyai.com/v2/" + endpoint))
                .header("Authorization", "609928dcd52c422e8b8ce37cc020a4ea")
                .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("URI inválida: " + endpoint, e);
        }
    }

	public HttpResponse<String> sendRequest(HttpRequest request) {
	    try {
	        return httpClient.send(request, BodyHandlers.ofString());
	    } catch (IOException e) {
	        throw new RuntimeException("Erro de comunicação com a API: " + e.getMessage(), e);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        throw new RuntimeException("Requisição interrompida: " + e.getMessage(), e);
	    }
	}

}