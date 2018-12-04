package paint;

import java.io.File;
import java.io.IOException;

import application.Manager;
import gui.Paint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import users.User;

public class AvatarSelectorController {
	private File imagesDir;

	@FXML
	private Button backButton;
	@FXML
	private ImageView default1;
	@FXML
	private ImageView default2;
	@FXML
	private ImageView default3;
	@FXML
	private ImageView default4;
	@FXML
	private ImageView default5;
	@FXML
	private ImageView custom1;
	@FXML
	private ImageView custom2;
	@FXML
	private ImageView custom3;
	@FXML
	private ImageView custom4;
	@FXML
	private ImageView custom5;

	private String[] images;

	public void initialize() throws IOException {
		imagesDir = new File("./images/avatars/");
		images = listUserAvatars();
		default1.setImage(new Image("images/avatars/default1.png"));
		default1.setImage(new Image("images/avatars/default2.png"));
		default1.setImage(new Image("images/avatars/default3.png"));
		default1.setImage(new Image("images/avatars/default4.png"));
		default1.setImage(new Image("images/avatars/default5.png"));
		System.out.println(imagesDir.getAbsolutePath() + "\\" + images[0]);
		custom1.setImage(new Image(imagesDir.getAbsolutePath() + "\\" + images[0]));
		custom2.setImage(new Image(imagesDir.getAbsolutePath() + "\\" + images[1]));
		custom3.setImage(new Image(imagesDir.getAbsolutePath() + "\\" + images[2]));
		custom4.setImage(new Image(imagesDir.getAbsolutePath() + "\\" + images[3]));
		custom5.setImage(new Image(imagesDir.getAbsolutePath() + "\\" + images[4]));

	}

	@FXML
	private void handleBackButton(ActionEvent e) {
		Stage primaryStage = (Stage) backButton.getScene().getWindow();
		Paint paint = new Paint();
		paint.start(primaryStage);
	}

	@FXML
	private void selectAvatar(MouseEvent e) {
		Manager m = Manager.getInstance();
		User user = m.getCurrentUser();
		ImageView selection = (ImageView) e.getSource();
		System.out.println();
		switch (selection.getId()) {
		case "default1":
			user.setProfileImage("default1.png");
			break;
		case "default2":
			user.setProfileImage("default2.png");
			break;
		case "default3":
			user.setProfileImage("default3.png");
			break;
		case "default4":
			user.setProfileImage("default4.png");
			break;
		case "default5":
			user.setProfileImage("default5.png");
			break;
		case "custom1":
			user.setProfileImage(images[0]);
			break;
		case "custom2":
			user.setProfileImage(images[1]);
			break;
		case "custom3":
			user.setProfileImage(images[2]);
			break;
		case "custom4":
			user.setProfileImage(images[3]);
			break;
		case "custom5":
			user.setProfileImage(images[4]);
			break;
		}
	}

	private String[] listUserAvatars() {
		Manager m = Manager.getInstance();
		String[] images = imagesDir.list();
		String[] temp = { "none.png", "none.png", "none.png", "none.png", "none.png" };
		int limit = (images.length > 5 ? 5 : images.length);
		for (int i = 0; i < limit; i++) {
			String image = images[i];
			if (image.startsWith(m.getCurrentUser().getUsername() + "-") && image.endsWith(".png")) {
				temp[i] = image;
			}
		}
		return temp;
	}
}
