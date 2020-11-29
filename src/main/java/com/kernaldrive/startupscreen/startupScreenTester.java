package com.kernaldrive.startupscreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class startupScreenTester extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage)throws Exception{
            primaryStage.setTitle("MoviePage");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation( new URL(new URL("file:"), "./src/main/java/com/kernaldrive/startupscreen/StartupScreen.fxml"));
            Parent root = loader.load();
            //Parent root = FXMLLoader.load(getClass().getResource("MoviePage.fxml"));
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            double screenWidth = screenBounds.getWidth();
            double  screenHeight = screenBounds.getHeight();
            Scene moviePage = new Scene(root, screenWidth, screenHeight-40);
            primaryStage.setScene(moviePage);
            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.show();

            StartupScreenController startupScreenController = loader.getController();
            startupScreenController.loadStartupScreen(primaryStage);

        }
}

