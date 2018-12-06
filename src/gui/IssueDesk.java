package gui;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import application.Manager;
import application.Transaction;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import resources.Resource;

public class IssueDesk extends Application {
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;
	private static final int BUTTON_MAX_WIDTH = 150;
	private static final int BUTTON_MAX_HEIGHT = 40;
	private Manager manager;
	private ArrayList<Button> borrowed = new ArrayList<Button>();

	public IssueDesk() {
		manager = Manager.getInstance();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Image profile = new Image("images/avatars/" + manager.getCurrentUser().getProfileImage());
			BorderPane root = new BorderPane();
			
			String line = "";

			HBox top = new HBox();
			top.setId("top");

			Label userName = new Label(manager.getCurrentUser().getUsername());
			userName.setFont(Font.font("Arial", 16));
			userName.setId("userName");

			TextField fineTextField = new TextField();
			fineTextField.setMaxWidth(150);
			
			HBox topLeft = new HBox();
			topLeft.setAlignment(Pos.CENTER_LEFT);

			Button logOut = new Button("Log out");
			HBox topRight = new HBox();
			topRight.setAlignment(Pos.CENTER_RIGHT);
			
			Button returnCopyButton = new Button("Return a Copy");
			
			Button fineButton =  new Button ("Pay your fines");
			
			Button backButton = new Button("Back");
			
			Button payButton = new Button("Pay");
			
			Label validEntry = new Label ();
			
			Label fineMessage = new Label ();
			
			top.setPadding(new Insets(2));
			
			//TextField resourceIDField = new TextField();
			//TextField copyIDField = new TextField();
			
			Label currentlyBorrowed = new Label();
			
			
			Circle profilePic = new Circle(40);
			
			Button takeOutButton = new Button("Take out a book etc.");
			takeOutButton.setMaxWidth(BUTTON_MAX_WIDTH);
			takeOutButton.setMinHeight(BUTTON_MAX_HEIGHT);
			Button createAcct = new Button("create acct");
			createAcct.setMaxWidth(BUTTON_MAX_WIDTH);
			createAcct.setMinHeight(BUTTON_MAX_HEIGHT);

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
			
			payFine.setVisible(false);
			payFine.minWidth(150);
			payFine.setId("payFine");
			payFine.getChildren().addAll(fineMessage);
			payFine.setSpacing(10);
			
			VBox returnCopy = new VBox();	
			
			ArrayList<Transaction> curBor = manager.getTransactions();
			
			
			for(Transaction item : curBor) {
				if(manager.getCurrentUser().getUsername().equals(item.getUsername())) {
					Resource curItem = manager.getResourceByID(item.getResourceID());
					Button b1 = new Button();
					b1.setText( "Return " + curItem.getTitle());
					borrowed.add(b1);
					returnCopy.getChildren().add(b1);
					
				}
			}
			
			returnCopy.minWidth(150);
			returnCopy.setId("Return");
			returnCopy.setSpacing(10);
			
		//TAKE THIS OUT
			Label testLabel = new Label ("TEST");
			returnCopy.getChildren().add(testLabel);
			
			float fineLeftToPay = manager.getCurrentUser().getBalance() - finesPending(LibrarianIssueDesk.readFile(),
					manager.getCurrentUser().getUsername());
			
			if (fineLeftToPay == 0)
			{
				fineMessage.setText("There is no fine to be payed");
			}
			else
			{
				fineMessage.setText("Total fine payable: £"+ Float.toString(fineLeftToPay));
				
				payFine.getChildren().addAll(fineTextField, validEntry, payButton);
				payButton.setVisible(true);
				
			}
			
			

			menuBar.getChildren().addAll(fineButton, returnCopyButton, takeOutButton);
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
			LogInScr logIn = new LogInScr();
			
			
			
			
			logOut.setOnAction(e -> {
				LogInScr instance = new LogInScr();
				instance.start(primaryStage);
				
			});
			
			returnCopyButton.setOnAction(e -> {
				root.setCenter(returnCopy);
				
			});
			
			backButton.setOnAction(e -> {
				Dashboard instance = new Dashboard();
				instance.start(primaryStage);
				
			});
			
			
			
			fineButton.setOnAction(e -> {
				payFine.setVisible(true);
			});
			
			
			
			
			//TAKE THIS OUT IT IS FOR TESTING
			Button addOneToBalance = new Button ("Balance+1");
			menuBar.getChildren().add(addOneToBalance);
			addOneToBalance.setOnAction(e -> 
			{
				manager.getCurrentUser().setBalance(manager.getCurrentUser().getBalance() + 1);
			});
			
			
			payButton.setOnAction(e -> {
				float finesLeftToPay = manager.getCurrentUser().getBalance() - finesPending(LibrarianIssueDesk.readFile(),
						manager.getCurrentUser().getUsername());
				
				if (finesLeftToPay - Float.valueOf(fineTextField.getText()) == 0)
				{
					writeTransactionToFile(manager.getCurrentUser().getUsername() +" "+ fineTextField.getText(), "fines.txt");
					//writeTransactionToFile(manager.getCurrentUser().getUsername() +" "+ fineTextField.getText());
					//manager.getCurrentUser().setBalance(manager.getCurrentUser().getBalance() - Float.valueOf(fineTextFeild.getText()));
					fineMessage.setText("There is no fine to be payed");
					fineTextField.setVisible(false);
					payButton.setVisible(false);
					validEntry.setVisible(false);
				}
				else if (Float.valueOf(fineTextField.getText()) <= manager.getCurrentUser().getBalance())
				{
					writeTransactionToFile(manager.getCurrentUser().getUsername() +" "+ fineTextField.getText(),"fines.txt");
					System.out.println(finesLeftToPay);
					fineMessage.setText("Total fine payable: £"+(finesLeftToPay - Float.valueOf(fineTextField.getText())));
					//manager.getCurrentUser().setBalance(manager.getCurrentUser().getBalance() - Float.valueOf(fineTextFeild.getText()));
					fineMessage.setText("Total fine payable: £"+Float.toString(manager.getCurrentUser().getBalance()));
					validEntry.setVisible(false);
				}
				else
				{
					validEntry.setText("Please enter a valid input");
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
	
	private void writeTransactionToFile (String line, String fileName)
	{
		try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(line);
            writer.write("\r\n");   // write new line
            
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	 private float finesPending (ArrayList<String> userArray, String user)
	    {
		 int total = 0;
	    	for (int i=0;i<userArray.size();i++)
	    	{

	    		if (userArray.get(i).equals(user))
	    		{
	    			total = total + Integer.parseInt(userArray.get(i+1));
	    		}

	    	}
	    	return total;
	    }
	
	
	/**
	private Queue<Resource> requests = new LinkedList<>();
	private User userObject;
	
	public issueDesk (User userObject)
	{
		this.userObject = userObject;
	}
	
	public void payFine (float payment)
			{
				this.userObject.subtractBalance(payment);
			}
	
	public void returnCopy(Resource item)
	{
		
	}
	

	
	public void issueCopy (Resource item)
	{
		
	} 
	//gg
	 * **/
	 
}

