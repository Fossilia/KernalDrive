package com.kernaldrive.gui.moviepage;

import java.io.File;
import java.util.*;
import com.kernaldrive.gui.MainPageController;
import com.kernaldrive.metadata.Movie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.kernaldrive.metadata.TmdbFilenameSearch;
import com.kernaldrive.metadata.TmdbMovieExtractor;
import com.kernaldrive.metadata.MediaInfoExtractor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.net.URL;

public class MoviePageTester extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)throws Exception{
        primaryStage.setTitle("MoviePage");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:///C:\\Users\\idree\\Documents\\Java Projects\\KernalDrive\\src\\main\\java\\com\\kernaldrive\\gui\\moviepage\\MoviePage.fxml"));
        Parent root = loader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("MoviePage.fxml"));
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double  screenHeight = screenBounds.getHeight();
        Scene moviePage = new Scene(root, screenWidth, screenHeight-40);
        primaryStage.setScene(moviePage);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();

        MoviePageController moviePageController = loader.getController();
        TmdbFilenameSearch tmdbFilenameSearch = new TmdbFilenameSearch();
        TmdbMovieExtractor tmdbMovieExtractor = new TmdbMovieExtractor();
        int movieID = tmdbFilenameSearch.searchTmdb("You were never lovelier 1942");
        Movie movie = tmdbMovieExtractor.extractMovieInfo("", movieID);
        moviePageController.setMovie(movie);
        moviePageController.setLabels();

    }
}