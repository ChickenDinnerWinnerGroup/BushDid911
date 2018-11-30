package gui;

import application.Manager;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import users.User;

public class LogInScr extends Application {
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;
	private Manager manager;

	public LogInScr() {
		manager = Manager.getInstance();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			scene1(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scene1(Stage primaryStage) {
		// scene 1
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);

		HBox center = new HBox();
		center.setAlignment(Pos.CENTER);
		HBox centerTwo = new HBox();
		centerTwo.setAlignment(Pos.CENTER);

		TextField num1 = new TextField();
		num1.setMaxWidth(150);

		Label userID = new Label("Username: ");
		Button logIn = new Button("Log in");
		Label label = new Label();

		root.setSpacing(30);
		center.getChildren().addAll(userID, num1, label);
		centerTwo.setSpacing(30);
		centerTwo.getChildren().addAll(logIn);

		root.getChildren().addAll(center, centerTwo);

		logIn.setOnAction(e -> {
			String username = "";
			if (num1.getText().isEmpty()) {
				label.setText("information is missing");
				label.setTextFill(Color.web("#B22222"));
			} else {
				username = num1.getText();
				Manager m = Manager.getInstance();
				if (m.authenticate(username)) {
					System.out.println("User was found in the database!");
					Dashboard dash = new Dashboard();
					dash.start(primaryStage);
				} else {
					System.out.println("User was not found in the database!");
				}
			}
		});

		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		primaryStage.getIcons().add(new Image("images/swanseauni.png"));
		primaryStage.setTitle("Tawe-Lib");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}