package sdis.projeto;

public class App {

    public static void main(String[] args) throws InterruptedException {

        Thread fullTranscript = new Thread(new FullTranscript());
        Thread searchWord = new Thread(new SearchWord());

        fullTranscript.start();
        searchWord.start();

        fullTranscript.join();
        searchWord.join();


    }
}
