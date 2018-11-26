import java.util.LinkedList;
import resources.*;
import users.*;
import java.util.Queue;

public class issueDesk 
{
	private Queue<Resource> requests = new LinkedList<>();
	private User userObject;
	
	public issueDesk (User userObject)
	{
		this.userObject = userObject;
	}
	
	public void returnCopy(Resource item)
	{
		
	}
	
	public void payFine (float payment)
	{
		this.userObject.subtractBalance(payment);
	}
	
	public void issueCopy (Resource item)
	{
		
	} 
	//g
}
