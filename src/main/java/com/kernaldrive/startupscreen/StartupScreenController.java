package com.kernaldrive.startupscreen;
import com.kernaldrive.filescanning.GroupDBManager;
import com.kernaldrive.gui.MainScreen;
import com.kernaldrive.gui.splashscreen.SplashScreen;
import com.kernaldrive.metadata.MovieGroup;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;

//FOR FONTS
import javafx.scene.text.Font;

//FOR IMAGES
import javafx.scene.image.ImageView;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;

import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StartupScreenController{

    //private Button folderButton, backButton, addGroupButton;
    private ArrayList<File> groupPaths;
    private ArrayList<MovieGroup> movieGroups;
    GroupDBManager groupDB;
    ResultSet dbGroups;

    private Color LIGHTBLUE = Color.rgb(153, 201, 255);
    private Color SIDEBARCOLOR = Color.rgb(32, 71, 102);
    private Color DARKBLUE = Color.rgb(26, 158, 197);

    @FXML AnchorPane anchorPane;

    @FXML ImageView imageView;

    @FXML Button button1;

    @FXML Button folderButton;

    @FXML Button addGroupButton;

    @FXML Button enterHomePage;

    @FXML TextField textField;

    @FXML ListView listView;

    @FXML ListView listView1;

    public void loadStartupScreen(Stage primaryStage) throws Exception{

        groupPaths = new ArrayList<File>();
        movieGroups = new ArrayList<MovieGroup>();

        anchorPane.setStyle("-fx-background-color: linear-gradient(to bottom left, rgba(40,43,82, 0.8) 20% , rgba(21,102,123, 0.8))");

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();


        //IMPORTING CSS

        //CREATING A LISTVIEW OF THE PATH
        listView.setLayoutX((screenWidth/30)*15.1);
        listView.setLayoutY((screenHeight/23)*13.5);
        listView.setPrefSize((screenWidth/30)*8, (screenHeight/20)*2.7);
        listView.getItems().add("Directories:");
        listView.setStyle("-fx-text-fill: WHITE;");

        //CREATING A LISTVIEW OF THE GROUP
        listView1.setLayoutX((screenWidth/30)*6.5);
        listView1.setLayoutY((screenHeight/23)*13.5);
        listView1.setPrefSize((screenWidth/30)*8, (screenHeight/20)*2.7);
        listView1.getItems().add("Groups:");
        listView1.setStyle("-fx-text-fill: WHITE;");



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
        textField.setLayoutY((screenHeight/20)*9.8);
        textField.setPrefWidth((screenWidth/30)*16.6);


        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");

        //folderButton = new Button();
        folderButton.setText("Add Directories");
        folderButton.setFont(Font.loadFont(new FileInputStream("./varela/Varela-Regular.otf"), 25));
        folderButton.setStyle("-fx-text-fill: WHITE;");
        folderButton.setLayoutX((screenWidth/30)*15.1);
        folderButton.setLayoutY((screenHeight/23)*17.2);
        folderButton.setPrefWidth((screenWidth/30)*8);

        //addGroupButton = new Button();
        addGroupButton.setText("Add Group");
        addGroupButton.setFont(Font.loadFont(new FileInputStream("./varela/Varela-Regular.otf"), 25));
        addGroupButton.setStyle("-fx-text-fill: WHITE;");
        addGroupButton.setLayoutX((screenWidth/30)*6.5);
        addGroupButton.setLayoutY((screenHeight/23)*17.2);
        addGroupButton.setPrefWidth((screenWidth/30)*8);

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
                    splashScreen = new SplashScreen(movieGroups, primaryStage);
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

        addGroupButton.setOnAction(new EventHandler<ActionEvent>() { //if add group button is clicked
            @Override
            public void handle(ActionEvent event) {
                addGroup(textField.getText());
                listView1.getItems().add(textField.getText());
                clearScreen(textField, listView);
            }
        });

        //building group table
        try {
            groupDB = new GroupDBManager();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error with creating Group manager table");
            //e.printStackTrace();
        }

        //try to get group data from table
        try {
            dbGroups = groupDB.getGroups();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error with getting group info from database.");
            e.printStackTrace();
        }

        if(dbGroups.next()){ //if the group table exists (the program was booted once before)
            MovieGroup group = new MovieGroup(dbGroups, groupDB.getGroupPaths(dbGroups.getString(2))); //load group data from database
            movieGroups.add(group); //add group
            listView1.getItems().add(group.getName()); //add the group name to added group list
            while(dbGroups.next()){
                group = new MovieGroup(dbGroups, groupDB.getGroupPaths(dbGroups.getString(2))); //load group data from database
                movieGroups.add(group); //add group
                System.out.println(group.getName());
                listView1.getItems().add(group.getName()); //add the group name to added group list
            }
            groupDB.closeConnection();
        }
    }

    public void addGroup(String groupName){
        //if(groupType == "Movies"){
        MovieGroup movieGroup = new MovieGroup(groupName);
        copyPaths(movieGroup);
        try {
            groupDB.addGroup(movieGroup); //add group to database
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
            movieGroups.add(mediaGroup);
        }
    }

}
