package com.kernaldrive.gui.moviepage;


import com.kernaldrive.gui.MainPageController;
import com.kernaldrive.metadata.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.CycleMethod;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class MoviePageController {

    private Movie movie;
    private String fontPath = "file:C:\\Users\\idree\\Documents\\Java Projects\\KernalDrive\\varela\\Varela-Regular.otf";
    private Font movieFont = Font.loadFont(fontPath, 40);

    @FXML
    private VBox movieVBox;
    @FXML
    private ImageView posterImage;
    @FXML
    private ImageView bannerImageView;
    @FXML
    private Label titleLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Label runtimeLabel;
    @FXML
    private Label mpaaLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private Label taglineLabel;
    @FXML
    private Label synopsisLabel;
    @FXML
    private ScrollPane scrollPane;

    public void setMovie(Movie movie){
        this.movie = movie;
    }

    public void setLabels(){

        Stop[] stops = new Stop[] { new Stop(0, Color.web("#000428")), new Stop(1, Color.web("#004e92"))};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        //bannerImageView.setStyle("-fx-effect: innershadow(gaussian, , 1000, .5, 0, 0);");
        //-fx-effect: innershadow(gaussian, #000428, 1000, .5, 0, 0);
        //movieVBox.setStyle("-fx-background-color: linear-gradient(to right, #000428, #004e92)");

        posterImage.setImage(new Image(movie.getPosterPath()));
        //posterImage.setCache(true);
        bannerImageView.setImage(new Image(movie.getBannerPath()));
        //bannerImageView.setCache(true);
        //fixBlurryText(scrollPane);

        titleLabel.setText(movie.getTitle());
        //titleLabel.setFont(movieFont);
        titleLabel.setWrapText(true);

        yearLabel.setText(movie.getYear());
        runtimeLabel.setText(movie.getRuntime());
        genreLabel.setText(movie.getGenres().toString());

        if(movie.getTagline() == "") {
            taglineLabel.setText("Overview");
        }
        else{
            taglineLabel.setText(movie.getTagline());
        }
        taglineLabel.setWrapText(true);
        taglineLabel.setMaxWidth(800);
        synopsisLabel.setText(movie.getOverview());
        synopsisLabel.setMaxWidth(800);
        synopsisLabel.setWrapText(true);



    }

    public void fixBlurryText(ScrollPane scrollPane) {
        StackPane stackPane = (StackPane) scrollPane.lookup("ScrollPane .viewport");
        stackPane.setCache(false);
    }


}
