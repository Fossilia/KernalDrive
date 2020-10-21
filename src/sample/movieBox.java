package sample;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

public class movieBox {
    private ArrayList<Image> moviePosters;
    private ArrayList<String> movieNames;
    private ArrayList<VBox> boxes;
    private HBox hbox;
    private int currMovie;

    public movieBox(HashMap<String, String> movies){
        moviePosters = new ArrayList<>();
        movieNames = new ArrayList<>();
        boxes = new ArrayList<>();
        currMovie = 0;
    }

    public void addHBox(HBox hBox){
        hbox = hBox;
    }
    public void addMovieBox(VBox vbox){
        boxes.add(vbox);
    }

    public int numMoviesOnPage(){
        return boxes.size();
    }

    public void addMoviePoster(Image img){
        moviePosters.add(img);
    }

    public void addMovieName(String name){
        movieNames.add(name);
    }





    public void placeInVBox(ImageView movieImg, Label movieName, int position){
        VBox box = boxes.get(position);
        ObservableList<Node> boxChildren = box.getChildren();
        boxChildren.add(movieImg);
        boxChildren.add(movieName);
    }

    public VBox getMovieBox(int pos){
        return boxes.get(pos);
    }

    public void updateMovies(){
        System.out.println(movieNames.get(1));
        currMovie++;
        for (int i = 0; i < boxes.size(); i++){
            VBox box = boxes.get(i);
            ObservableList<Node> boxChildren = box.getChildren();
            /*ImageView img = (ImageView)boxChildren.get(0);
            img.setImage(moviePosters.get(currMovie+i).getImage());
            img.setId(moviePosters.get(currMovie+i).getId());*/
            Label name = (Label) boxChildren.get(1);
            name.setText(movieNames.get(currMovie + i));
        }
        System.out.println(movieNames.get(1));
        //printHBox();

    }



    public VBox getFromHbox(int pos){
        ObservableList<Node> hboxChildren = hbox.getChildren();
        VBox firstBox = (VBox)hboxChildren.get(0);
        return firstBox;
    }

    public VBox getVBox(int pos){
        ObservableList<Node> hboxChildren = hbox.getChildren();
        VBox box = (VBox)hboxChildren.get(pos);
        return box;
    }

    public void printHBox(){
        ObservableList<Node> hboxChildren = hbox.getChildren();
        for (int i = 0; i < hboxChildren.size();i++){
            VBox box = (VBox)hboxChildren.get(i);
            ObservableList<Node> boxChildren = box.getChildren();
            Label name = (Label) boxChildren.get(1);
            System.out.println(name.getText());
        }
    }

}
