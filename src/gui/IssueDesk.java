package gui;

import java.io.File;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class IssueDesk extends Application {
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;
	private static final int BUTTON_MAX_WIDTH = 150;
	private static final int BUTTON_MAX_HEIGHT = 40;
	private Manager manager;

	public IssueDesk() {
		manager = Manager.getInstance();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			File imagesDir = new File("./images/avatars/");
			Image profile = new Image("file:" + imagesDir.getAbsolutePath() + "\\" + manager.getCurrentUser().getProfileImage());
			BorderPane root = new BorderPane();

			HBox top = new HBox();
			top.setId("top");

			Label userName = new Label(manager.getCurrentUser().getUsername());
			userName.setFont(Font.font("Arial", 16));
			userName.setId("userName");

			HBox topLeft = new HBox();
			topLeft.setAlignment(Pos.CENTER_LEFT);

			Button logOut = new Button("Log out");
			HBox topRight = new HBox();
			topRight.setAlignment(Pos.CENTER_RIGHT);
			Label fineMessage = new Label ();
			Label validEntry = new Label ();

			TextField fineTextFeild = new TextField();
			fineTextFeild.setMaxWidth(150);


			Button backButton = new Button("Back");

			Button payButton = new Button ("Pay Fine");
			payButton.setVisible(false);


			top.setPadding(new Insets(2));

			Circle profilePic = new Circle(40);

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
			payFine.getChildren().addAll(fineMessage,validEntry);
			payFine.setSpacing(10);


			if (manager.getCurrentUser().getBalance() == 0)
			{
				fineMessage.setText("There is no fine to be payed");
			}
			else
			{
				fineMessage.setText("Total fine payable: £"+Float.toString(manager.getCurrentUser().getBalance()));

				payFine.getChildren().addAll(fineTextFeild, payButton);
				payButton.setVisible(true);

			}


			Button fineButton = new Button("Pay a fine");
			fineButton.setMaxWidth(BUTTON_MAX_WIDTH);
			fineButton.setMinHeight(BUTTON_MAX_HEIGHT);
			Button returnCopyButton = new Button("Return a book etc.");
			returnCopyButton.setMaxWidth(BUTTON_MAX_WIDTH);
			returnCopyButton.setMinHeight(BUTTON_MAX_HEIGHT);
			Button takeOutButton = new Button("Take out a book etc.");
			takeOutButton.setMaxWidth(BUTTON_MAX_WIDTH);
			takeOutButton.setMinHeight(BUTTON_MAX_HEIGHT);

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

			backButton.setOnAction(e -> {
				Dashboard instance = new Dashboard();
				instance.start(primaryStage);

			});

			fineButton.setOnAction(e -> {
				payFine.setVisible(true);
			});

			payButton.setOnAction(e -> {
				if (Float.valueOf(fineTextFeild.getText()) - manager.getCurrentUser().getBalance() == 0)
				{
					manager.getCurrentUser().setBalance(manager.getCurrentUser().getBalance() - Float.valueOf(fineTextFeild.getText()));
					fineMessage.setText("There is no fine to be payed");
					fineTextFeild.setVisible(false);
					payButton.setVisible(false);
					validEntry.setVisible(false);
				}
				else if (Float.valueOf(fineTextFeild.getText()) <= manager.getCurrentUser().getBalance())
				{
					manager.getCurrentUser().setBalance(manager.getCurrentUser().getBalance() - Float.valueOf(fineTextFeild.getText()));
					fineMessage.setText("Total fine payable: £"+Float.toString(manager.getCurrentUser().getBalance()));
					validEntry.setVisible(false);
				}
				else
				{
					validEntry.setText("Please enter a valid input");
				}
			});
			//TAKE THIS OUT IT IS FOR TESTING
			Button addOneToBalance = new Button ("Balance+1");
			menuBar.getChildren().add(addOneToBalance);
			addOneToBalance.setOnAction(e ->
			{
				manager.getCurrentUser().setBalance(manager.getCurrentUser().getBalance() + 1);
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

