package com.optum.model;

public class FavouriteModel {

	private String title;
	private String year;
	private String rated;
	private String released;
	private String director;
	private String writer;
	private String actors;
	private String language;
	private String country;
	private String poster;

	public FavouriteModel() {
		super();
	}

	public FavouriteModel(String title, String year, String rated, String released, String director, String writer,
			String actors, String language, String country, String poster) {
		super();
		this.title = title;
		this.year = year;
		this.rated = rated;
		this.released = released;
		this.director = director;
		this.writer = writer;
		this.actors = actors;
		this.language = language;
		this.country = country;
		this.poster = poster;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRated() {
		return rated;
	}

	public void setRated(String rated) {
		this.rated = rated;
	}

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	@Override
	public String toString() {
		return "FavouriteModel [title=" + title + ", year=" + year + ", rated=" + rated + ", released=" + released
				+ ", director=" + director + ", writer=" + writer + ", actors=" + actors + ", language=" + language
				+ ", country=" + country + ", poster=" + poster + "]";
	}

}
