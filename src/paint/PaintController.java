package paint;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import application.Manager;
import gui.AvatarSelector;
import gui.Dashboard;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PaintController {
	private ArrayList<DrawTool> tools;
	private int currentTool = 0;
	private GraphicsContext graphicsContext;
	private File imagesDir;

	@FXML
	private Button saveButton;
	@FXML
	private Button clearButton;
	@FXML
	private Button exitButton;
	@FXML
	private Button avatarButton;
	@FXML
	private ColorPicker mainColourPicker;
	@FXML
	private ColorPicker fillColourPicker;
	@FXML
	private Slider lineWidthSlider;
	@FXML
	private Canvas canvas;

	public void initialize() {
		this.tools = new ArrayList<DrawTool>();
		tools.add(new StraightLineTool());
		tools.add(new CircleTool());
		tools.add(new RectangleTool());
		tools.add(new FreeDrawTool());
		tools.add(new RubberTool());

		graphicsContext = canvas.getGraphicsContext2D();
		mainColourPicker.setValue(Color.BLACK);

		graphicsContext.setStroke(mainColourPicker.getValue());
		graphicsContext.setFill(fillColourPicker.getValue());

		imagesDir = new File("./images/avatars/");
		if (!imagesDir.exists()) {
			System.out.println("Directory for storage of avatars doesnt exist. Creating now.");

			imagesDir.mkdirs();
			try {
				Files.copy(Paths.get("images/avatars/none.png"), Paths.get(imagesDir.getCanonicalPath() + "/none.png"),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				System.out.println(
						"An error occured while attempting to create the directory structure for the paint system.");
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void handleToolBoxButton(ActionEvent e) {
		String tool = ((Button) e.getSource()).getText().toLowerCase();
		System.out.println(tool);
		for (int i = 0; i < tools.size(); i++) {
			if (tools.get(i).getName().equalsIgnoreCase(tool)) {
				currentTool = i;
			}
		}
	}

	@FXML
	private void handleMainColourPicker(ActionEvent e) {
		graphicsContext.setStroke(mainColourPicker.getValue());
	}

	@FXML
	private void handleFillColourPicker(ActionEvent e) {
		graphicsContext.setFill(fillColourPicker.getValue());
	}

	@FXML
	private void handleSave(ActionEvent event) {
		int i = 1;
		if (i == 0) {
			new Alert(Alert.AlertType.ERROR,
					"You already have 5 custom avatars! \nIn order to use save this please delete one of your existing custom avatars and try again.")
							.showAndWait();
		}
		try {
			Manager m = Manager.getInstance();
			File saveFile = new File(imagesDir.getCanonicalPath() + "/" + m.getCurrentUser().getUsername() + "-"
					+ generateRandomFileName() + ".png");

			WritableImage wi = new WritableImage(600, 400);

			SnapshotParameters sp = new SnapshotParameters();
			sp.setFill(Color.TRANSPARENT);

			ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(sp, wi), null), "png", saveFile);

			if (saveFile.exists()) {
				new Alert(Alert.AlertType.INFORMATION,
						"Your avatar has been saved. \nIn order to view all saved avatars and change your avater, please visit the avatar selector.")
								.showAndWait();
			}
		} catch (IOException e) {
			System.out.println("An error occured attempting to save the canvas to disk.");
			e.printStackTrace();
		}
	}

	@FXML
	private void handleClear(ActionEvent e) {
		graphicsContext.clearRect(0, 0, 600, 400);
	}

	@FXML
	private void handleExit(ActionEvent e) {
		Stage primaryStage = (Stage) exitButton.getScene().getWindow();
		Dashboard dashboard = new Dashboard();
		dashboard.start(primaryStage);
	}

	@FXML
	private void handleAvatarSelector(ActionEvent e) {
		Stage primaryStage = (Stage) exitButton.getScene().getWindow();
		AvatarSelector avatarSelector = new AvatarSelector();
		avatarSelector.start(primaryStage);
	}

	@FXML
	private void handleCanvasMousePress(MouseEvent e) {
		tools.get(currentTool).onMousePress(e, graphicsContext);
	}

	@FXML
	private void handleCanvasMouseRelease(MouseEvent e) {
		tools.get(currentTool).onMouseRelease(e, graphicsContext);
	}

	@FXML
	private void handleCanvasMouseDrag(MouseEvent e) {
		tools.get(currentTool).onMouseDrag(e, graphicsContext);
	}

	@FXML
	private void handleLineWidthSliderChange(MouseEvent e) {
		graphicsContext.setLineWidth(lineWidthSlider.getValue());
	}

	private String generateRandomFileName() {
		String possibleCharacters = "0123456789abcdefghijklmnopqrstuvwxyz";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			int character = (int) (Math.random() * possibleCharacters.length());
			builder.append(possibleCharacters.charAt(character));
		}
		return builder.toString();
	}
}
