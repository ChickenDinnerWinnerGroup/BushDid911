package users;

import java.util.ArrayList;

public interface UserManager {
	/**
	 *
	 * @param username  Username of the user the authenticate.
	 * @return          <ul><li><code>true</code>: if there is a user
     *                  in the database with the supplied username.</li>
     *                  <li><code>false</code>: if there is no user
     *                  in the database with the supplied username parameter.</li></ul>
	 */
	public boolean authenticate(String username);
	/**
	 *
	 * @param u
	 */
	public void createUser(User u);
	/**
	 *
	 * @param username
	 */
	public void deleteUser(String username);
	/**
	 *
	 * @param username
	 * @return
	 */
	public User retrieveUser(String username);
	/**
	 *
	 * @param username
	 * @param u
	 */
	public void updateUser(String username, User u);
	/**
	 *
	 * @return
	 */
	public ArrayList<User> getUsers();
	/**
	 *
	 * @return          <ul><li><code>true</code>: if there is a user
     *                  currently authenticated and using the system.</li>
     *                  <li><code>false</code>: if there is no user
     *                  currently authenticated and using the system.</li></ul>
	 */
	public boolean checkAuthorised();
}
