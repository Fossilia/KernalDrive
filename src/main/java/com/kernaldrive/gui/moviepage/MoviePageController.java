package com.kernaldrive.gui.moviepage;


import com.kernaldrive.gui.MainPageController;
import com.kernaldrive.metadata.Movie;
import info.movito.themoviedbapi.model.people.*;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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

import java.util.ArrayList;
import java.util.List;

public class MoviePageController {

    private Movie movie;
    private String fontPath = "file:C:\\Users\\idree\\Documents\\Java Projects\\KernalDrive\\varela\\Varela-Regular.otf";
    private Font movieFont = Font.loadFont(fontPath, 40);
    private String defaultPersonImage = "file:cast.jpg";
    private ArrayList<ImageView> castImages = new ArrayList<>();
    private ArrayList<ImageView> crewImages = new ArrayList<>();
    private List<PersonCast> cast;
    private List<PersonCrew> crew;

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
    @FXML private ScrollPane castScrollPane;
    @FXML private ScrollPane crewScrollPane;
    @FXML private HBox castHBox;
    @FXML private HBox crewHBox;



    public void setMovie(Movie movie){
        this.movie = movie;
        cast = movie.getCast();
        crew = movie.getCrew();
    }

    public void setLabels(){
        //scrollPane.setStyle("-fx-background: linear-gradient(to bottom left, rgba(40,43,82, 0.8) 20% , rgba(21,102,123, 0.8))");
        //moviePane.setStyle("-fx-background-color: rgba(217, 217, 217, 0.2); -fx-background-radius: 10;");
        moviePane.setStyle("-fx-border-color: BLACK");
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double  screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        scrollPane.setPrefWidth(screenWidth-30);
        posterImage.setImage(new Image(movie.getPosterPath()));
        Image img = new Image(movie.getBannerPath());
        ImageView bannerI = new ImageView(img);
        bannerI.setPreserveRatio(true);
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
        gc.fillRect(40, 40, w-80, h-100);
        gc.setEffect(null);
        gc.setGlobalBlendMode(BlendMode.SRC_ATOP);
        gc.drawImage(croppedImage, 0, 0);

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

        castScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        crewScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);


        loadCastCrew();
    }

    public void loadCastCrew(){

        for(int i=0; i<cast.size(); i++){
            VBox test = addCast(cast.get(i));
            castHBox.getChildren().addAll(test);
        }
        for(int i=0; i<crew.size(); i++){
            VBox test = addCrew(crew.get(i));
            crewHBox.getChildren().addAll(test);
        }

        Thread castThread = new Thread(new Runnable() {
            public void run(){
                for(int i=0; i<cast.size(); i++){
                    if(cast.get(i).getProfilePath() != "") {
                        castImages.get(i).setImage(new Image("https://image.tmdb.org/t/p/w185" + cast.get(i).getProfilePath()));
                    }
                }
            }
        });
        Thread crewThread = new Thread(new Runnable() {
            public void run(){
                for(int i=0; i<crew.size(); i++){
                    if(crew.get(i).getProfilePath() != "") {
                        crewImages.get(i).setImage(new Image("https://image.tmdb.org/t/p/w185" + crew.get(i).getProfilePath()));
                    }
                }
            }
        });
        castThread.start();
        crewThread.start();
    }

    public VBox addCast(PersonCast person){
        VBox creditVBox = new VBox();
        creditVBox.setPadding(new Insets(10,10,10,10));
        ImageView personImage = createCreditsImage();
        castImages.add(personImage);
        Label personName = createCreditsLabel(person.getName());
        Label personRole = createCreditsLabel(person.getCharacter());
        creditVBox.getChildren().addAll(personImage, personName, personRole);
        return creditVBox;
    }

    public VBox addCrew(PersonCrew person){
        VBox test = new VBox();
        test.setPadding(new Insets(10,10,10,10));
        ImageView personImage = createCreditsImage();
        crewImages.add(personImage);
        Label personName = createCreditsLabel(person.getName());
        Label personRole = createCreditsLabel(person.getDepartment());
        test.getChildren().addAll(personImage, personName, personRole);
        return test;
    }

    public ImageView createCreditsImage(){
        ImageView creditImage = new ImageView(new Image("file:cast.jpg"));
        //personImage.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        creditImage.setFitWidth(100);
        creditImage.setPreserveRatio(true);
        return creditImage;
    }

    public Label createCreditsLabel(String credit){
        Label creditText = new Label(credit);
        creditText.setMaxWidth(100);
        creditText.setAlignment(Pos.CENTER);
        return creditText;
    }
}
