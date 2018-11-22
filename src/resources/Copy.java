package resources;

public class Copy {
	private int id;
	private int loanDuration;
	private int status = 0;
	//Status meanings:
	//0 = Available
	//1 =

	public Copy(int id, int loanDuration, int status) {
		this.id = id;
		this.loanDuration = loanDuration;
		this.status = status;
	}

	public int getID() {
		return id;
	}

	public int getLoanDuration() {
		return loanDuration;
	}

	public int getStatus() {
		return status;
	}
}
