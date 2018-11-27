package gui;

import application.Manager;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import users.User;

public class LogInScr extends Application {
	private static final int WINDOW_WIDTH= 600 ;
	private static final int WINDOW_HEIGHT= 400;
	private Manager manager;

	public LogInScr() {
		manager = Manager.getInstance();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			scene1(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void scene1(Stage primaryStage) {
		//scene 1
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);

		HBox center = new HBox();
		center.setAlignment(Pos.CENTER);
		HBox centerTwo = new HBox();
		centerTwo.setAlignment(Pos.CENTER);

		TextField num1= new TextField();
		num1.setMaxWidth(150);

		Label userID = new Label("Username: ");
		Button logIn = new Button("Log in");
		Button signUp = new Button("Sign up");
		Label label = new Label();

		root.setSpacing(30);
		center.getChildren().addAll(userID,num1,label);
		centerTwo.setSpacing(30);
		centerTwo.getChildren().addAll(signUp,logIn);

		root.getChildren().addAll(center,centerTwo);

		logIn.setOnAction(e -> {
			String username = "";
			if(num1.getText().isEmpty()) {
				label.setText("information is missing");
				label.setTextFill(Color.web("#B22222"));
			}else {
				username = num1.getText();
				Manager m = Manager.getInstance();
				if(m.authenticate(username)) {
					System.out.println("User was found in the database!");
					Dashboard dash = new Dashboard();
					dash.start(primaryStage);
				} else {
					System.out.println("User was not found in the database!");
				}
			}
		});
		//when a user press' on a sign up button
		signUp.setOnAction(e -> scene2(primaryStage));

		Scene scene = new Scene(root,WINDOW_WIDTH,WINDOW_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());


		//I(mohamed) had issues with the logo file being found so i just commented them out so i could run the program
		//primaryStage.getIcons().add(new Image("logo.png"));
		primaryStage.setTitle("Tawe-Lib");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void scene2(Stage primaryStage) {
		//scene2
		GridPane center = new GridPane();
		center.setAlignment(Pos.CENTER);
		center.setVgap(20);
		center.setHgap(20);
		BorderPane root = new BorderPane();
		VBox root2 = new VBox();
		root2.setAlignment(Pos.CENTER);
		root2.setSpacing(20);

		//username entry
		Label name = new Label("username");
		name.setMaxWidth(70);
		TextField nameFld= new TextField();
		nameFld.setMaxWidth(150);

		//firtname entry
		Label forename = new Label("first name");
		forename.setMaxWidth(70);
		TextField fNameFld= new TextField();
		fNameFld.setMaxWidth(150);

		//surname entry
		Label surname = new Label("surname");
		surname.setMaxWidth(70);
		TextField sNameFld= new TextField();
		sNameFld.setMaxWidth(150);

		//address entry
		Label address = new Label("address");
		address.setMaxWidth(70);
		TextField addressFld= new TextField();
		addressFld.setMaxWidth(150);

		//postcode entry
		Label postcode = new Label("postcode");
		postcode.setMaxWidth(70);
		TextField postcodeFld= new TextField();
		postcodeFld.setMaxWidth(150);

		//phone Number entry
		Label phoneNumber = new Label("mobile");
		phoneNumber.setMaxWidth(70);
		TextField phoneNumberFld= new TextField();
		postcodeFld.setMaxWidth(150);


		//valid label
		Label label1 = new Label();

		///create account button

		Button createAccount = new Button("Create Account");
		createAccount.maxWidth(100);


		Button back = new Button("Back");


		center.add(name, 0, 0);
		center.add(nameFld, 1, 0);
		center.add(forename, 0, 1);
		center.add(fNameFld, 1, 1);
		center.add(surname, 0, 2);
		center.add(sNameFld, 1, 2);
		center.add(address, 0, 3);
		center.add(addressFld, 1, 3);
		center.add(postcode, 0, 4);
		center.add(postcodeFld, 1, 4);
		center.add(phoneNumber, 0, 5);
		center.add(phoneNumberFld, 1, 5);
		center.add(createAccount, 0, 6);
		center.add(back, 1, 6);
		center.add(label1, 0, 7);

		//add the data to the root
		//add the root to the scene to be displayed
		root.setCenter(center);
		Scene scene2 = new Scene(root,WINDOW_WIDTH,WINDOW_HEIGHT);

		createAccount.setOnAction(e-> {

			String username = "";
			String fullName = "";
			String fAddress = "";
			String number = "";
			boolean[] fldValues = new boolean[6];
			fldValues[0] = isNotEmpty(nameFld);
			fldValues[1] = isNotEmpty(fNameFld);
			fldValues[2] = isNotEmpty(sNameFld);
			fldValues[3] = isNotEmpty(addressFld);
			fldValues[4] = isNotEmpty(postcodeFld);
			fldValues[5] = isNotEmpty(phoneNumberFld);
			int emptyFld = anyFalse(fldValues);
			if(emptyFld >0){
			 	label1.setText("information is missing");
				label1.setTextFill(Color.web("#B22222"));
			 }else{
				username = nameFld.getText();
			 	fullName = fNameFld.getText() + " " + sNameFld.getText();
				fAddress = addressFld.getText() + ";" + postcodeFld.getText();
				number = phoneNumberFld.getText();
				User u = new User(username, fNameFld.getText(), sNameFld.getText(), fAddress, number, "1.png");
				manager.createUser(u);
				if(manager.authenticate(u.getUsername())) {
					System.out.println("Created user and logged in successfully");
					Dashboard dash = new Dashboard();
					dash.start(primaryStage);
				} else {
					System.out.println("Unable to create and login to user, check console logs.");
				}
			 }
		});
		back.setOnAction(e-> scene1(primaryStage));
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		//primaryStage.getIcons().add(new Image("logo.png"));
		primaryStage.setTitle("Tawe-Lib");
		primaryStage.setScene(scene2);
	}

	private int anyFalse(boolean[] fldVals) {
		int i =0;
		for(int j=0; j < fldVals.length-1;j++) {
			if(fldVals[j] == false) {
				i++;
			}
		}
		return i;
	}

	private boolean isNotEmpty(TextField fld) {
		if(fld.getText().isEmpty()) {
			return false;
		}else {
			return true;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}