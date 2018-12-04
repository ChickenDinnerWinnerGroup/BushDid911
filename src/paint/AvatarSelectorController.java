package paint;

import java.io.File;
import java.util.Optional;

import application.Manager;
import gui.Paint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import users.User;

public class AvatarSelectorController {
	private File imagesDir;
	private String[] images;

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

	public void initialize() {
		imagesDir = new File("./images/avatars/");
		images = listUserAvatars();
		default1.setImage(new Image("images/avatars/default1.png"));
		default1.setImage(new Image("images/avatars/default2.png"));
		default1.setImage(new Image("images/avatars/default3.png"));
		default1.setImage(new Image("images/avatars/default4.png"));
		default1.setImage(new Image("images/avatars/default5.png"));

		custom1.setImage(new Image("file:" + imagesDir.getAbsolutePath() + "\\" + images[0]));
		custom2.setImage(new Image("file:" + imagesDir.getAbsolutePath() + "\\" + images[1]));
		custom3.setImage(new Image("file:" + imagesDir.getAbsolutePath() + "\\" + images[2]));
		custom4.setImage(new Image("file:" + imagesDir.getAbsolutePath() + "\\" + images[3]));
		custom5.setImage(new Image("file:" + imagesDir.getAbsolutePath() + "\\" + images[4]));
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
		if (e.getButton() == MouseButton.PRIMARY) {
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
		} else if (e.getButton() == MouseButton.SECONDARY) {
			switch (selection.getId()) {
			case "custom1":
				File image = new File(imagesDir.getAbsolutePath() + "\\" + images[0]);
				Optional<ButtonType> result = new Alert(Alert.AlertType.WARNING,
						"Are you sure you want to delete this image?", ButtonType.OK, ButtonType.CANCEL).showAndWait();
				if(result.get() == ButtonType.OK) {
					if (image.delete()) {
						System.out.println("Deleted user avatar: " + image.getAbsolutePath());
						initialize();
					}
				}
				break;
			case "custom2":
				File image1 = new File(imagesDir.getAbsolutePath() + "\\" + images[1]);
				Optional<ButtonType> result1 = new Alert(Alert.AlertType.WARNING,
						"Are you sure you want to delete this image?", ButtonType.OK, ButtonType.CANCEL).showAndWait();
				if(result1.get() == ButtonType.OK) {
					if (image1.delete()) {
						System.out.println("Deleted user avatar: " + image1.getAbsolutePath());
						initialize();
					}
				}
				break;
			case "custom3":
				File image2 = new File(imagesDir.getAbsolutePath() + "\\" + images[2]);
				Optional<ButtonType> result2 = new Alert(Alert.AlertType.WARNING,
						"Are you sure you want to delete this image?", ButtonType.OK, ButtonType.CANCEL).showAndWait();
				if(result2.get() == ButtonType.OK) {
					if (image2.delete()) {
						System.out.println("Deleted user avatar: " + image2.getAbsolutePath());
						initialize();
					}
				}
				break;
			case "custom4":
				File image3 = new File(imagesDir.getAbsolutePath() + "\\" + images[3]);
				Optional<ButtonType> result3 = new Alert(Alert.AlertType.WARNING,
						"Are you sure you want to delete this image?", ButtonType.OK, ButtonType.CANCEL).showAndWait();
				if(result3.get() == ButtonType.OK) {
					if (image3.delete()) {
						System.out.println("Deleted user avatar: " + image3.getAbsolutePath());
						initialize();
					}
				}
				break;
			case "custom5":
				File image4 = new File(imagesDir.getAbsolutePath() + "\\" + images[4]);
				Optional<ButtonType> result4 = new Alert(Alert.AlertType.WARNING,
						"Are you sure you want to delete this image?", ButtonType.OK, ButtonType.CANCEL).showAndWait();
				if(result4.get() == ButtonType.OK) {
					if (image4.delete()) {
						System.out.println("Deleted user avatar: " + image4.getAbsolutePath());
						initialize();
					}
				}
				break;
			default:
				break;
			}
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
