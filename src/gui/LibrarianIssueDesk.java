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
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
			File imagesDir = new File("./images/avatars/");
			Image profile = new Image("file:" + imagesDir.getAbsolutePath() + "\\" + manager.getCurrentUser().getProfileImage());
			BorderPane root = new BorderPane();

			HBox top = new HBox();
			top.setId("top");

			Label userName = new Label(manager.getCurrentUser().getUsername());
			userName.setFont(Font.font("Arial", 16));
			userName.setId("userName" + " LIBRARIAN");

			HBox topLeft = new HBox();
			topLeft.setAlignment(Pos.CENTER_LEFT);

			Button logOut = new Button("Log out");
			HBox topRight = new HBox();
			topRight.setAlignment(Pos.CENTER_RIGHT);

			top.setPadding(new Insets(2));

			Circle profilePic = new Circle(40);

			Button backButton = new Button("Back");

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

			Button fineButton = new Button("Pay a fine");
			fineButton.setMaxWidth(BUTTON_MAX_WIDTH);
			fineButton.setMinHeight(BUTTON_MAX_HEIGHT);
			Button returnCopyButton = new Button("Return a book etc.");
			returnCopyButton.setMaxWidth(BUTTON_MAX_WIDTH);
			returnCopyButton.setMinHeight(BUTTON_MAX_HEIGHT);
			Button takeOutButton = new Button("Take out a book etc.");
			takeOutButton.setMaxWidth(BUTTON_MAX_WIDTH);
			takeOutButton.setMinHeight(BUTTON_MAX_HEIGHT);
			Button createAcct = new Button("create acct");
			createAcct.setMaxWidth(BUTTON_MAX_WIDTH);
			createAcct.setMinHeight(BUTTON_MAX_HEIGHT);

			menuBar.getChildren().addAll(fineButton, returnCopyButton, takeOutButton, createAcct);
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
			Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
			LogInScr logIn = new LogInScr();

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
	public Queue<Resource> getOverdueCopies()
	{
		gg
	}


	public boolean canBorrow (int userID)
	{

	}

	public void createAccount (String username, String firstName, String lastName, String address, String profileImage)
	{

	}
	**/


}
