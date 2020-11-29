package com.kernaldrive.startupscreen;
import com.kernaldrive.gui.MainScreen;
import com.kernaldrive.gui.splashscreen.SplashScreen;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Stack;

public class StartupScreenController{

    //private Button folderButton, backButton, addGroupButton;
    private ArrayList<File> groupPaths;
    private ArrayList<MovieGroup> mediaGroups;
    private Color LIGHTBLUE = Color.rgb(153, 201, 255);
    private Color SIDEBARCOLOR = Color.rgb(32, 71, 102);
    private Color DARKBLUE = Color.rgb(26, 158, 197);

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
Button enterHomePage;

@FXML
TextField textField;

@FXML
ListView listView;

    public void loadStartupScreen(Stage primaryStage) throws Exception{

        groupPaths = new ArrayList<File>();
        mediaGroups = new ArrayList<MovieGroup>();
        anchorPane.setStyle("-fx-background-color: linear-gradient(to bottom left, rgba(40,43,82, 0.8) 20% , rgba(21,102,123, 0.8))");


        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();


        //IMPORTING CSS

        //CREATING A LISTVIEW OF THE PATH
        //ListView<String> folderPath = new ListView<>();
        listView.setLayoutX((screenWidth/30)*11);
        listView.setLayoutY((screenHeight/23)*15);
        listView.setPrefSize((screenWidth/30)*12.1, (screenHeight/20)*2.7);
        listView.getItems().add("Below is a List of Your Directory Paths:");
        listView.setStyle("-fx-text-fill: WHITE;");


        //Adding LOGO
        imageView.setFitHeight((screenHeight/3)*1.5);
        imageView.setPreserveRatio(true);
        imageView.setX((screenWidth/14)*2);
        imageView.setY((screenHeight/20)*0.2);
        imageView.setPickOnBounds(true);


        //ADDING TEXTFIELD-------------------------------
        textField.setPromptText("Enter Group Name...");
        textField.setFont(Font.loadFont(new FileInputStream("./varela/Varela-Regular.otf"), 25));
        textField.setStyle("-fx-text-fill: WHITE;");
        textField.setLayoutX((screenWidth/30)*6.5);
        textField.setLayoutY((screenHeight/20)*11.1);
        textField.setPrefWidth((screenWidth/30)*16.6);


        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");

        //folderButton = new Button();
        folderButton.setText("Add Directories");
        folderButton.setFont(Font.loadFont(new FileInputStream("./varela/Varela-Regular.otf"), 25));
        folderButton.setStyle("-fx-text-fill: WHITE;");
        folderButton.setLayoutX((screenWidth/30)*6.5);
        folderButton.setLayoutY((screenHeight/23)*15);
        folderButton.setPrefWidth((screenWidth/30)*4.1);

        //addGroupButton = new Button();
        addGroupButton.setText("Add Group");
        addGroupButton.setFont(Font.loadFont(new FileInputStream("./varela/Varela-Regular.otf"), 25));
        addGroupButton.setStyle("-fx-text-fill: WHITE;");
        addGroupButton.setLayoutX((screenWidth/30)*6.5);
        addGroupButton.setLayoutY((screenHeight/23)*17);
        addGroupButton.setPrefWidth((screenWidth/30)*4.1);

        //enter home page button
        enterHomePage.setText("Enter Home Page");
        enterHomePage.setFont(Font.loadFont(new FileInputStream("./varela/Varela-Regular.otf"), 25));
        enterHomePage.setStyle("-fx-text-fill: WHITE;");
        enterHomePage.setPrefWidth((screenWidth/30)*16.6);
        enterHomePage.setLayoutX((screenWidth/30)*6.5);
        enterHomePage.setLayoutY((screenHeight/23)*19);

        enterHomePage.setOnAction(new EventHandler<>() {      //if home button is clicked
            @Override
            public void handle(ActionEvent event) {
                SplashScreen splashScreen;
                try {
                    splashScreen = new SplashScreen(mediaGroups, primaryStage);
                    MainScreen mainScreen;
                    mainScreen = new MainScreen();
                    Scene splashScreenScene = splashScreen.getScene();

                    //primaryStage.initStyle(StageStyle.UTILITY);
                    primaryStage.show();
                    //This essentially controls the loading of the resources and the progress bar
                    splashScreen.setSplashScreen(mainScreen);
                    primaryStage.setScene(splashScreenScene);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        folderButton.setOnAction(new EventHandler<>() {      //if folder button is clicked
            @Override
            public void handle(ActionEvent event) {
                File selectedDirectory = directoryChooser.showDialog(primaryStage);

                if (selectedDirectory == null) {
                    //No Directory selected
                } else {
                    //textArea.append(selectedDirectory.getAbsolutePath());
                    //System.out.println(selectedDirectory.);

                    groupPaths.add(selectedDirectory);
                    listView.getItems().add(selectedDirectory.toString());

                }
            }
        });

        addGroupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addGroup(textField.getText());
                clearScreen(textField, listView);
            }
        });


    }

    public void addGroup(String groupName){
        //if(groupType == "Movies"){
        MovieGroup mediaGroup = new MovieGroup(groupName);
        copyPaths(mediaGroup);
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

    public void copyPaths(MovieGroup mediaGroup){
        for(File filePath : this.groupPaths){
            System.out.println(filePath.getPath());
            mediaGroup.addPath(filePath);
            mediaGroups.add(mediaGroup);
        }
    }

}
