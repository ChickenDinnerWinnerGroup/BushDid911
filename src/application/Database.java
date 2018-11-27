package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Mohamed Ashaibani
 * @version     1.1, 23/11/2018
 * @since       1.0
 */
public class Database {

	private Connection connection;

	/**
	 * Database class constructor
	 *
	 * @param filename The filename of the sqlite database that the application will work with.
	 *
	 */
	public Database(String filename) {
		try {
			createConnection(filename);
		} catch (SQLException e) {
			System.out.println("A connection to the database could not be created.");
			e.printStackTrace();
		}
	}

	/**
	 * Creates a connection to the database based on the fileName parameter
	 *
	 * @param filename The filename of the sqlite database that the connection will be opened to.
	 * @exception SQLException If an error occurs attempting to create a connection to the database.
	 */
	private void createConnection(String filename) throws SQLException {
		String url = "jdbc:sqlite:" + filename;

        Connection conn = DriverManager.getConnection(url);
        if (conn != null) {

        	System.out.println("A connection to the database has been created.");
        	this.connection = conn;
        }
	}

	/**
	 * Executes a sql query that generates a set of results.
	 *
	 * @param sql The sql statement that is going to be executed.
	 * @return A ResultSet containing the results of the executed sql statement.
	 * @exception SQLException If an error occurs attempting to create a connection to the database.
	 *
	 * @see ResultSet
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		Statement statement = connection.createStatement();
		System.out.println("Executing sql query: "+sql);
		ResultSet results = statement.executeQuery(sql);
		return results;
	}

	/**
	 * Executes a sql query that is not expected to return a set of results.
	 *
	 * @param sql The sql statement that is going to be executed.
	 * @exception SQLException If an error occurs attempting to create a connection to the database.
	 */
	public void executeUpdate(String sql) throws SQLException {
		Statement statement = connection.createStatement();

		System.out.println("Executing sql query: "+sql);
		statement.executeUpdate(sql);
	}

	/* for testing purposes
	public static void main(String[] args) {
		Manager m = new Manager();
		ArrayList<Copy> copies = new ArrayList<Copy>();
		copies.add(new Copy(1, 1, 0));
		copies.add(new Copy(2, 7, 0));
		copies.add(new Copy(3, 14, 0));

		//String[] reserveQueue = new String[] {"testuser 1", "test user 2"};

		//Book book = new Book(1, "test book", 2018, "2.png", "test author", "test publisher", "test genre", "testisbn", "English", copies, reserveQueue);
		//DVD dvd = new DVD(5000, "test dvd", 2018, "1.png", "test director", 200, "English", new String[] {"testsubtitle3", "testsubtitle2"}, copies, reserveQueue);
		//Laptop laptop = new Laptop(10001, "test laptop", 2018, "3.png", "test manufacturer", "test model", "test os", copies, reserveQueue);
		//Librarian librarian = new Librarian("ashaibani", "Mohamed", "Ashaibani", "89 Hanover Street;SA16BQ;Swansea;United Kingdom", "1.png", "teststaffnumber", new Date(Calendar.getInstance().getTimeInMillis()));

		//m.addResource(dvd);
		//m.addResource(book);
		//m.addResource(laptop);
		//m.createUser(librarian);
		//m.updateResource(5000, dvd);

		for(Resource r : m.getResources()) {
			System.out.println("Loaded resource with id, title and type: "+r.getID()+"/"+r.getTitle()+"/"+r.getClass().getSimpleName());
		}

		for(User u : m.getUsers()) {
			System.out.println("Loaded user with username and role:"+u.getUsername()+"/"+(u.isLibrarian() ? "Librarian" : "User"));
		}
	}*/

}
