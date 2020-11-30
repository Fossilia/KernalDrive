package com.kernaldrive.startupscreen;

import com.kernaldrive.filescanning.GroupDBManager;
import com.kernaldrive.metadata.MediaGroup;
import com.kernaldrive.metadata.MovieGroup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class startupScreenTester extends Application {

        private ArrayList<File> groupPaths;
        private ArrayList<MediaGroup> mediaGroups;
        GroupDBManager groupDB;

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

        public void addGroup(String groupName) {
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

        public void copyPaths(ArrayList<File> groupPaths){
            for(File filePath : this.groupPaths){
                groupPaths.add(filePath);
            }
        }
}

