package gui;

import java.io.File;

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

public class Dashboard extends Application {
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;
	private static final int BUTTON_MAX_WIDTH = 150;
	private static final int BUTTON_MAX_HEIGHT = 40;
	private Manager manager;

	public Dashboard() {
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

			top.setPadding(new Insets(2));

			Circle profilePic = new Circle(40);

			profilePic.setFill(new ImagePattern(profile));
			topLeft.getChildren().addAll(profilePic, userName);
			topLeft.setSpacing(20);
			topRight.getChildren().add(logOut);
			top.getChildren().addAll(topLeft, topRight);
			top.setSpacing(300);

			VBox menuBar = new VBox();
			menuBar.minWidth(150);
			menuBar.setId("menuBar");

			Button issueDesk = new Button("IssueDesk");
			issueDesk.setMaxWidth(BUTTON_MAX_WIDTH);
			issueDesk.setMinHeight(BUTTON_MAX_HEIGHT);
			Button browse = new Button("Browse");
			browse.setMaxWidth(BUTTON_MAX_WIDTH);
			browse.setMinHeight(BUTTON_MAX_HEIGHT);
			Button avatar = new Button("Avatar");
			avatar.setMaxWidth(BUTTON_MAX_WIDTH);
			avatar.setMinHeight(BUTTON_MAX_HEIGHT);


			Button tempLibButton = new Button("Librarian");
			tempLibButton.setMaxWidth(BUTTON_MAX_WIDTH);
			tempLibButton.setMinHeight(BUTTON_MAX_HEIGHT);

			menuBar.getChildren().addAll(issueDesk, browse, avatar,tempLibButton);
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

			logOut.setOnAction(e -> {
				manager.logout();
				logIn.start(primaryStage);
			});
			
			browse.setOnAction(e -> {
				Browser browser = new Browser();
				browser.start(primaryStage);
			});


			issueDesk.setOnAction(e -> {
				if (manager.getCurrentUser().isLibrarian())
				{
					LibrarianIssueDesk instance = new LibrarianIssueDesk();
					instance.start(primaryStage);
				}
				//TAKE OUT BEFORE SUBMISSION THIS IS FOR TESTING
				else
				{
					IssueDesk instance = new IssueDesk();
					instance.start(primaryStage);
				}


			});

			tempLibButton.setOnAction(e -> {
				LibrarianIssueDesk desk = new LibrarianIssueDesk();
				desk.start(primaryStage);
			});

			avatar.setOnAction(e -> {
				Paint paint = new Paint();
				paint.start(primaryStage);
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
}
