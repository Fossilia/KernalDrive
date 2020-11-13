package com.kernaldrive.gui.moviepage;


import com.kernaldrive.gui.MainPageController;
import com.kernaldrive.metadata.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MoviePageController {

    private Movie movie;

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
        posterImage.setImage(new Image(movie.getPosterPath()));
        bannerImageView.setImage(new Image(movie.getBannerPath()));

        titleLabel.setWrapText(true);
        synopsisLabel.setMaxWidth(700);
        synopsisLabel.setWrapText(true);
        StackPane stackPane = (StackPane) scrollPane.lookup("ScrollPane .viewport");
        stackPane.setCache(false);

    }


}
