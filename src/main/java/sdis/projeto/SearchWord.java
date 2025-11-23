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
import java.lang.Thread;

public class SearchWord implements Runnable {

    public SearchWordHandler getResult() {
        return swHandler;
    }

    SearchWordHandler swHandler = new SearchWordHandler();
    Audio audio = new Audio();
    private final Gson gson = new Gson();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String apiKey = System.getenv("ASSEMBLYAI_API_KEY");
    IdHandler idHandler = new IdHandler(gson, httpClient, apiKey, audio);

    public void generateSearch(String endpoint){

        HttpRequest getRequest = idHandler.createGetRequest(endpoint);

        while (true) {

            HttpResponse<String> getResponse = idHandler.sendRequest(getRequest);

            swHandler = gson.fromJson(getResponse.body(), SearchWordHandler.class);

            if(getResponse.statusCode() == 200){
                insertWordAppearences();
                break;
            }

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }

        }

    }

    public void insertWordAppearences(){

        if(swHandler.matches.size() == 0) {
            System.out.println("Nao existem ocorrencias dessa palavra no audio");
            return;
        }

        for(Match m : swHandler.matches){
            for (double[] ts : m.timestamps) {
                double time = ts[0] / 1000;
                swHandler.wordAppearences.add(time);
                System.out.println("encontrado -> " + time + "s");
            }
        }

    }

	@Override
	public void run(){

        String endpointPost = "transcript";
        audio.setAudioUrl("https://github.com/johnmarty3/JavaAPITutorial/raw/refs/heads/main/Thirsty.mp4");
        idHandler.generateId(audio, endpointPost);

        String word = "me";
        String endpointGet = "transcript/" + audio.getId() + "/word-search?words=" + word;

        generateSearch(endpointGet);

	}

}