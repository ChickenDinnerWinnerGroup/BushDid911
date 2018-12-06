package gui;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.text.Position;

import application.Manager;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import resources.Resource;

public class Browser extends Application {
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;
	private static final int BUTTON_MAX_WIDTH = 150;
	private static final int BUTTON_MAX_HEIGHT = 40;
	private Manager manager;
	
	public Browser() {
		manager = Manager.getInstance();
	}

	public void start(Stage primaryStage) {
		try {    
		    ListView<String> items = new ListView<String>();
		    items.setPrefWidth(350);
		    items.setPrefHeight(420);
    
		    TextField typeFilter = new TextField();
		    typeFilter.setPromptText("Enter the resource type");
		    Button byType = new Button("Filter by resource type");
		    
		    TextField IDFilter = new TextField();
		    IDFilter.setPromptText("Enter the resource's ID number");
		    Button byID = new Button("Filter by resource ID");
		    
		    Button all = new Button("Get all resources");
		    Button logOut = new Button("Log out");
		    Button back = new Button("Back");
		    
		    Rectangle thumbnail = new Rectangle(123, 159);
		    
		    //we want the thumbnail box to be invisible when nothing has been loaded into it yet
		    thumbnail.setFill(javafx.scene.paint.Paint.valueOf("#cccccc"));
		    
		    FlowPane flowpane = new FlowPane(Orientation.VERTICAL);

		    flowpane.getChildren().addAll(back, logOut, items, typeFilter, byType, 
		    		IDFilter, byID, all, thumbnail);

		    flowpane.setPadding(new Insets(10, 10, 10, 10));
		    flowpane.setVgap(20);
		    flowpane.setHgap(20);
		    	      
		    Scene scene = new Scene(flowpane, 700, 450);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    primaryStage.setTitle("Browse for resources");
		    primaryStage.setScene(scene);
		    primaryStage.show();
		    		           
		    all.setOnAction(ev -> {
		       ArrayList<Resource> allResources = manager.getResources();
		       ObservableList<String> itemNames = FXCollections.observableArrayList();
		       for (Resource r : allResources) {
		    	 itemNames.add("ID: " + Integer.toString(r.getID()) + ", Title: " + r.getTitle() 
		    	 + ", Year: " + Integer.toString(r.getYear()) + ", Thumbnail: " + r.getThumbnail());

		       }
		       items.setItems(itemNames);	       
		    });
		    
		    byType.setOnAction(ev -> {
		       Class<Resource> selectedType = null;
		       String typeFilterText = typeFilter.getText();
		       
			   try {
				  //auto-capitalise the first letter for a class name as user might forget it
			      typeFilterText = typeFilterText.substring(0, 1).toUpperCase() + typeFilterText.substring(1);
			      
		          selectedType = (Class<Resource>) Class.forName("resources." + typeFilterText);
			   } catch (ClassNotFoundException e) {
				  showInfoBox("Resource type not found.", "Input Error");
			   }
			   catch (NoClassDefFoundError e) {
				   showInfoBox("Class not found - double check the character casing.", "Input Error");
			   }
			   
			   catch (StringIndexOutOfBoundsException e) {
				   showInfoBox("The field was left blank, please try again.", "Input Error");
			   }
		       ArrayList<Resource> resources = manager.getResourceByType(selectedType);
		       ObservableList<String> itemNames = FXCollections.observableArrayList();
			   for (Resource r : resources) {
			     itemNames.add("ID: " + Integer.toString(r.getID()) + ", Title: " + r.getTitle() +
			    		 ", Year: " + Integer.toString(r.getYear()) + ", Thumbnail: " + r.getThumbnail());
			     
			   }
		       items.setItems(itemNames);	    
			});
		    
		    byID.setOnAction(ev -> {
			   try {
			      int id = Integer.parseInt(IDFilter.getText());
			      Resource r = manager.getResourceByID(id);
				  ObservableList<String> itemNames = FXCollections.observableArrayList();
				  itemNames.add("ID: " + Integer.toString(r.getID()) + ", Title: " + r.getTitle() +
						  ", Year: " + Integer.toString(r.getYear()) + ", Thumbnail: " + r.getThumbnail());
				  items.setItems(itemNames);
			   } catch (NumberFormatException e) {
				  showInfoBox("Value entered is not a number.", "Input Error");
			   }
			   catch (NullPointerException e){
				  showInfoBox("ID not recognised.", "Input Error");
				   
			   }	    	
		    });
		    
			back.setOnAction(e -> {
				Dashboard instance = new Dashboard();
				instance.start(primaryStage);
			});
		    
			logOut.setOnAction(e -> {
				LogInScr instance = new LogInScr();
				instance.start(primaryStage);
			});
			
			//this allows the user to see the thumbnail for a resource once it has been clicked
			items.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			    	if (newValue != null) {
					    File imagesDir = new File("./images/resources/");
						String path = "File:" + imagesDir.getAbsolutePath() + "\\" + getThumbnailName(newValue);
						Image thumbnailImg = new Image(path);
						thumbnail.setFill(new ImagePattern(thumbnailImg));
			    	}			    						
			    }
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	//method for showing error and messages to the user in pop-up windows
	public static void showInfoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	
	public String getThumbnailName(String listViewSelection) {
		String name = "";
		String[] segments = listViewSelection.split(",");
		String nameSection = segments[3];
		boolean startPoint = false;
		for (int i = 0; i < nameSection.length(); i++) {
			if (nameSection.charAt(i) == ':') {
				startPoint = true;
			}
			else if (startPoint == true) {
				name += nameSection.charAt(i);
			}
		}
		name = name.substring(1, name.length());
		return name;
	}
}

