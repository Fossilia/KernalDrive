package com.kernaldrive.gui.splashscreen;

import com.kernaldrive.gui.MainScreen;
import com.kernaldrive.metadata.MediaManager;
import com.kernaldrive.metadata.MovieGroup;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SplashScreen{
    private double screenWidth;
    private double screenHeight;
    private MediaManager manager;
    private Stage primaryStage;
    private FXMLLoader loader;

    public SplashScreen(MediaManager manager, Stage primaryStage) throws MalformedURLException {
        this.manager = manager;
        this.primaryStage = primaryStage;
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        screenWidth = screenBounds.getWidth();
        screenHeight = screenBounds.getHeight() - 30;
        loader = new FXMLLoader();
        loader.setLocation(new URL("file:///C:\\Users\\HP\\IdeaProjects\\untitled\\src\\main\\java\\com\\kernaldrive\\gui\\splashscreen\\SplashScreen.fxml"));
    }

    public void setSplashScreen(MainScreen mainScreen) throws IOException {
        SplashScreenController splashScreenController = loader.getController();
        splashScreenController.setSplashScreen(screenWidth, screenHeight - 30, manager.getMovieGroups().get(0));
        loadResources(splashScreenController, mainScreen);
    }

    private void loadResources(SplashScreenController splashScreenController, MainScreen mainScreen) throws IOException {
        Scene mainScreenScene = mainScreen.getMainScreenScene();
        Button advanceButton = new Button("Advance to Home");
        //splashScreen.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        advanceButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    mainScreen.displayPage(primaryStage, mainScreenScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        new Thread(){
            public void run(){
                ArrayList<MovieGroup> movieGroups = manager.getMovieGroups();
                double eachToValue = 0.4 / movieGroups.size();
                double interval = 3000 / movieGroups.size();
                System.out.println(movieGroups.size());
                for (int i = 0; i < movieGroups.size(); i++){
                    manager.scanGroups(i);
                    if (i + 1 == movieGroups.size()) splashScreenController.setProgressBar(movieGroups.get(i).getName(), movieGroups.get(i).getMovies().size(), eachToValue*(i + 1), interval, "Building Side Bar...", advanceButton);
                    else splashScreenController.setProgressBar(movieGroups.get(i).getName(), movieGroups.get(i).getMovies().size(), eachToValue*(i + 1), interval, "Scanning " + movieGroups.get(i + 1).getName() + " Group...", advanceButton);
                    try {
                        Thread.sleep((long) interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    mainScreen.setSideBar(manager);
                    splashScreenController.setProgressBar("", -1, 0.6, 1000, "Building Content Page...", advanceButton);
                    mainScreen.setContentPage();
                    splashScreenController.setProgressBar("", -1, 1.0, 3000, "Completed!", advanceButton);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public Scene getScene() throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root, screenWidth, screenHeight - 20);
        return scene;
    }


}
