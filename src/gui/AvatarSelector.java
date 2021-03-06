package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AvatarSelector extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("AvatarSelector.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("images/swanseauni.png"));
			primaryStage.setTitle("Avatar Selector");
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Failed to load FXML layout for the avatar selector user interface.");
			e.printStackTrace();
		}
	}
}
