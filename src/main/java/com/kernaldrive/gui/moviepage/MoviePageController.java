package com.kernaldrive.gui.moviepage;


import com.kernaldrive.metadata.Movie;
import com.kernaldrive.videoplayer.mediaPlayer;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.ProductionCompany;
import info.movito.themoviedbapi.model.people.*;

import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.List;

public class MoviePageController {

    private Movie movie;
    private String fontPath = "file:varela\\Varela-Regular.otf";
    private Font movieFont = Font.loadFont(fontPath, 40);
    private Font castFont = Font.loadFont(fontPath, 20);
    private ArrayList<StackPane> castCircles = new ArrayList<>();
    private ArrayList<StackPane> crewCircles = new ArrayList<>();
    private List<PersonCast> cast;
    private List<PersonCrew> crew;
    private List<ProductionCompany> productionCompanies;

    @FXML private ScrollPane scrollPane;
    @FXML private Pane moviePane;
    @FXML private VBox movieVBox;
    @FXML private Canvas bannerCanvas;
    @FXML private StackPane posterStackPane;
    @FXML private Label titleLabel;
    @FXML private Label yearLabel;
    @FXML private Label runtimeLabel;
    @FXML private Label mpaaLabel;
    @FXML private Label genreLabel;
    @FXML private Label taglineLabel;
    @FXML private Label synopsisLabel;
    @FXML private Label directorNameLabel;
    @FXML private Label writerNameLabel;
    @FXML private Label studioNameLabel;
    @FXML private Label imdbLabel;
    @FXML private ScrollPane castScrollPane;
    @FXML private ScrollPane crewScrollPane;
    @FXML private HBox castHBox;
    @FXML private HBox crewHBox;

    public void setMovie(Movie movie){
        this.movie = movie;
        cast = movie.getCast();
        crew = movie.getCrew();
        productionCompanies = movie.getProductionCompanies();
    }

    public void setPageContents(double width, double height){
        scrollPane.setPrefWidth(width);
        scrollPane.setPrefHeight(height);
        //moviePane.setTranslateX((scrollPane.getPrefWidth()-moviePane.getPrefWidth())/2);
        //moviePane.setStyle("-fx-border-color: black");

        loadPoster();
        loadBanner();

        titleLabel.setText(movie.getTitle());
        titleLabel.setWrapText(true);
        yearLabel.setText(movie.getYear());
        directorNameLabel.setText(getDirectorWriter("Directing"));
        writerNameLabel.setText(getDirectorWriter("Writing"));
        studioNameLabel.setText(getStudio());


        imdbLabel.setText(movie.getImdbRating().toString());

        //Fix mimizing bug
        mpaaLabel.setText("PG-13");
        runtimeLabel.setText(movie.getRuntime());

        String genres= "";
        for(Genre g: movie.getGenres()){
            genres += g.getName() + ", ";
        }
        genreLabel.setText(genres.substring(0, genres.length() - 2));

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

        loadCredits();
    }

    private void loadPoster(){
        Rectangle posterRect = new Rectangle(250, 375);
        posterRect.setFill(new ImagePattern(new Image(movie.getMoviePagePoster())));
        ImageView playImageView = new ImageView(new Image("file:resources/icons/play_image.png"));
        playImageView.setFitWidth(50);
        playImageView.setFitHeight(50);
        playImageView.setVisible(false);
        posterStackPane.getChildren().addAll(posterRect, playImageView);
        posterStackPane.setOnMouseEntered(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                posterRect.setStrokeType(StrokeType.OUTSIDE);
                posterRect.setStroke(Color.rgb(26, 158, 158));
                posterRect.setStrokeWidth(4);
                //posterRect.setEffect(new GaussianBlur(5));
                playImageView.setVisible(true);
            }
        });
        posterStackPane.setOnMouseExited(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                posterRect.setStroke(null);
                posterRect.setEffect(null);
                playImageView.setVisible(false);
            }
        });
        posterStackPane.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                mediaPlayer mp = new mediaPlayer(movie.getFilePath());
                mp.start();
            }
        });
    }

    private void loadBanner(){
        ImageView bannerImageView = new ImageView(new Image(movie.getBannerPath()));
        bannerImageView.setPreserveRatio(true);
        bannerImageView.setFitWidth(1060);
        bannerImageView.setSmooth(true);
        WritableImage croppedImage = bannerImageView.snapshot(null, null);
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
    }

    private void loadCredits(){
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
                        ((Circle)castCircles.get(i).getChildren().get(0)).setFill(new ImagePattern((new Image("https://image.tmdb.org/t/p/w185" + cast.get(i).getProfilePath())), 0, -20, 100, 150, false));
                    }
                    else{
                        String initials = getInitials(cast.get(i).getName().split(" "));
                        ((Text)castCircles.get(i).getChildren().get(1)).setText(initials);
                    }
                }
            }
        });
        Thread crewThread = new Thread(new Runnable() {
            public void run(){
                for(int i=0; i<crew.size(); i++){
                    if(crew.get(i).getProfilePath() != "") {
                        ((Circle)crewCircles.get(i).getChildren().get(0)).setFill(new ImagePattern((new Image("https://image.tmdb.org/t/p/w185" + crew.get(i).getProfilePath())), 0, -20, 100, 150, false));
                    }
                    else{
                        String initials = getInitials(crew.get(i).getName().split(" "));
                        ((Text)crewCircles.get(i).getChildren().get(1)).setText(initials);
                    }
                }
            }
        });
        castThread.start();
        crewThread.start();
    }

    private VBox addCast(PersonCast person){
        VBox creditVBox = new VBox();
        creditVBox.setPadding(new Insets(10,10,10,10));
        StackPane personImageCircle = createCreditsImage();
        castCircles.add(personImageCircle);
        Label personName = createCreditsLabel(person.getName(), "regular");
        Label personRole = createCreditsLabel(person.getCharacter(), "fade");
        creditVBox.getChildren().addAll(personImageCircle, personName, personRole);
        return creditVBox;
    }

    private VBox addCrew(PersonCrew person){
        VBox test = new VBox();
        test.setPadding(new Insets(10,10,10,10));
        StackPane personImageCircle = createCreditsImage();
        crewCircles.add(personImageCircle);
        Label personName = createCreditsLabel(person.getName(), "regular");
        Label personRole = createCreditsLabel(person.getDepartment(), "fade");
        test.getChildren().addAll(personImageCircle, personName, personRole);
        return test;
    }

    private StackPane createCreditsImage(){
        StackPane stack = new StackPane();
        Text text = new Text();
        text.setFont(castFont);
        text.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(128, 128, 128, 0.3);");
        Circle cir2 = new Circle(50,50,45);
        cir2.setFill(Color.rgb(128, 128, 128, 0.1));//new ImagePattern(new Image("file:cast.jpg")));
        cir2.setStroke(Color.rgb(128, 128, 128, 0.3));
        cir2.setStrokeType(StrokeType.OUTSIDE);
        cir2.setStrokeWidth(5);

        stack.getChildren().addAll(cir2, text);
        stack.setLayoutX(30);
        stack.setLayoutY(30);

        cir2.setOnMouseEntered(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                //cir2.setStrokeType(StrokeType.INSIDE);
                cir2.setStroke(Color.rgb(26, 158, 158));
                //cir2.setStrokeWidth(2);
            }
        });
        cir2.setOnMouseExited(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                cir2.setStroke(Color.rgb(128, 128, 128, 0.2));
            }
        });
        return stack;
    }

    private Label createCreditsLabel(String credit, String colorType){
        Label creditText = new Label(credit);
        creditText.setMaxWidth(100);
        if (colorType == "regular") creditText.setStyle("-fx-font-weight: bold; -fx-text-fill: #000000");
        if (colorType == "fade") creditText.setStyle("-fx-font-weight: bold; -fx-text-fill: #999999");
        creditText.setAlignment(Pos.CENTER);
        return creditText;
    }

    private String getInitials(String [] names){
        String initials = "";
        for (String name : names) {
            try { initials += name.charAt(0); }
            catch (Exception e){}
        }
        return initials;
    }

    private String getDirectorWriter(String role){
        String person = "";
        int i = 0;
        for(PersonCrew personCrew : crew){
            if (personCrew.getDepartment().equals(role) && i<10){
                person += personCrew.getName()+ ", ";
            }
            if (personCrew.getDepartment().equals(role) && i>10){
                person += personCrew.getName() + ", ";
                break; }
            i++;
        }
        return  person.substring(0, person.length() - 2);
    }

    private String getStudio(){
        String studio = "";
        if (productionCompanies.size() < 3){
            for(ProductionCompany p : productionCompanies) {
                studio += p.getName() + ", ";
            }
        }
        else{
            studio += productionCompanies.get(0).getName() + ", ";
        }
        return studio.substring(0, studio.length() - 2);
    }

}