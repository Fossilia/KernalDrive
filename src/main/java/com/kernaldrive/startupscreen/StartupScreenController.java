package com.kernaldrive.startupscreen;
import com.kernaldrive.metadata.MediaGroup;

import com.kernaldrive.metadata.MovieGroup;
import com.kernaldrive.metadata.ShowGroup;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;

//FOR FONTS
import javafx.scene.text.Font;

//FOR IMAGES
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;

import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.awt.*;
import java.awt.TextArea;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

public class StartupScreenController extends Application{

    //private Button folderButton, backButton, addGroupButton;
    private ArrayList<File> groupPaths;
    private ArrayList<MediaGroup> mediaGroups;

@FXML
AnchorPane anchorPane;

@FXML
ImageView imageView;

@FXML
Button button1;

@FXML
Button folderButton;

@FXML
Button addGroupButton;

@FXML
TextField textField1;

@FXML
TextField textField2;

    @Override
    public void start(Stage primaryStage) throws Exception {

        groupPaths = new ArrayList<File>();
        mediaGroups = new ArrayList<MediaGroup>();

        primaryStage.initStyle(StageStyle.UTILITY);     //removes minimize and fullscreen button

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        primaryStage.setTitle("Homepage - KernalDrive");

        Pane layout = new Pane();

        Scene scene = new Scene(layout, 1920, 1080);

        //IMPORTING CSS
        scene.getStylesheets().add("C:\\Users\\ahmed\\IdeaProjects\\KernalDrive\\src\\main\\java\\com\\kernaldrive\\startupscreen\\list-view.css");

        //CREATING A LISTVIEW OF THE PATH
        ListView<String> folderPath = new ListView<>();
        folderPath.setLayoutX((screenWidth/30)*11);
        folderPath.setLayoutY((screenHeight/23)*15);
        folderPath.setPrefSize((screenWidth/30)*12.1, (screenHeight/20)*2.7);
        folderPath.getItems().add("Below is a List of Your Directory Paths:");
        folderPath.setStyle("-fx-text-fill: #0054B4;");

        layout.getChildren().add(folderPath);

        layout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        //ADDING TEXTFIELD-------------------------------

        //TextField textField = new TextField ();
        textField1.setPromptText("Enter Group Name...");
        textField1.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 25));
        textField1.setStyle("-fx-text-fill: #0054B4;");
        textField1.setLayoutX((screenWidth/30)*6.5);
        textField1.setLayoutY((screenHeight/20)*11.1);
        textField1.setPrefWidth(700);
        layout.getChildren().add(textField1);

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");

        //folderButton = new Button();
        folderButton.setText("Add Directories");
        folderButton.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 25));
        folderButton.setStyle("-fx-text-fill: #0054b4;");
        folderButton.setLayoutX((screenWidth/30)*6.5);
        folderButton.setLayoutY((screenHeight/23)*15);
        //folderButton.setMinWidth();
        layout.getChildren().addAll(folderButton);

        addGroupButton = new Button();
        addGroupButton.setText("Add Group");
        addGroupButton.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 25));
        addGroupButton.setStyle("-fx-text-fill: #0054b4;");
        addGroupButton.setLayoutX((screenWidth/30)*6.5);
        addGroupButton.setLayoutY((screenHeight/23)*17);
        layout.getChildren().addAll(addGroupButton );

        folderButton.setOnAction(new EventHandler<ActionEvent>() {      //if folder button is clicked
            @Override
            public void handle(ActionEvent event) {
                File selectedDirectory = directoryChooser.showDialog(primaryStage);

                if(selectedDirectory == null){
                    //No Directory selected
                }else{
                    //textArea.append(selectedDirectory.getAbsolutePath());
                    //System.out.println(selectedDirectory.);

                    groupPaths.add(selectedDirectory);
                    folderPath.getItems().add(selectedDirectory.toString());

                }
            }
        });

        addGroupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addGroup(textField1.getText());
                clearScreen(textField1, folderPath);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);        //starts program as full screen
        primaryStage.show();

    }

    public void addGroup(String groupName){
        //if(groupType == "Movies"){
        MediaGroup mediaGroup = new MovieGroup(groupName);
        copyPaths(mediaGroup.getPaths());
            //printGroups(mediaGroup.getPaths());
        //}

        /*if(groupType == "TV"){
            MediaGroup mediaGroup = new ShowGroup(groupName);
            copyPaths(mediaGroup.getPaths());
        }
         */
    }

    public void clearScreen(TextField textField, ListView folderPath){
        textField.clear();
        groupPaths.clear();
        folderPath.getItems().clear();
    }

    private void printGroups(ArrayList<File> groupPaths){
        for(File a : groupPaths){
            System.out.println(a);
        }
    }

    public void copyPaths(ArrayList<File> groupPaths){
        for(File filePath : this.groupPaths){
            groupPaths.add(filePath);
        }
    }

    public static void main(String[] args) {
        launch(args);   //sets up program as javafx application
    }

}
