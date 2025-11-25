package sdis.projeto.servicea;

import sdis.projeto.common.*;
import java.net.URI;
import java.net.URISyntaxException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import java.io.IOException;

import com.google.gson.Gson;
import java.lang.Thread;

public class FullTranscript implements Runnable {

    FullTranscriptHandler ftHandler = new FullTranscriptHandler();
    Audio audio = new Audio();
    private final Gson gson = new Gson();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String apiKey = System.getenv("ASSEMBLYAI_API_KEY");
    IdHandler idHandler = new IdHandler(gson, httpClient, apiKey, audio);

    public void generateText(String endpoint){

        HttpRequest getRequest = idHandler.createGetRequest(endpoint);

        while (true) {

            HttpResponse<String> getResponse = idHandler.sendRequest(getRequest);

            ftHandler = gson.fromJson(getResponse.body(), FullTranscriptHandler.class);

            boolean responseCompleted = "completed".equals(ftHandler.getStatus());
            boolean responseError = "error".equals(ftHandler.getStatus());

            if (responseCompleted || responseError) {
                break;
            }

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            
        }

    }

	@Override
	public void run(){

        String endpointPost = "transcript";
        audio.setAudioUrl("https://github.com/johnmarty3/JavaAPITutorial/raw/refs/heads/main/Thirsty.mp4");
        idHandler.generateId(audio, endpointPost);

        String endpointGet = "transcript/" + audio.getId();
        generateText(endpointGet);

        System.out.println(ftHandler.getText());
	} 
}