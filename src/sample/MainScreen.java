package sample;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.HashMap;

public class MainScreen{
    private double screenWidth;
    private double screenHeight;
    private double sideBarWidth;
    private FXMLLoader loader;
    private Controller controller;

    public MainScreen(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        screenWidth = screenBounds.getWidth();
        screenHeight = screenBounds.getHeight();
        sideBarWidth = screenWidth / 6;
        loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("MainPage.fxml"));
    }

    public Scene getScene() throws IOException {
        Parent root = loader.load();
        Scene s = new Scene(root, screenWidth, screenHeight - 50);
        return s;
    }

    public void setMainScreen(){
        HashMap<String, String> groups = new HashMap<>();
        groups.put("Shows 1", "TV");
        groups.put("4K Movies", "Movie");
        groups.put("720p Movies", "Movie");

        HashMap<String, String> movies = new HashMap<>();
        movies.put("Avengers Endgame", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\endgamePoster.jpg");
        movies.put("IT", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\itPoster.jpg");
        movies.put("The Purge", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\purgePoster.jpeg");
        movies.put("Batman: The Dark Knight", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\darkKnightPoster.jpg");
        movies.put("Joker", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\jokerPoster.jpg");
        movies.put("The Pursuit of Happyness", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\pursuitPoster.jpg");
        movies.put("American Sniper", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\americanSniperPoster.jpg");
        controller = loader.getController();
        controller.setScreenHBox(screenWidth, screenHeight);
        controller.setSideBar(sideBarWidth, screenHeight, groups);
        controller.setMainPage((screenWidth / 6) * 5, screenHeight);
        controller.addLabelBox((screenWidth / 6) * 5, screenHeight, "TRENDING", movies);
    }


}
