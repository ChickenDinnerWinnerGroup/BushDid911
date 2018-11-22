package users;

public class User {
	private String firstName;
	private String lastName;
	private String username;
	private String address;
	private String profileImage;
	private float balance = 0.0f;

	public User(String username, String firstName, String lastName, String address, String profileImage) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.profileImage = profileImage;
	}

	public User(String username, String firstName, String lastName, String address, String profileImage, float balance) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.profileImage = profileImage;
		this.balance  = balance;
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

	public void setAddress(String address) {
		this.address = address;
		// TODO Implement database code (basically write query and call the user function)
	}

	public boolean isLibrarian() {
		return false;
	}

	@Override
	public String toString() {
		String userString = "username = '"+username+"', firstname = '"+firstName+"'"+", address = '"
				+address+"', profileImage = '"+profileImage+"'"+", balance = '"+balance+"'";
		return userString;
	}
}
