package resources;

import java.util.ArrayList;

public class Book extends Resource {
	private String author;
	private String publisher;
	private String genre;
	private String isbn;
	private String language;

	public Book (int id, String title, int year, String thumbnail, String auth, String pub,
			String genre, String isbn, String lang, ArrayList<Copy> copies, String[] reserveQueue) {
		super (id, title, year, thumbnail, copies, reserveQueue);
		this.author = auth;
		this.publisher = pub;
		this.genre = genre;
		this.isbn = isbn;
		this.language = lang;
	}

	public String getAuthor() {
		return author;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getGenre() {
		return genre;
	}

	public String getISBN() {
		return isbn;
	}

	public String getLanguage() {
		return language;
	}

	@Override
	public String toString() {
		String bookString = super.toString() + ", author = '"+this.author+"', genre = '"+this.genre+"', language = '"
				+this.language+"', isbn = '"+this.isbn+"', publisher = '"+this.publisher+"'";
		return bookString;
	}
}
