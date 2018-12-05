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
import java.util.ArrayList;
import java.util.Arrays;

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
		    
		    TextField nameFilter = new TextField();
		    Button byName = new Button("Filter by resource name");
		    
		    Button all = new Button("Get all resources");
		    Button back = new Button("Back");
		    
		    FlowPane flowpane = new FlowPane(Orientation.VERTICAL);

		    flowpane.getChildren().addAll(items, typeFilter, byType, nameFilter, byName, all, back);

		    flowpane.setPadding(new Insets(5, 0, 5, 0));
		    flowpane.setVgap(20);
		    

		      
		    Scene scene = new Scene(flowpane, 700, 400);
		    primaryStage.setTitle("Browse for resources");
		    primaryStage.setScene(scene);
		    primaryStage.show();
		    
		    
		    
		    
		    
            
		    all.setOnAction(ev -> {
			  
		       ArrayList<Resource> allResources = manager.getResources();
		       ObservableList<String> itemNames = FXCollections.observableArrayList();
		       for (Resource r : allResources) {
		    	 itemNames.add(r.getTitle());

		       }
		       items.setItems(itemNames);

		       
		    });
		    
		    byType.setOnAction(ev -> {
		       items.setItems(null);
		       Class<Resource> selectedType = null;
		       
			   try {
		          selectedType = (Class<Resource>) Class.forName("resources." + typeFilter.getText());
			   } catch (ClassNotFoundException e) {
				  e.printStackTrace();
			   }
			   
		       ArrayList<Resource> resources = manager.getResourceByType(selectedType);
		       ObservableList<String> itemNames = FXCollections.observableArrayList();
			   for (Resource r : resources) {
			     itemNames.add(r.getTitle());
			     
			   }
		       items.setItems(itemNames);
		       
			    
			});
		    
			back.setOnAction(e -> {
				Dashboard instance = new Dashboard();
				instance.start(primaryStage);

			});
		    
		    
		    
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
			

}

