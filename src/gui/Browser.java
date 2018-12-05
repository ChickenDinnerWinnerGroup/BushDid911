package gui;
import java.io.File;

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

import javax.swing.JOptionPane;
import javax.swing.text.Position;

import application.Manager;
import javafx.application.Application;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
    
		    TextField typeFilter = new TextField();
		    Button byType = new Button("Filter by resource type");
		    
		    TextField IDFilter = new TextField();
		    Button byID = new Button("Filter by resource ID");
		    
		    Button all = new Button("Get all resources");
		    Button back = new Button("Back");
		    
		    FlowPane flowpane = new FlowPane(Orientation.VERTICAL);

		    flowpane.getChildren().addAll(items, typeFilter, byType, IDFilter, byID, all, back);

		    flowpane.setPadding(new Insets(5, 5, 5, 5));
		    flowpane.setVgap(20);
		    flowpane.setHgap(20);
		    

		      
		    Scene scene = new Scene(flowpane, 700, 400);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    primaryStage.setTitle("Browse for resources");
		    primaryStage.setScene(scene);
		    primaryStage.show();
		    
		    
		    
		    
		    
            
		    all.setOnAction(ev -> {
			   items.setItems(null);
		       ArrayList<Resource> allResources = manager.getResources();
		       ObservableList<String> itemNames = FXCollections.observableArrayList();
		       for (Resource r : allResources) {
		    	 itemNames.add("ID: " + Integer.toString(r.getID()) + " | Title: " + r.getTitle() + " | Year: " + Integer.toString(r.getYear()));

		       }
		       items.setItems(itemNames);

		       
		    });
		    
		    byType.setOnAction(ev -> {
		       items.setItems(null);
		       Class<Resource> selectedType = null;
		       
			   try {
		          selectedType = (Class<Resource>) Class.forName("resources." + typeFilter.getText());
			   } catch (ClassNotFoundException e) {
				  showInfoBox("Resource type not found", "Input Error");
			   }
			   
		       ArrayList<Resource> resources = manager.getResourceByType(selectedType);
		       ObservableList<String> itemNames = FXCollections.observableArrayList();
			   for (Resource r : resources) {
			     itemNames.add("ID: " + Integer.toString(r.getID()) + " | Title: " + r.getTitle() + " | Year: " + Integer.toString(r.getYear()));
			     
			   }
		       items.setItems(itemNames);
		       
			    
			});
		    
		    byID.setOnAction(ev -> {
		       items.setItems(null);
			   int id = 0;
			   try {
			      id = Integer.parseInt(IDFilter.getText());
			      Resource r = manager.getResourceByID(id);
				  ObservableList<String> itemNames = FXCollections.observableArrayList();
				  itemNames.add("ID: " + Integer.toString(r.getID()) + " | Title: " + r.getTitle() + " | Year: " + Integer.toString(r.getYear()));
				  items.setItems(itemNames);
			   } catch (NumberFormatException e) {
				  showInfoBox("ID is not a number", "Input Error");
			   }
			   
			   catch (NullPointerException e ){
				  showInfoBox("ID not recognised", "Input Error");
				   
			   }
			   	   
			   
		    	
		    });
		    
			back.setOnAction(e -> {
				Dashboard instance = new Dashboard();
				instance.start(primaryStage);

			});
		    
		    
		    
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public static void showInfoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
			

}

