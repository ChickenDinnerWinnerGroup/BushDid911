package users;

import java.sql.Date;

public class Librarian extends User {
	private Date employmentDate;
	private String staffNumber;

	public Librarian(String username, String firstName, String lastName, String address, String phoneNumber,
			String profileImage, float balance, String staffNumber, Date employmentDate) {
		super(username, firstName, lastName, address, phoneNumber, profileImage, balance);
		this.staffNumber = staffNumber;
		this.employmentDate = employmentDate;
	}

	@Override
	public boolean isLibrarian() {
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + ", employmentDate = '" + employmentDate.toString() + "', staffNumber = '"
				+ staffNumber + "'";
	}

	public Date getEmploymentDate() {
		return employmentDate;
	}

	public String getStaffNumber() {
		return staffNumber;
	}
}
