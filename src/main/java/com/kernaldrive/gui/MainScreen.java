package com.kernaldrive.gui;

import com.kernaldrive.filescanning.DatabaseManager;
import com.kernaldrive.metadata.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainScreen{
    private ArrayList<MovieGroup> movieGroups;
    private double screenWidth;
    private double screenHeight;
    private FXMLLoader loader;
    private MainPageController mainPageController;
    private Scene screenScene;

    public MainScreen() throws MalformedURLException {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        screenWidth = screenBounds.getWidth();
        screenHeight = screenBounds.getHeight();
        loader = new FXMLLoader();
        loader.setLocation(new URL("file:///C:\\Users\\ahmed\\IdeaProjects\\KernalDrive\\src\\main\\java\\com\\kernaldrive\\gui\\MainScreen.fxml"));
    }

    public void setSideBar(MediaManager manager){
        movieGroups = manager.getMovieGroups();
        mainPageController = loader.getController();
        mainPageController.setSideBar(screenWidth / 6, screenHeight, movieGroups);
    }

    public void setContentPage(){
        mainPageController.setContent();
    }

    public void displayPage(Stage primaryStage, Scene scene) throws IOException {
        primaryStage.setScene(scene);
        mainPageController.fixPane();
    }


    public Scene getMainScreenScene() throws IOException {
        Parent root = loader.load();
        screenScene = new Scene(root, screenWidth, screenHeight - 20);
        return screenScene;
    }
}
