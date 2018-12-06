package gui;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import application.Manager;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import resources.Resource;

/**
*
* @authors Guillaume + Sarang
*
*/

public class Browser extends Application {
	private Manager manager;
	
	public Browser() {
		manager = Manager.getInstance();
	}

	@SuppressWarnings("unchecked")
	public void start(Stage primaryStage) {
		try {    
		    ListView<String> items = new ListView<String>();
		    items.setPrefWidth(350);
		    items.setPrefHeight(477);
    
		    TextField typeFilter = new TextField();
		    typeFilter.setPromptText("Enter the resource type");
		    Button byType = new Button("Filter by resource type");
		    
		    TextField IDFilter = new TextField();
		    IDFilter.setPromptText("Enter the resource's ID number");
		    Button byID = new Button("Filter by resource ID");
		    
		    Button all = new Button("Get all resources");
		    Button takeOut = new Button("Take out selected");
		    Button logOut = new Button("Log out");
		    Button back = new Button("Back");
		    
		    Rectangle thumbnail = new Rectangle(123, 159);
		    
		    //we want the thumbnail box to be invisible when nothing has been loaded into it yet
		    thumbnail.setFill(javafx.scene.paint.Paint.valueOf("#cccccc"));
		    
		    FlowPane flowpane = new FlowPane(Orientation.VERTICAL);

		    flowpane.getChildren().addAll(back, logOut, items, typeFilter, byType, 
		    		IDFilter, byID, all, takeOut, thumbnail);

		    flowpane.setPadding(new Insets(10, 10, 10, 10));
		    flowpane.setVgap(20);
		    flowpane.setHgap(20);
		    	      
		    Scene scene = new Scene(flowpane, 720, 500);
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
				  showInfoBox("Resource type not found.");
			   }
			   catch (NoClassDefFoundError e) {
				   showInfoBox("Class not found - double check the character casing.");
			   }
			   
			   //if the user enters nothing
			   catch (StringIndexOutOfBoundsException e) {
				   showInfoBox("The field was left blank, please try again.");
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
				  showInfoBox("Value entered is not a number.");
			   }
			   catch (NullPointerException e){
				  showInfoBox("ID not recognised.");
				   
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
			
			//button user selects to take out a resource
			takeOut.setOnAction(e -> {
				
				//TEST
				String selected = items.getSelectionModel().selectedItemProperty().get();
				showInfoBox("You are going to take out: " + selected);
				
				//ADD ACTIONS ASSOCIATED WITH TAKING OUT A RESOURCE HERE
			});
			
			//this allows the user to see the thumbnail for a resource once it has been clicked
			items.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					 File imagesDir = new File("./images/resources/");
					 String fileName = getThumbnailName(newValue);
					 if (fileName.equals("none") || newValue == null) {
					    String path = "File:" + imagesDir.getAbsolutePath() + "\\" + "blank.png";					
						Image thumbnailImg = new Image(path);
						thumbnail.setFill(new ImagePattern(thumbnailImg));
					 }
					 else {
					    String path = "File:" + imagesDir.getAbsolutePath() + "\\" + fileName;					
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
	public static void showInfoBox(String infoMessage)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Notification", JOptionPane.INFORMATION_MESSAGE);
    }
	
	public String getThumbnailName(String listViewSelection) {
		String name = "";
		try {		
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
		}
		catch (NullPointerException e) {
			System.out.println("The selected item in null.");
		}			
		return name;
	}
}

