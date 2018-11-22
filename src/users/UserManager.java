package users;

import java.util.ArrayList;

public interface UserManager {
	public boolean authenticate(String username);
	public void createUser(User u);
	public void deleteUser(String username);
	public User retriveUser(String username);
	public void updateUser(String username, User u);
	public ArrayList<User> getUsers();
	public boolean checkAuthorised();
}
