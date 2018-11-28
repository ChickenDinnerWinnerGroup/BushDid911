package users;

import application.Manager;

public class User {
	private String firstName;
	private String lastName;
	private String username;
	private String address;
	private String profileImage;
	private float balance = 0.0f;
	private String phoneNumber;

	public User(String username, String firstName, String lastName, String address, String phoneNumber,
			String profileImage, float balance) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.profileImage = profileImage;
		this.balance = balance;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public float getBalance() {
		return balance;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setAddress(String address) {
		this.address = address;
		Manager.getInstance().updateUser(username, this);
	}

	public void setBalance(float balance) {
		this.balance = balance;
		Manager.getInstance().updateUser(username, this);
	}

	public boolean isLibrarian() {
		return false;
	}

	@Override
	public String toString() {
		String userString = "username = '" + username + "', firstname = '" + firstName + "'" + ", address = '" + address
				+ "', phoneNumber = '" + phoneNumber + "', profileImage = '" + profileImage + "'" + ", balance = '"
				+ balance + "'";
		return userString;
	}
}
