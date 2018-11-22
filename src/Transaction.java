
import java.sql.Date;

public class Transaction {
	private int id;
	private String username;
	private int resourceId;
	private int copyId;
	private Date dateTaken;
	private Date dueDate;
	private int status;

	public Transaction(int id, String username, int resourceId, int copyId, Date dateTaken, Date dueDate, int status) {
		this.id = id;
		this.username = username;
		this.resourceId = resourceId;
		this.copyId = copyId;
		this.dateTaken = dateTaken;
		this.dueDate = dueDate;
		this.status = status;
	}

	public int getID() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public int getResourceID() {
		return resourceId;
	}

	public int getCopyID() {
		return copyId;
	}

	public Date getDateTaken() {
		return dateTaken;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
