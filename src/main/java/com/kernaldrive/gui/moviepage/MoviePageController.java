package com.kernaldrive.gui.moviepage;


import com.kernaldrive.gui.MainPageController;
import com.kernaldrive.metadata.Movie;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.CycleMethod;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class MoviePageController {

    private Movie movie;
    private String fontPath = "file:C:\\Users\\idree\\Documents\\Java Projects\\KernalDrive\\varela\\Varela-Regular.otf";
    private Font movieFont = Font.loadFont(fontPath, 40);

    @FXML private ScrollPane scrollPane;
    @FXML private Pane moviePane;
    @FXML private VBox movieVBox;
    @FXML private Canvas bannerCanvas;
    @FXML private ImageView posterImage;
    @FXML private ImageView bannerImageView;
    @FXML private Label titleLabel;
    @FXML private Label yearLabel;
    @FXML private Label runtimeLabel;
    @FXML private Label mpaaLabel;
    @FXML private Label genreLabel;
    @FXML private Label taglineLabel;
    @FXML private Label synopsisLabel;


    public void setMovie(Movie movie){
        this.movie = movie;
    }

    public void setLabels(){
        //scrollPane.setStyle("-fx-background: linear-gradient(to bottom left, rgba(40,43,82, 0.8) 20% , rgba(21,102,123, 0.8))");
        //moviePane.setStyle("-fx-background-color: rgba(217, 217, 217, 0.2); -fx-background-radius: 10;");
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double  screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        scrollPane.setPrefWidth(screenWidth-30);
        posterImage.setImage(new Image(movie.getPosterPath()));
        Image img = new Image(movie.getBannerPath());
        ImageView bannerI = new ImageView(img);
        bannerI.setFitHeight(543);
        bannerI.setFitWidth(1000);
        bannerI.setSmooth(true);
        WritableImage croppedImage = bannerI.snapshot(null, null);

        int w = (int) croppedImage.getWidth();
        int h = (int) croppedImage.getHeight();

        bannerCanvas.setWidth(w);
        bannerCanvas.setHeight(h);
        GraphicsContext gc = bannerCanvas.getGraphicsContext2D();
        gc.setEffect(new GaussianBlur(50));
        gc.setFill(Color.BLACK);
        gc.fillRect(40, 40, w-70, h-70);
        gc.setEffect(null);
        gc.setGlobalBlendMode(BlendMode.SRC_ATOP);
        gc.drawImage(croppedImage, 0, 0);



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


}
