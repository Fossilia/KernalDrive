package sample;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.HashMap;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Kernal Drive");

        MainScreen mainScreen = new MainScreen();
        Scene mainScreenScene = mainScreen.getScene();
        primaryStage.setScene(mainScreenScene);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();

        mainScreen.setMainScreen();
        /*Controller c = loader.getController();
        HashMap<String, String> movies = new HashMap<>();
        movies.put("Avengers Endgame", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\endgamePoster.jpg");
        movies.put("IT", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\itPoster.jpg");
        movies.put("The Purge", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\purgePoster.jpeg");
        movies.put("Batman: The Dark Knight", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\darkKnightPoster.jpg");
        movies.put("Joker", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\jokerPoster.jpg");
        movies.put("The Pursuit of Happyness", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\pursuitPoster.jpg");
        movies.put("American Sniper", "C:\\Users\\HP\\IdeaProjects\\RandomTest\\americanSniperPoster.jpg");

       // c.setLabelBox((screenWidth / 6) * 5, screenHeight, "forYouTxt", movies);
        c.setScreenHBox(screenWidth, screenHeight);
       // c.addToSideBar(screenWidth / 6, screenHeight, "4K Movies", "Movie");
       // c.addToSideBar(screenWidth / 6, screenHeight, "720p Movies", "Movie");
       // c.addToSideBar(screenWidth / 6, screenHeight, "Shows 1", "TV");
        c.setSideBar(screenWidth / 6, screenHeight);
       // c.setMainPage((screenWidth / 6) * 5, screenHeight);*/


    }


    public static void main(String[] args) {
        launch(args);
    }
}
