package resources;

import java.util.ArrayList;

public class Laptop extends Resource {

	private String manufacturer;
	private String model;
	private String operatingSys;

	public Laptop (int id, String title, int year, String thumbnail, String manufacturer,
			String model, String operatingSys, ArrayList<Copy> copies, String[] reserveQueue) {
		super (id, title, year, thumbnail, copies, reserveQueue);
		this.manufacturer = manufacturer;
		this.model = model;
		this.operatingSys = operatingSys;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getModel() {
		return model;
	}

	public String getOS() {
		return operatingSys;
	}

	@Override
	public String toString() {
		String laptopString = super.toString() + ", manufacturer = '"+this.manufacturer+"', model = '"+this.model+"', os = '"+
				this.operatingSys+"'";
		return laptopString;
	}
}
