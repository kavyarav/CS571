package com.csci571.hw9;

public class Result {
	String poster = "";
	String title = "";
	String year = "";
	String director = "";
	String rating = "";
	String link = "";
	
	public Result(String poster, String title, String year, String director, 
					String rating, String link) {
		this.poster = poster;
		this.title = title;
		this.year = year;
		this.director = director;
		this.rating = rating;
		this.link = link;
	}
	
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	public String getPoster() {
		return poster;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setDiretor(String  dir) {
		director = dir;
	}
	
	public String getDirector() {
		return director;
	}
	
	public void setRating(String rate) {
		rating = rate;
	}
	
	public String getRating() {
		return rating;
	}
	
	public void setLInk(String link) {
		this.link = link;
	}
	
	public String getLink() {
		return link;
	}
	
	public String toString() {
		return "title: " + title + ", year: " + year + ", director: " + director + ", rating: " + rating;
	}
}
