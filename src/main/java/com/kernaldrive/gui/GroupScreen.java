package com.kernaldrive.gui;

import com.kernaldrive.metadata.Movie;
import com.kernaldrive.metadata.MovieGroup;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GroupScreen {
    private String title;
    private int rows, moviesPerRow, imagesNum, groupNum;
    private boolean loading;
    private ArrayList<HBox> pageSections;
    private ArrayList<MovieGroup> movieGroups;
    private ArrayList<ImageView> moviePosters;
    private boolean searching = false;
    private Thread searchingThread;

    public GroupScreen(){
        rows = 0;
        pageSections = new ArrayList<>();
        movieGroups = new ArrayList<>();
        moviePosters = new ArrayList<>();
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void addMovieGroups(MovieGroup movieGroup){
        movieGroups.add(movieGroup);
    }

    public void setMovieGroups(ArrayList<MovieGroup> movieGroups){
        this.movieGroups = movieGroups;
    }

    public ArrayList<HBox> getContent(double width, double height, Font movieFont, ObservableList<Node> visibleNodes){
        moviesPerRow = 0;
        double total = (width / 40) * 2;
        while (true) {
            moviesPerRow++;
            total += width / 7.5;
            total += (width / 40) * 2;
            if (total > width) break;
        }

        VBox[] boxes = new VBox[moviesPerRow];
        for (int i = 0; i < moviesPerRow; i++) {
            boxes[i] = new VBox();
            boxes[i].setPadding(new Insets(0, width / 40, 0, 0));
        }

        int movieNum = 0, rowNum = 0;
        for (int i = 0; i < movieGroups.size(); i++){
            if ((movieNum + movieGroups.get(i).getMovies().size()) > (moviesPerRow * 5)) movieNum = (moviesPerRow * 5);
            else movieNum += movieGroups.get(i).getMovies().size();
        }

        if (movieNum % moviesPerRow == 0) rowNum = movieNum / moviesPerRow;
        else rowNum = (movieNum / moviesPerRow) + 1;

        HBox[] rows = new HBox[rowNum];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new HBox();
            rows[i].setId("Row " + (i+ 1));
            rows[i].setPadding(new Insets(height / 20, 0, 0, width / 40));
            rows[i].setMaxWidth(width);
        }

        int count = 0;

        for (int i = 0; i < movieGroups.size() && count < movieNum; i++){
            groupNum = i;
            ArrayList<Movie> groupMovies = movieGroups.get(i).getMovies();
            for (int x = 0; x < groupMovies.size() && count < movieNum; x++) {
                imagesNum = x;
                Movie movie = groupMovies.get(x);
                VBox movieBox = addMovieBox(width, height, movieFont, movie);

                rows[count / 6].getChildren().add(movieBox);
                count++;
            }
        }
        for (int i = 0; i < rows.length; i++) {
            pageSections.add(rows[i]);
        }
        System.out.println("");
        return pageSections;
    }


    private List<Node> getVisibleElements(ScrollPane scrollPane) {
        List<Node> visibleNodes = new ArrayList<Node>();
        Bounds paneBounds = scrollPane.localToScene(scrollPane.getBoundsInParent());
        if (scrollPane.getContent() instanceof Parent) {
            for (Node n : ((Parent) scrollPane.getContent()).getChildrenUnmodifiable()) {
                Bounds nodeBounds = n.localToScene(n.getBoundsInLocal());
                if (paneBounds.intersects(nodeBounds)) {
                    visibleNodes.add(n);
                }
            }
        }
        return visibleNodes;
    }

    public void addRows(double width, double height, Font movieFont, ObservableList<Node> visibleNodes, ScrollPane scrollPane, VBox container, Button button){
        visibleNodes.setAll(getVisibleElements(scrollPane));
        String[] splitted = visibleNodes.get(visibleNodes.size() - 1).getId().split("\\s+");
        int rowNum = Integer.parseInt(splitted[1]);

        if (((container.getChildren().size() - rowNum) < 2) && !loading){
            loading = true;
            if (moreImagesAvailable()) {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        int count = 0;
                        HBox[] newRows = new HBox[2];
                        for (int i = 0; i < 2; i++) {
                            newRows[i] = new HBox();
                            newRows[i].setId("Row " + (container.getChildren().size() + (i + 1)));
                            newRows[i].setPadding(new Insets(height / 20, 0, 0, width / 40));
                            newRows[i].setMaxWidth(width);
                        }
                        System.out.println("Getting Images");
                        ArrayList<Movie> movies = movieGroups.get(groupNum).getMovies();
                        while (count < (moviesPerRow * 2) && moreImagesAvailable()) {
                            imagesNum++;
                            System.out.println(imagesNum + ", " + movieGroups.get(groupNum).getMovies().size());
                            if (imagesNum == 0){
                                movies = movieGroups.get(groupNum).getMovies();
                            }
                            Movie movie = movies.get(imagesNum);
                            System.out.println(movie.getTitle());
                            VBox box = addMovieBox(width, height, movieFont, movie);
                            newRows[count / moviesPerRow].getChildren().add(box);
                            count++;
                        }
                        System.out.println("Adding Images");;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                container.getChildren().remove(button);
                                for (int i = 0; i < 2; i++) {
                                    if (newRows[i].getChildren().size() > 0){
                                        System.out.println(newRows[i].getId());
                                        container.getChildren().add(newRows[i]);
                                    }
                                }
                                button.setId(container.getChildren().get(container.getChildren().size() - 1).getId());
                                container.getChildren().add(button);
                            }
                        });
                        loading = false;
                        return null;
                    }
                };
                new Thread(task).start();
            }

        }
    }

    private boolean moreImagesAvailable(){
        if (movieGroups.get(groupNum).getMovies().size() > (imagesNum + 1)){
            return true;
        }
        else if ((groupNum < (movieGroups.size()) - 1) && imagesNum > 0){
            groupNum++;
            imagesNum = -1;
            return true;

        }
        System.out.println("End");
        return false;
    }

    private VBox addMovieBox(double width, double height, Font movieFont, Movie movie){
        VBox movieBox = new VBox();
        movieBox.setMaxWidth(width / 7.5);
        movieBox.setPadding(new Insets(0, width / 40, 0, 0));

        String imagePath = movie.getPosterPath();
        ImageView moviePoster = new ImageView(new Image(imagePath));
        moviePoster.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0.8,0), 30, 0, 0, 0)");
        moviePoster.setFitWidth((width / 7.5));
        moviePoster.setFitHeight((width / 7.5) * 1.5);

        Label movieName = new Label(movie.getTitle());
        movieName.setFont(movieFont);
        movieName.setMinHeight(Region.USE_PREF_SIZE);
        movieName.setPrefHeight(Region.USE_COMPUTED_SIZE);
        movieName.setPadding(new Insets(height / 50, 0, 0, 0));
        movieName.setWrapText(true);
        movieName.setTextFill(Color.WHITE);

        Label year = new Label(movie.getYear());
        year.setFont(movieFont);
        year.setMinHeight(Region.USE_PREF_SIZE);
        year.setPrefHeight(Region.USE_COMPUTED_SIZE);
        year.setPadding(new Insets(0, 0, 0, 0));
        year.setWrapText(true);
        year.setTextFill(Color.rgb(166, 166, 166));
        movieBox.getChildren().addAll(moviePoster, movieName, year);
        return movieBox;
    }

    public void matchMoviesBySearch(String search, Task<Void> task) {
        while (true){
            if (task.isCancelled()){
                System.out.println("Done Searching");
                break;
            }
            System.out.println("-------------------\nNew Search: " + search);
        }
        /*for (int i = 0; i < movieGroups.size(); i++) {
            ArrayList<Movie> movies = movieGroups.get(i).getMovies();
            for (int x = 0; x < movies.size(); x++) {
                if (movies.get(x).getTitle().toLowerCase().contains(search.toLowerCase()))
                    System.out.println(movies.get(x).getTitle());
            }
        }*/

    }

    public boolean isSearching(){
        return searching;
    }

    public void setSearching(boolean val){
        searching = val;
    }

    public void addMoviePoster(ImageView poster){
        moviePosters.add(poster);
    }

}
