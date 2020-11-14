package com.kernaldrive.gui;

import com.kernaldrive.metadata.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainScreen{
    private ArrayList<MovieGroup> movieGroups;
    private double screenWidth;
    private double screenHeight;
    private FXMLLoader loader;
    private MainPageController mainPageController;
    private Scene screenScene;
    private HashMap<String, String> groups;
    private HashMap<String, String> movies;


    public MainScreen() throws MalformedURLException {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        screenWidth = screenBounds.getWidth();
        screenHeight = screenBounds.getHeight() - 30;
        loader = new FXMLLoader();
        loader.setLocation(new URL("file:///C:\\Users\\HP\\IdeaProjects\\untitled\\src\\main\\java\\com\\kernaldrive\\gui\\MainScreen.fxml"));
        groups = new HashMap<>();
        movies = new HashMap<>();
        //System.out.println("True");
    }

    public void setSideBar(MediaManager manager){
        movieGroups = manager.getMovieGroups();
        for (MovieGroup movieGroup: movieGroups){
            groups.put(movieGroup.getName(), "Movie");
            for (Movie movie:movieGroup.getMovies()) {
                movies.put(movie.getTitle(), movie.getPosterPath());
            }
        }
        mainPageController = loader.getController();
        mainPageController.setSideBar(screenWidth / 6, screenHeight, groups);
        /*
        MainPageController mainPageController = loader.getController();
        //mainPageController.testFunction();
        mainPageController.setSideBar(screenWidth / 6, screenHeight, groups);
        mainPageController.setContent((screenWidth/6) * 5, screenHeight, movies);*/
    }

    public void setContentPage(){
        mainPageController.setContent((screenWidth/6) * 5, screenHeight, movies);
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
