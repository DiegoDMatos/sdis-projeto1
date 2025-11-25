package sdis.projeto.common;

public class Audio{
	
	private String language_code = "pt";
	private String audio_url;
	private String id;

	public void setAudioUrl(String audio_url){
		this.audio_url = audio_url;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getLanguageCode(){
		return language_code;
	}

	public void setLanguageCode(String language_code){
		this.language_code = language_code;
	}

}