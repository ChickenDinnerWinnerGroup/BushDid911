package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import application.Manager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LibrarianIssueDesk extends Application {
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;
	private static final int BUTTON_MAX_WIDTH = 150;
	private static final int BUTTON_MAX_HEIGHT = 40;
	private Manager manager;

	public LibrarianIssueDesk() {
		manager = Manager.getInstance();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			ArrayList<String> userArray = new ArrayList<String>();
			
			String origionalUser = manager.getCurrentUser().getUsername();  
			Image profile = new Image("images/avatars/" + manager.getCurrentUser().getProfileImage());
			BorderPane root = new BorderPane();

			HBox top = new HBox();
			top.setId("top");
			
			Button payButton = new Button ("Pay Fine");
			payButton.setVisible(false);
			
			TextField fineTextFeild = new TextField();
			fineTextFeild.setMaxWidth(150);
			
			TextField usernameField = new TextField();
			
			Label fineMessage = new Label ("Below are the fines that have been payed,\n"
					+ "Enter a users name to confirm the fine has been payed");
			Label finesLabel = new Label();
			
			userArray = readFile();
			System.out.print(Arrays.toString(userArray.toArray()));
			
			Button createAcct =  new Button ("Create An Account");

			Label userName = new Label(manager.getCurrentUser().getUsername());
			userName.setFont(Font.font("Arial", 16));
			userName.setId("userName" + " LIBRARIAN");

			HBox topLeft = new HBox();
			topLeft.setAlignment(Pos.CENTER_LEFT);

			Button logOut = new Button("Log out");
			HBox topRight = new HBox();
			topRight.setAlignment(Pos.CENTER_RIGHT);
			
			Button accessAccount =  new Button ("Confirm Fine Payments");

			top.setPadding(new Insets(2));

			Circle profilePic = new Circle(40);

			Button backButton = new Button("Back");
			Button returnCopy = new Button("Return Copy");
			Button takeOutButton = new Button("Take out a book");

			profilePic.setFill(new ImagePattern(profile));
			topLeft.getChildren().addAll(profilePic, userName);
			topLeft.setSpacing(20);
			topRight.getChildren().add(backButton);
			topRight.getChildren().add(logOut);
			top.getChildren().addAll(topLeft, topRight);
			top.setSpacing(300);

			VBox menuBar = new VBox();
			menuBar.minWidth(150);
			menuBar.setId("menuBar");
			VBox payFine = new VBox();
			
			readFile();
			
			payFine.setVisible(false);
			payFine.minWidth(150);
			payFine.setId("payFine");
			payFine.getChildren().addAll(fineMessage, finesLabel, usernameField, accessAccount);
			payFine.setSpacing(10);
			

			Button fineButton = new Button("Pay a fine");
			fineButton.setMaxWidth(BUTTON_MAX_WIDTH);
			fineButton.setMinHeight(BUTTON_MAX_HEIGHT);

			menuBar.getChildren().addAll(fineButton, returnCopy, takeOutButton, createAcct);
			menuBar.setSpacing(20);

			HBox bottomBar = new HBox();
			bottomBar.setId("bottomBar");
			Label copyNote = new Label("Copyright Tawe-Lib 2019");
			copyNote.setId("copyNote");
			bottomBar.getChildren().add(copyNote);
			bottomBar.setAlignment(Pos.CENTER);

			root.setBottom(bottomBar);
			root.setTop(top);
			root.setLeft(menuBar);
			root.setCenter(payFine);
			Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
			
			createAcct.setOnAction(e -> {
				CreateAccount desk = new CreateAccount();
				desk.start(primaryStage);
			});
			
			logOut.setOnAction(e -> {
				LogInScr instance = new LogInScr();
				instance.start(primaryStage);
				
			});
			
			backButton.setOnAction(e -> {
				Dashboard instance = new Dashboard();
				instance.start(primaryStage);
				
			});
			
			fineButton.setOnAction(e -> {
				
				payFine.setVisible(true);
				updateLabelText(finesLabel);
			});
			
			accessAccount.setOnAction(e -> {
				String username = "";
				if (usernameField.getText().isEmpty()) {
					
				} else {
					username = usernameField.getText();
					Manager m = Manager.getInstance();
					if (m.authenticate(username)) {
						System.out.println("User was found in the database!");
						confirmFinesPayed(readFile(), manager.getCurrentUser().getUsername());
						deletePayedFines(username);
						updateLabelText(finesLabel);
						                                                                                         
					} else {
						System.out.println("User was not found in the database!");
					}
				}
			});
			

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.getIcons().add(new Image("images/swanseauni.png"));
			primaryStage.setTitle("Tawe-Lib");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
			
	    public static ArrayList<String> readFile ()
		{
	    	ArrayList<String> userArray = new ArrayList<String>(0);
	    	try (BufferedReader br = new BufferedReader(new FileReader("fines.txt"))) {
	    		String line = null;
	             while ((line = br.readLine()) != null) {
	               // label.setText(label.getText() +"\n"+ line);
	                String[] splited = line.split(" ");
	                userArray.addAll(Arrays.asList(splited));
	             }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	return userArray;
	    }
	    
	    private void confirmFinesPayed (ArrayList<String> userArray, String user)
	    {
	    	for (int i=0;i<userArray.size();i++)
	    	{

	    		if (userArray.get(i).equals(user))
	    		{
	    			
	    			System.out.println(manager.getCurrentUser().getBalance());
	    			
	    			manager.getCurrentUser().setBalance(manager.getCurrentUser().getBalance() - Integer.parseInt(userArray.get(i+1)));
	    			System.out.println(manager.getCurrentUser().getBalance());
	    			
	    		}

	    	}
	    }
	    
	   private void updateLabelText (Label label)
	   {
	    	try (BufferedReader br = new BufferedReader(new FileReader("fines.txt"))) {
	    		String line = null;
	             while ((line = br.readLine()) != null) {
	               label.setText(label.getText() + line);
	             }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	   private void deletePayedFines (String user)
	   {
		   File inputFile = new File("fines.txt");
		   File tempFile = new File("myTempFile.txt");
	    	try (BufferedReader br = new BufferedReader(new FileReader("fines.txt")))
	    	{
	    		try(BufferedWriter bw = new BufferedWriter(new FileWriter("myTempFile.txt")))
	               {
	    		String line = null;
	             while ((line = br.readLine()) != null) {
	               
	                String[] splited = line.split(" ");
	                if (!splited[0].equals(user))
	                {
	                	
	                	bw.write(line);
	                	bw.write("\r\n");
	                }
	             }
	               }
	               catch(IOException e) 
	               {
	   	            e.printStackTrace();
	               }
	        } 
	    	catch (IOException e) 
	        {
	            e.printStackTrace();
	        
	    	
	        }
	    	inputFile.delete();
	    	tempFile.renameTo(inputFile);
	    }

}
