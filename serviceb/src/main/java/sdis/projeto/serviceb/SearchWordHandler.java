package sdis.projeto.serviceb;

import java.util.ArrayList;

public class SearchWordHandler{

    private int total_count;
    ArrayList<Match> matches = new ArrayList<>();
    ArrayList<Double> wordAppearences = new ArrayList<>();
    private String result;

    public int getTotalCount(){
    	return total_count;
    }

    public void setResult(String result){
        this.result = result;
    }

    public String getResult(){
        return result;
    }

}