package com.kernaldrive.gui;

import com.kernaldrive.filescanning.DatabaseManager;
import com.kernaldrive.metadata.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainScreen {
    private ArrayList<MovieGroup> movieGroups;
    private double screenWidth;
    private double screenHeight;
    private FXMLLoader loader;
    private Scene screenScene;

    public MainScreen() throws MalformedURLException {
        MediaManager manager = new MediaManager();
        movieGroups = manager.getMovieGroups();

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        screenWidth = screenBounds.getWidth();
        screenHeight = screenBounds.getHeight();
        loader = new FXMLLoader();
        loader.setLocation(new URL("file:///C:\\Users\\Faisal\\Documents\\GitHub\\KernalDrive-v2\\src\\main\\java\\com\\kernaldrive\\gui\\MainScreen.fxml"));
    }

    public void setScreen(){
        HashMap<String, String> groups = new HashMap<>();
        HashMap<String, String> movies = new HashMap<>();
        for (MovieGroup movieGroup: movieGroups){
            groups.put(movieGroup.getName(), "Movie");
            for (Movie movie:movieGroup.getMovies()) {
                movies.put(movie.getTitle(), movie.getPosterPath());
            }
        }
        /*movies.put("American Sniper", "C:\\Users\\HP\\IdeaProjects\\untitled\\americanSniperPoster.jpg");
        movies.put("IT", "C:\\Users\\HP\\IdeaProjects\\untitled\\itPoster.jpg");
        movies.put("The Purge", "C:\\Users\\HP\\IdeaProjects\\untitled\\purgePoster.jpeg");
        movies.put("Batman: The Dark Knight", "C:\\Users\\HP\\IdeaProjects\\untitled\\darkKnightPoster.jpg");
        movies.put("Joker", "C:\\Users\\HP\\IdeaProjects\\untitled\\jokerPoster.jpg");
        movies.put("The Pursuit of Happyness", "C:\\Users\\HP\\IdeaProjects\\untitled\\pursuitPoster.jpg");
        movies.put("Avengers Endgame", "C:\\Users\\HP\\IdeaProjects\\untitled\\endgamePoster.jpg");
        movies.put("Batman: The Dark Knight Rises", "C:\\Users\\HP\\IdeaProjects\\untitled\\darkKnightRisesPoster.jpg");
        movies.put("Batman Begins", "C:\\Users\\HP\\IdeaProjects\\untitled\\batmanBegins.jpg");
        movies.put("Ocean's 11", "C:\\Users\\HP\\IdeaProjects\\untitled\\oceansElevenPoster.jpg");
        movies.put("Kung Fu Panda", "C:\\Users\\HP\\IdeaProjects\\untitled\\kungFuPandaPoster.jpg");
        movies.put("Toy Story", "C:\\Users\\HP\\IdeaProjects\\untitled\\toyStoryPoster.jpg");
        movies.put("Knives Out", "C:\\Users\\HP\\IdeaProjects\\untitled\\knivesOutPoster.jpg");*/
        MainPageController mainPageController = loader.getController();
        //mainPageController.testFunction();
        mainPageController.setSideBar(screenWidth / 6, screenHeight, groups);
        mainPageController.setContent((screenWidth/6) * 5, screenHeight, movies);
    }


    public Scene getMainScreenScene() throws IOException {
        Parent root = loader.load();
        screenScene = new Scene(root, screenWidth, screenHeight - 20);
        return screenScene;
    }
}
