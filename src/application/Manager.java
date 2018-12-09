package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import resources.Book;
import resources.Copy;
import resources.DVD;
import resources.Laptop;
import resources.Resource;
import resources.ResourceManager;
import users.Librarian;
import users.User;
import users.UserManager;

/**
 *
 * @author Mohamed Ashaibani
 *
 */
public class Manager implements UserManager, ResourceManager {
	private static Manager _instance;
	private Database db;
	private ArrayList<Resource> resources;
	private ArrayList<User> users;
	private User currentUser;
	private ArrayList<Transaction> transactions;

	/**
	 * Manager class constructor.
	 * <p>
	 * When called it creates a connection to the database and initialises the
	 * users, resources and transactions arrays.
	 */
	public Manager() {
		this.db = new Database("tawe.db");
		this.users = new ArrayList<User>();
		this.resources = new ArrayList<Resource>();
		this.transactions = new ArrayList<Transaction>();
	}

	/**
	 * Returns a list of all the resources by querying the books, dvds and
	 * laptops tables in the database.
	 *
	 * @return ArrayList<Resources>
	 * @see Database
	 */
	public ArrayList<Resource> getResources() {
		// TODO NEED TO DO NATURAL JOIN TO CREATE UNIQUE KEY ON ALL 3 RESOURCE
		// TABLES
		resources.clear();
		try {
			ResultSet books = db.executeQuery("SELECT * FROM 'books'");
			while (books.next()) {
				int id = books.getInt("id");
				String title = books.getString("title");
				int year = books.getInt("year");
				String thumbnail = books.getString("thumbnail");
				String author = books.getString("author");
				String publisher = books.getString("publisher");
				String genre = books.getString("genre");
				String isbn = books.getString("isbn");
				String language = books.getString("language");
				ArrayList<Copy> copies = new ArrayList<Copy>();
				for (String copy : books.getString("copies").split("@")) {
					int[] components = Arrays.stream(copy.split("-")).mapToInt(Integer::parseInt).toArray();
					copies.add(new Copy(components[0], components[1], components[2]));
				}
				String[] reserveQueue = books.getString("reserveQueue").split(";");
				Book book = new Book(id, title, year, thumbnail, author, publisher, genre, isbn, language, copies,
						reserveQueue);
				resources.add(book);
			}
			ResultSet dvds = db.executeQuery("SELECT * FROM 'dvds'");
			while (dvds.next()) {
				int id = dvds.getInt("id");
				String title = dvds.getString("title");
				int year = dvds.getInt("year");
				String thumbnail = dvds.getString("thumbnail");
				String director = dvds.getString("director");
				int runtime = dvds.getInt("runtime");
				String language = dvds.getString("language");
				String[] subtitleLangs = dvds.getString("subtitles").split(";");
				ArrayList<Copy> copies = new ArrayList<Copy>();
				for (String copy : dvds.getString("copies").split("@")) {
					int[] components = Arrays.stream(copy.split("-")).mapToInt(Integer::parseInt).toArray();
					copies.add(new Copy(components[0], components[1], components[2]));
				}
				String[] reserveQueue = dvds.getString("reserveQueue").split(";");
				DVD dvd = new DVD(id, title, year, thumbnail, director, runtime, language, subtitleLangs, copies,
						reserveQueue);
				resources.add(dvd);
			}
			ResultSet laptops = db.executeQuery("SELECT * FROM 'laptops'");
			while (laptops.next()) {
				int id = laptops.getInt("id");
				String title = laptops.getString("title");
				int year = laptops.getInt("year");
				String thumbnail = laptops.getString("thumbnail");
				String manufacturer = laptops.getString("manufacturer");
				String model = laptops.getString("model");
				String os = laptops.getString("os");
				ArrayList<Copy> copies = new ArrayList<Copy>();
				for (String copy : laptops.getString("copies").split("@")) {
					int[] components = Arrays.stream(copy.split("-")).mapToInt(Integer::parseInt).toArray();
					copies.add(new Copy(components[0], components[1], components[2]));
				}
				String[] reserveQueue = laptops.getString("reserveQueue").split(";");
				Laptop laptop = new Laptop(id, title, year, thumbnail, manufacturer, model, os, copies, reserveQueue);
				resources.add(laptop);
			}
		} catch (SQLException e) {
			System.out.println("An error occured trying to retrive the resources from the database!");
			e.printStackTrace();
		}
		return resources;
	}

	/**
	 * Returns a list of resources that are an instance of the classType
	 * parameter. The classType parameter must be a class that extends the
	 * Resource class.
	 *
	 * @param classType
	 *            a class that extends the Resource class e.g.
	 *            DVD.class/Book.class/Laptop.class
	 * @return the image at the specified URL
	 * @see Image
	 */
	// will return an arraylist with every class that is a subclass of the given
	// class type in the resources list
	public ArrayList<Resource> getResourceByType(Class<Resource> classType) {
		ArrayList<Resource> temp = new ArrayList<Resource>();
		for (Resource r : getResources()) {
			if (r.getClass() == classType) {
				temp.add(r);
			}
		}
		return temp;
	}

	// example call:
	// getResourceByID(13)
	// will return an instance of the resource with the given id in the
	// resources list
	public Resource getResourceByID(int id) {
		for (Resource r : getResources()) {
			if (r.getID() == id) {
				return r;
			}
		}
		return null;
	}
	
	public Resource getResourceByTitle(String title) {
		for (Resource r : getResources()) {
			if (r.getTitle().equals(title)) {
				return r;
			}
		}
		return null;
	}


	public void addResource(Resource r) {
		switch (r.getClass().getSimpleName().toLowerCase()) {
		case "dvd":
			try {
				DVD dvd = (DVD) r;
				String subtitles = "";
				for (String language : dvd.getSubtitleLangs()) {
					subtitles += language + ";";
				}
				String copies = "";
				for (Copy copy : dvd.getCopies()) {
					copies += copy.getID() + "-" + copy.getLoanDuration() + "-" + copy.getStatus() + "@";
				}
				String reserveQueue = "";
				for (String user : dvd.getReserveQueue()) {
					reserveQueue += user + ";";
				}
				db.executeUpdate(
						"INSERT INTO dvds(title, year, director, runtime, language, subtitles, thumbnail, copies, reserveQueue) VALUES ('"
								+ dvd.getTitle() + "', '" + dvd.getYear() + "', '" + dvd.getDirector() + "', '"
								+ dvd.getRunTime() + "', '" + dvd.getLanguage() + "', '" + subtitles + "', '"
								+ dvd.getThumbnail() + "', '" + copies + "', '" + reserveQueue + "')");
			} catch (SQLException e) {
				System.out.println("An error occured attempting to add a dvd to the database!");
				e.printStackTrace();
				return;
			}
			break;
		case "book":
			try {
				Book book = (Book) r;
				String copies = "";
				for (Copy copy : book.getCopies()) {
					copies += copy.getID() + "-" + copy.getLoanDuration() + "-" + copy.getStatus() + "@";
				}
				String reserveQueue = "";
				for (String user : book.getReserveQueue()) {
					reserveQueue += user + ";";
				}
				db.executeUpdate(
						"INSERT INTO books(title, year, author, genre, isbn, language, publisher, thumbnail, copies, reserveQueue) VALUES ('"
								+ book.getTitle() + "', '" + book.getYear() + "', '" + book.getAuthor() + "', '"
								+ book.getGenre() + "', '" + book.getISBN() + "', '" + book.getLanguage() + "', '"
								+ book.getPublisher() + "', '" + book.getThumbnail() + "', '" + copies + "', '"
								+ reserveQueue + "')");
			} catch (SQLException e) {
				System.out.println("An error occured attempting to add a book to the database!");
				e.printStackTrace();
				return;
			}
			break;
		case "laptop":
			try {
				Laptop laptop = (Laptop) r;
				String copies = "";
				for (Copy copy : laptop.getCopies()) {
					copies += copy.getID() + "-" + copy.getLoanDuration() + "-" + copy.getStatus() + "@";
				}
				String reserveQueue = "";
				for (String user : laptop.getReserveQueue()) {
					reserveQueue += user + ";";
				}
				db.executeUpdate(
						"INSERT INTO laptops(title, year, model, os, manufacturer, thumbnail, copies, reserveQueue) VALUES ('"
								+ laptop.getTitle() + "', '" + laptop.getYear() + "', '" + laptop.getModel() + "', '"
								+ laptop.getOS() + "', '" + laptop.getManufacturer() + "', '" + laptop.getThumbnail()
								+ "', '" + copies + "', '" + reserveQueue + "')");
			} catch (SQLException e) {
				System.out.println("An error occured attempting to add a laptop to the database!");
				e.printStackTrace();
				return;
			}
			break;
		}
		resources.add(r);
	}

	public void updateResource(int id, Resource r) {
		for (int i = 0; i < resources.size(); i++) {
			if (resources.get(i).getID() == id) {
				String table = resources.get(i).getClass().getSimpleName().toLowerCase() + "s";
				try {
					db.executeUpdate("UPDATE " + table + " SET " + r.toString() + " WHERE id = '" + id + "'");
				} catch (SQLException e) {
					System.out.println("An error occured attempting to update a "
							+ table.substring(0, table.length() - 1) + " in the database.");
					return;
				}
				resources.set(i, r);
			}
		}
	}

	public void removeResource(int id) {
		for (int i = 0; i < resources.size(); i++) {
			if (resources.get(i).getID() == id) {
				String table = resources.get(i).getClass().getSimpleName().toLowerCase() + "s";
				try {
					db.executeUpdate("DELETE FROM '" + table + "' WHERE id = '" + id + "'");
					resources.remove(i);
				} catch (SQLException e) {
					System.out.println("An error occured trying to delete a " + table.substring(0, table.length() - 1)
							+ " from the database!");
					return;
				}
			}
		}
	}

	public void reserveResource(int id) {
		// TODO some more code has to be written for this
	}

	public void requestResource(int id) {
		// TODO some more code has to be written for this
	}

	public void addTransaction(Transaction t) {
		// TODO add database code to handle this
		this.transactions.add(t);
	}

	public boolean authenticate(String username) {
		try {
			ResultSet results = db.executeQuery("SELECT * FROM 'users' WHERE username='" + username + "'");
			if (results.isBeforeFirst()) {
				this.currentUser = retrieveUser(username);
				return true;
			} else {
				results = db.executeQuery("SELECT * FROM 'librarians' WHERE username='" + username + "'");
				if (results.isBeforeFirst()) {
					this.currentUser = retrieveUser(username);
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			System.out.println(
					"An error occured trying to locate a user with username: " + username + " in the database!");
			e.printStackTrace();
		}
		return false;
	}

	public void createUser(User u) {
		if (retrieveUser(u.getUsername()) != null) {
			System.out.println("User already exists! Please choose a unique username.");
			return;
		}
		if (!u.isLibrarian()) {
			try {
				db.executeUpdate("INSERT INTO users VALUES ('" + u.getUsername() + "', '" + u.getFirstName() + "', '"
						+ u.getLastName() + "', '" + u.getAddress() + "', '" + u.getPhoneNumber() + "', '"
						+ u.getProfileImage() + "', '" + u.getBalance() + "')");
				users.add(u);
			} catch (SQLException e) {
				System.out.println("An error occured attempting to add a user to the database!");
				e.printStackTrace();
				return;
			}
		} else {
			try {
				db.executeUpdate("INSERT INTO librarians VALUES ('" + u.getUsername() + "', '" + u.getFirstName()
						+ "', '" + u.getLastName() + "', '" + u.getAddress() + "', '" + u.getPhoneNumber() + "', '"
						+ u.getProfileImage() + "', '" + u.getBalance() + "', '" + ((Librarian) u).getStaffNumber()
						+ "', '" + ((Librarian) u).getEmploymentDate().toString() + "')");
				users.add(u);
			} catch (SQLException e) {
				System.out.println("An error occured attempting to add a librarian to the database!");
				e.printStackTrace();
				return;
			}
		}
	}

	public void deleteUser(String username) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equalsIgnoreCase(username)) {
				try {
					db.executeUpdate("DELETE FROM 'users' WHERE username = '" + username + "'");
					users.remove(i);
				} catch (SQLException e) {
					System.out.println("An error occured trying to delete a user from the database!");
					e.printStackTrace();
					return;
				}
			}
		}
	}

	public User retrieveUser(String username) {
		for (User u : getUsers()) {
			if (u.getUsername().equalsIgnoreCase(username)) {
				return u;
			}
		}
		return null;
	}

	public void updateUser(String username, User u) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equalsIgnoreCase(username)) {
				User user = users.get(i);
				String table = user.isLibrarian() ? "librarians" : "users";
				try {
					db.executeUpdate(
							"UPDATE " + table + " SET " + user.toString() + " WHERE username = '" + username + "'");
					users.set(i, u);
				} catch (SQLException e) {
					System.out.println("An error occured attempting to update a user in the database!");
					e.printStackTrace();
					return;
				}
			}
		}
	}

	public boolean checkAuthorised() {
		if (currentUser != null) {
			return true;
		} else {
			return false;
		}
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public ArrayList<User> getUsers() {
		users.clear();
		ResultSet result;
		try {
			result = db.executeQuery("SELECT * FROM 'librarians'");

			while (result.next()) {
				String username = result.getString("username");
				String firstname = result.getString("firstname");
				String lastname = result.getString("lastname");
				String address = result.getString("address");
				String phoneNumber = result.getString("phoneNumber");
				String profileImage = result.getString("profileImage");
				String staffNumber = result.getString("staffNumber");
				Date employmentDate = Date.valueOf(result.getString("employmentDate"));
				float balance = result.getFloat("balance");

				Librarian u = new Librarian(username, firstname, lastname, address, phoneNumber, profileImage, balance,
						staffNumber, employmentDate);
				users.add(u);
			}
			result = db.executeQuery("SELECT * FROM 'users'");

			while (result.next()) {
				String username = result.getString("username");
				String firstname = result.getString("firstname");
				String lastname = result.getString("lastname");
				String address = result.getString("address");
				String phoneNumber = result.getString("phoneNumber");
				String profileImage = result.getString("profileImage");
				float balance = result.getFloat("balance");

				User u = new User(username, firstname, lastname, address, phoneNumber, profileImage, balance);
				users.add(u);
			}
		} catch (SQLException e) {
			System.out.println("An error occured trying to retrieve all users/librarians in the database!");
			e.printStackTrace();
			return null;
		}
		return users;
	}

	public ArrayList<Transaction> getTransactions() {
		transactions.clear();
		try {
			ResultSet transactions = db.executeQuery("SELECT * FROM 'transactions'");
			while (transactions.next()) {
				int id = transactions.getInt("id");
				String username = transactions.getString("username");
				int resourceId = transactions.getInt("resourceId");
				int copyId = transactions.getInt("copyId");
				Date dateTaken = Date.valueOf(transactions.getString("dateTaken"));
				String dueDateString = transactions.getString("dueDate");
				Date dueDate = (dueDateString != "" || dueDateString != null) ? Date.valueOf(dueDateString) : null;
				int status = transactions.getInt("status");

				Transaction t = new Transaction(id, username, resourceId, copyId, dateTaken, dueDate, status);
				this.transactions.add(t);
			}
		} catch (SQLException e) {
			System.out.println("An error occured trying to retrieve all transactions in the database!");
			e.printStackTrace();
		}
		return transactions;
	}

	public static Manager getInstance() {
		if (_instance == null) {
			_instance = new Manager();
		}
		return _instance;
	}

	public void logout() {
		this.currentUser = null;
	}
}
