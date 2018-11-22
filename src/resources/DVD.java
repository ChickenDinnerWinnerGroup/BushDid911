package resources;

import java.util.ArrayList;

public class DVD extends Resource {
	private String director;
	private int runTime;
	private String language;
	private String[] subtitleLangs;

	//Takes: int id, String title, int year, String thumbnail,
	//String director,
	//String runTime, String language, String[] subtitleLangs
	public DVD(int id, String title, int year, String thumbnail, String director, int runtime,
			String language, String[] subtitleLangs, ArrayList<Copy> copies, String[] reserveQueue) {
		super (id, title, year, thumbnail, copies, reserveQueue);
		this.director = director;
		this.runTime = runtime;
		this.language = language;
		this.subtitleLangs = subtitleLangs;
	}

	public String getDirector() {
		return director;
	}

	public int getRunTime() {
		return runTime;
	}

	public String getLanguage() {
		return language;
	}

	public String[] getSubtitleLangs() {
		return subtitleLangs;
	}


	@Override
	public String toString() {
		String subtitles = "";
		for(String subtitle : subtitleLangs) {
			subtitles += subtitle+";";
		}
		String dvdString = super.toString() + ", director = '"+this.director+"', runtime = '"+this.runTime+"', language = '"
				+this.language+"', subtitles = '"+subtitles+"'";
		return dvdString;
	}
}

