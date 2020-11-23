package com.kernaldrive.gui.moviepage;

import com.kernaldrive.metadata.Movie;

import info.movito.themoviedbapi.model.people.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;


public class CastSliderController{

    private Movie movie;
    private ArrayList<ImageView> images;
    private List<PersonCast> cast;


    @FXML private HBox sliderHBox;
/*    @FXML private VBox personVBox;
    @FXML private ImageView personImage;
    @FXML private Label personName;
    @FXML private Label personRole;*/

    public void setMovie(Movie movie){
        this.movie = movie;
    }

    public void loadPeople(){
        cast = movie.getCast();
        images = new ArrayList<>();
        for(int i=0; i<cast.size(); i++){
            VBox test = addPerson(cast.get(i));
            sliderHBox.getChildren().addAll(test);
        }
        Thread thread = new Thread(new Runnable() {
            public void run(){
                for(int i=0; i<cast.size(); i++){
                    if(cast.get(i).getProfilePath() != "") {
                        images.get(i).setImage(new Image("https://image.tmdb.org/t/p/w185" + cast.get(i).getProfilePath()));
                    }
                }
            }
        });
        thread.start();
    }

    public VBox addPerson(PersonCast person){
        VBox test = new VBox();
        test.setPadding(new Insets(10,10,10,10));
        Image personImg = new Image("file:C:\\Users\\Faisal\\Documents\\Github\\KernalDrive\\cast.jpg");
        ImageView personImage = new ImageView(personImg);
        //personImage.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        images.add(personImage);
        personImage.setFitWidth(100);
        personImage.setPreserveRatio(true);
        Label personName = new Label(person.getName());
        personName.setMaxWidth(100);
        personName.setAlignment(Pos.CENTER);
        Label personRole = new Label(person.getCharacter());
        personRole.setMaxWidth(100);
        personRole.setAlignment(Pos.CENTER);
        test.getChildren().addAll(personImage, personName, personRole);
        return test;
    }
}
