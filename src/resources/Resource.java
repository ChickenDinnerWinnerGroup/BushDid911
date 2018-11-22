package resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Resource {
	protected int resID;
	protected String title;
	protected int year;
	protected String thumbnail;
	protected List<String> reserveQueue;
	protected ArrayList<Copy> copies;

	protected Resource (int id, String title, int year, String thumbnail,
			ArrayList<Copy> copies, String[] reserveQueue) {
		this.resID = id;
		this.title = title;
		this.year = year;
		this.thumbnail = thumbnail;
		this.copies = copies;
		this.reserveQueue = Arrays.asList(reserveQueue);
	}

	public int getID() {
		return resID;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public ArrayList<Copy> getCopies() {
		return copies;
	}

	public List<String> getReserveQueue() {
		return reserveQueue;
	}

	public void addToReserveQueue(String username) {
		if(!reserveQueue.contains(username.toLowerCase())) {
			reserveQueue.add(username);
		} else {
			System.out.println("Unable to add user(username: "+username+") to resource(id: "+getID()+")");
		}
	}

	@Override
	public String toString() {
		String copies = "";
		for(Copy copy : getCopies()) {
			copies += copy.getID()+"-"+copy.getLoanDuration()+"-"+copy.getStatus()+"@";
		}
		String reserveQueue = "";
		for(String user : getReserveQueue()) {
			reserveQueue += user+";";
		}
		String resourceString = "id = '"+this.resID+"', title = '"+this.title+"', thumbnail = '"+this.thumbnail+"', year = '"+
				this.year+"', copies = '"+copies+"', reserveQueue = '"+reserveQueue+"'";

		return resourceString;
	}
}

























