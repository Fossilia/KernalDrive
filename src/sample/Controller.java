package sample;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
@FXML
VBox sideBarIcons;
@FXML
HBox screenHBox;
@FXML
HBox homeItem;
    private String hello = "Hello";



    private String boldFontPath = "file:C:\\Users\\HP\\IdeaProjects\\RandomTest\\varela\\Varela-Regular.otf";
    private Font movieFont = Font.loadFont(boldFontPath, 12);
    private Font sideBarMenuFont = Font.loadFont(boldFontPath, 16);
    private Font headingFont = Font.loadFont(boldFontPath, 20);
    private Font pageTitleFont = Font.loadFont(boldFontPath, 22);
    private Color LIGHTBLUE = Color.rgb(153, 201, 255);
    private Color SIDEBARCOLOR = Color.rgb(238, 243, 244);
    private Color DARKBLUE = Color.rgb(0, 84, 180);


    private String currMenuItem = "homeItem";
    private ArrayList<ImageView> moviePosters = new ArrayList<>();
    private ArrayList<Label> movieNames = new ArrayList<>();
    private ArrayList<VBox> movieBoxes = new ArrayList<>();
    private int currMovie = 0;




@FXML
Button userIconBtn;
@FXML
Label userGreeting;
@FXML
FontAwesomeIconView homeIcon;
@FXML
FontAwesomeIconView userIcon;
@FXML
Button homeBtn;
@FXML
Rectangle currRect;
@FXML
HBox topBar;
@FXML
VBox mainPage;
@FXML
Label continueWatchingTxt;
@FXML
Label forYouTxt;
@FXML
HBox forYouMovies;
@FXML
VBox forYouBox;
@FXML
HBox arrowHBox;

    public void setScreenHBox(double width, double height){
        screenHBox.setPrefSize(width, height);
    }

    public void setSideBar(double width, double height, HashMap<String, String> groups){
        ObservableList<Node> sideBar = sideBarIcons.getChildren();
        userIconBtn.setPrefWidth(width);
        userIconBtn.setPadding(new Insets(height/15, 0, 0, 0));
        userIcon.setSize("30");
        sideBarIcons.setPrefWidth(width);
        sideBarIcons.setPrefHeight(height);
        userGreeting.setMinWidth(width);
        userGreeting.setFont(headingFont);

        homeBtn.setPrefWidth((width / 45) * 44);
        homeBtn.setFont(sideBarMenuFont);
        homeBtn.setPadding(new Insets(0, 0, 0, width / 10));
        setSideBarItemHandler(sideBar, homeBtn, homeItem);
        homeIcon.setSize(Double.toString(sideBarMenuFont.getSize()));
        currRect.setWidth(width/45);
        currRect.setHeight(homeBtn.getHeight());

        for (String group : groups.keySet()) {
            String type = groups.get(group);
            Button menuBtn = makeBtn(group, (width / 45) * 44, -1, sideBarMenuFont, LIGHTBLUE, SIDEBARCOLOR);
            menuBtn.setId(group+"Btn");
            menuBtn.setAlignment(Pos.BASELINE_LEFT);
            if (type.equals("Movie")) {
                MaterialDesignIconView movieIcon = new MaterialDesignIconView(MaterialDesignIcon.MOVIE);
                movieIcon.setSize(Double.toString(sideBarMenuFont.getSize()));
                movieIcon.setFill(menuBtn.getTextFill());
                menuBtn.setGraphic(movieIcon);
            }

            else{
                FontAwesomeIconView btnIcon;
                if (type.equals("Heart")) btnIcon = new FontAwesomeIconView(FontAwesomeIcon.HEART_ALT);
                else if (type.equals("Gear")) btnIcon = new FontAwesomeIconView(FontAwesomeIcon.GEAR);
                else if (type.equals("Leave")) btnIcon = new FontAwesomeIconView(FontAwesomeIcon.POWER_OFF);
                else btnIcon = new FontAwesomeIconView(FontAwesomeIcon.TV);
                btnIcon.setFill(menuBtn.getTextFill());
                btnIcon.setSize(Double.toString(sideBarMenuFont.getSize()));
                menuBtn.setGraphic(btnIcon);
            }
            menuBtn.setPadding(new Insets(0, 0, 0, width / 10));

            Rectangle itemRect = makeRect(currRect.getWidth(), currRect.getHeight(), SIDEBARCOLOR);
            itemRect.setArcWidth(8);
            itemRect.setArcHeight(8);

            HBox hbox = new HBox(menuBtn, itemRect);
            String id = group + "Item";
            hbox.setId(id);
            hbox.setPadding(new Insets(height/50,0,0,0));
            setSideBarItemHandler(sideBar, menuBtn, hbox);
            sideBarIcons.getChildren().add(hbox);
        }
    }

    public void setTopBar(double width, double height){
        topBar.setPrefWidth(width);
        //topBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        topBar.setPadding(new Insets(height/100,0,height/30,0));
        Button pageLabel = (Button)(topBar.getChildren().get(0));
        pageLabel.setPadding(new Insets(height/100,0,0,width/40));
        pageLabel.setPrefWidth((width / 4) * 3);
        pageLabel.setFont(pageTitleFont);
        Button searchIcon = (Button)(topBar.getChildren().get(1));
        searchIcon.setPrefWidth((width / 4));
        FontAwesomeIconView search = (FontAwesomeIconView)searchIcon.getGraphic();
        search.setSize(Double.toString(pageTitleFont.getSize()));
        //topBar.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-border-width: 4; -fx-border-color: #99C9FF;");
    }

    public void setMainPage(double width, double height){
        mainPage.setPrefSize(width, height);
        setTopBar(width, height);

        forYouTxt.setPadding(new Insets(height/50,0,0,(width / 40) * 2));
        forYouTxt.setMinWidth(width);
        forYouTxt.setFont(headingFont);
        forYouTxt.setStyle("-fx-font-weight: bold");

    }

    public void addLabelBox(double width, double height, String labelText, HashMap<String, String> movies){
        movieBox m = new movieBox(movies);

        Label boxLabel = new Label(labelText);
        boxLabel.setFont(headingFont);
        boxLabel.setPadding(new Insets(0, 0, 0, (width / 40) * 2));

        FontAwesomeIconView backArrowIcon = makeIcon(FontAwesomeIcon.ARROW_CIRCLE_ALT_LEFT, Double.toString(pageTitleFont.getSize()));
        Button backArrow = new Button();
        backArrow.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        backArrow.setGraphic(backArrowIcon);


        FontAwesomeIconView forwardArrowIcon = makeIcon(FontAwesomeIcon.ARROW_CIRCLE_ALT_RIGHT, Double.toString(pageTitleFont.getSize()));
        Button forwardArrow = new Button();
        forwardArrow.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        forwardArrow.setGraphic(forwardArrowIcon);
        HBox arrowHBox = new HBox(backArrow, forwardArrow);
        arrowHBox.setPadding(new Insets(0, 0, height / 40, (width / 8) * 7));

        HBox moviesBox = new HBox();
        moviesBox.setPadding(new Insets(0,0,0,(width/40) * 2));
        moviesBox.setId(labelText+" Box");

        double total = (width/40) * 2;
        while(true){
            total += width / 8;
            total += (width / 40) * 2;
            VBox vbox = new VBox();
            vbox.setPrefWidth(width / 8);
            vbox.setPadding(new Insets(0, (width / 40), 0, 0));
            moviesBox.getChildren().add(vbox);
            m.addMovieBox(vbox);
            if (total > width) break;
        }
        m.addHBox(moviesBox);

        int count = 1;
        for (String movie : movies.keySet()) {
            String imagePath = "file:" + movies.get(movie);
            Image moviePoster = new Image(imagePath);
            ImageView img = new ImageView(moviePoster);
            img.setFitWidth(width / 8);
            img.setFitHeight(width / 6);
            img.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0.8,0.8), 30, 0, 0, 0)");
            img.setId(movie+"Img");
            m.addMoviePoster(moviePoster);

            Label movieName = new Label(movie);
            movieName.setFont(movieFont);
            movieName.setPadding(new Insets(height/50, 0, height/40, 0));
            movieName.setWrapText(true);
            m.addMovieName(movie);

            m.placeInVBox(img, movieName, (count - 1) % m.numMoviesOnPage());
            //if (count <= m.numMoviesOnPage()) m.placeInVBox(img, movieName, (count - 1) % m.numMoviesOnPage());
            count++;
        }

        /*backArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FadeTransition posterFadeOut = makeFade(m.getCurrMoviePoster(), 1.0, 0, 0.5);
                posterFadeOut.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        m.updateMovies();
                        /*VBox v = m.getVBox(0);
                        ObservableList<Node> children = v.getChildren();
                        Label movieName = new Label(m.getCurrMovieName());
                        movieName.setFont(movieFont);
                        movieName.setPadding(new Insets(height/50, 0, 0, 0));
                        movieName.setWrapText(true);
                        children.add(movieName);
                        //v.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                });
                posterFadeOut.play();

                FadeTransition nameFadeOut = makeFade(m.getCurrMovieLabel(), 1.0, 0, 0.5);
                nameFadeOut.play();

                /*for (int i = 1; i <= 2; i++){
                    TranslateTransition posterMove = new TranslateTransition();
                    posterMove.setByX(-1 * ((width / 8) + (width / 40)));
                    posterMove.setNode(m.getMoviePoster(i));
                    posterMove.setDuration(Duration.seconds(1));
                    TranslateTransition nameMove = new TranslateTransition();
                    nameMove.setByX(-1 * ((width / 8) + (width / 40)));
                    nameMove.setNode(m.getMovieLabel(i));
                    nameMove.setDuration(Duration.seconds(1));
                    nameMove.play();
                    int finalI = i;
                    nameMove.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (finalI == 2) m.updateMovies();
                        }
                    });
                    posterMove.play();
                }*/

                //System.out.println(m.getMovieName(0).getText());
                /*for (int i = 1; i <= m.numMoviesOnPage() - 1; i++){
                    TranslateTransition posterMove = new TranslateTransition();
                    posterMove.setByX(-1 * ((width / 8) + (width / 40)));
                    posterMove.setNode(m.getMoviePoster(i));
                    posterMove.setDuration(Duration.seconds(1));
                    TranslateTransition nameMove = new TranslateTransition();
                    nameMove.setByX(-1 * ((width / 8) + (width / 40)));
                    nameMove.setNode(m.getMovieLabel(i));
                    nameMove.setDuration(Duration.seconds(1));

                    if (i == m.numMoviesOnPage() - 1){
                        posterMove.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                m.updateMovies();
                                VBox v = m.getVBox(1);
                                ObservableList<Node> children = v.getChildren();
                                Label name = (Label)children.get(1);
                                System.out.println(name.getText());
                                //v.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                            }
                        });
                    }
                    nameMove.play();
                    posterMove.play();
                }
            }
        });*/
        VBox vBox = new VBox(boxLabel, arrowHBox, moviesBox);
        mainPage.getChildren().add(vBox);

    }

    public void setLabelBox(double width, double height, String id, HashMap<String, String> movieImgs) {
        forYouMovies.setPadding(new Insets(0, 0, 0, (width / 40) * 2));




        /*int count = 0;
        for (String movie : movieImgs.keySet()) {
            String imagePath = "file:" + movieImgs.get(movie);
            ImageView img = new ImageView(new Image(imagePath));
            img.setFitWidth(width / 8);
            img.setFitHeight(width / 6);
            img.setId(movie);
            moviePosters.add(img);
            Label movieName = new Label(movie);
            movieName.setFont(movieFont);
            movieName.setPadding(new Insets(20, 0, 0, 0));
            movieName.setWrapText(true);
            movieNames.add(movieName);
            VBox movieBox = new VBox(img, movieName);
            movieBox.setPadding(new Insets(0, (width / 40), 0, 0));
            movieBox.setPrefWidth(width / 8);
            movieBox.setId(movie);
            movieBoxes.add(movieBox);
            //movieBox.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            img.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0)");

            if (count < 6) forYouMovies.getChildren().add(movieBox);
            count++;
        }

        backArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FadeTransition f = new FadeTransition();
                f.setDuration(Duration.seconds(0.5));
                f.setToValue(0);
                f.setNode(moviePosters.get(currMovie));
                f.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });
                FadeTransition f2 = new FadeTransition();
                f2.setDuration(Duration.seconds(0.5));
                f2.setToValue(0);
                f2.setNode(movieNames.get(currMovie));
                f2.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });
                f.play();
                f2.play();
                currMovie++;
                //if ((currMovie + 1) == numMovies) currMovie = 0;
                //else currMovie++;
                //int currMovieCount = currMovie;
                TranslateTransition t = new TranslateTransition();
                t.setDuration(Duration.seconds(1));
                t.setByX(-1 * ((width / 8) + movieBoxes.get(currMovie).getInsets().getRight()));
                //if (currMovieCount == numMovies) currMovieCount++;
                t.setNode(moviePosters.get(0));
                /*t.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        VBox currMovieBox = (VBox)forYouMovies.getChildren().get(0);
                        ImageView oldMovieImg = (ImageView)currMovieBox.getChildren().get(0);
                        ImageView newMovieImg = (ImageView)t.getNode();
                        oldMovieImg.setImage(newMovieImg.getImage());
                        oldMovieImg.setId(newMovieImg.getId());

                        VBox newBox = (VBox)forYouMovies.getChildren().get(0);
                        ImageView img = (ImageView)currMovieBox.getChildren().get(0);
                        System.out.println(img.getId());
                        currMovie++;
                        //System.out.println(movieBoxes.get(0).getLayoutX());
                        /*ObservableList<Node> workingCollection = FXCollections.observableArrayList(forYouMovies.getChildren());
                        workingCollection.remove(0);
                        //Collections.swap(workingCollection, 0, 1);
                        forYouMovies.getChildren().setAll(workingCollection);
                        //forYouMovies.getChildren().remove(workingCollection.get(0));
                        System.out.println(forYouMovies.getChildren().get(0).getId());
                        System.out.println(forYouMovies.getChildren().size());

                    }
                });*/
                //t.play();
                /*int numMovies = movieBoxes.size();
                if ((currMovie + 6 - movieBoxes.size()) >= 0){
                    System.out.println("True");
                    forYouMovies.getChildren().add(movieBoxes.get(currMovie + 6 - movieBoxes.size()));
                }*/

                //forYouMovies.getChildren().get(0) = movieBoxes.get(1);
                //if ((currMovie + 1) == numMovies) currMovie = 0;
                //else currMovie++;
                //int currMovieCount = currMovie;
                /*TranslateTransition t = new TranslateTransition();
                t.setDuration(Duration.seconds(1));
                t.setByX(-1 * ((width / 8) + movieBoxes.get(currMovie).getInsets().getRight()));
                //if (currMovieCount == numMovies) currMovieCount++;
                t.setNode(movieBoxes.get(1));
                t.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        forYouMovies.getChildren().remove(movieBoxes.get(1));
                    }
                });
                t.play();
                //forYouMovies.getChildren().add(movieBoxes.get(6));
                /*for (int i = 1; i <= 6; i++){
                    TranslateTransition t = new TranslateTransition();
                    t.setDuration(Duration.seconds(1));
                    t.setByX(-1 * ((width / 8) + movieBoxes.get(currMovie).getInsets().getRight()));
                    if (currMovieCount == numMovies) currMovieCount++;
                    t.setNode(movieBoxes.get(currMovieCount));
                    t.play();
                    ++currMovieCount;
                }
            }
        });*/

    }

    private void setSideBarItem(HBox hbox, Color c, Color rectCol, String type){
        ObservableList<Node> currBoxChildren = hbox.getChildren();
        Button b = (Button)(currBoxChildren.get(0));
        b.setTextFill(c);
        Node btnIcon;
        if (b.getChildrenUnmodifiable().size() >= 1) btnIcon = b.getChildrenUnmodifiable().get(0);
        else btnIcon = b.getGraphic();
        String iconClass = btnIcon.getClass().getName();
        switch(iconClass)
        {
            case("de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView"):
                FontAwesomeIconView icon = (FontAwesomeIconView)btnIcon;
                icon.setFill(c);
                break;
            case("de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView"):
                MaterialDesignIconView materialIcon = (MaterialDesignIconView)btnIcon;
                materialIcon.setFill(c);
                break;
        }
        if (!type.equals("entered")) {
            Rectangle r = (Rectangle) (currBoxChildren.get(1));
            r.setFill(rectCol);
            r.setStroke(rectCol);
        }
    }

    private void setSideBarItemHandler(ObservableList<Node> sideBar, Button menuBtn, HBox hbox){
        menuBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        HBox currHBox = new HBox();
                        HBox newHBox = new HBox();
                        int found = 0;
                        for (int i = 0; i < sideBar.size(); i++){
                            if (sideBar.get(i).getClass().getName().equals("javafx.scene.layout.HBox")){
                                HBox box =(HBox) sideBar.get(i);
                                if (box.getId().contains("Item")) {
                                    Button b = (Button) (box.getChildren().get(0));
                                    if (sideBar.get(i).getId().equals(currMenuItem)) {
                                        System.out.println(sideBar.get(i).getId());
                                        ++found;
                                        currHBox = (HBox) sideBar.get(i);
                                    } else if (b.getId().equals(menuBtn.getId())){
                                        ++found;
                                        newHBox = (HBox)sideBar.get(i);
                                    }
                                    if (found == 2) break;
                                }
                            }
                        }
                        setSideBarItem(currHBox, LIGHTBLUE, SIDEBARCOLOR, "clicked");
                        setSideBarItem(newHBox, DARKBLUE, DARKBLUE, "clicked");
                        currMenuItem = newHBox.getId();

                    }
                });
        hbox.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        setSideBarItem(hbox, DARKBLUE, SIDEBARCOLOR, "entered");
                    }
                });
        hbox.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!(hbox.getId().equals(currMenuItem))) {
                            setSideBarItem(hbox, LIGHTBLUE, SIDEBARCOLOR, "exited");
                        }
                    }
                });
    }

    private FadeTransition makeFade(Node node, double setFromValue, double setToValue, double seconds){
        FadeTransition f = new FadeTransition();
        if (setFromValue > 0) f.setFromValue(setFromValue);
        f.setToValue(setToValue);
        f.setDuration(Duration.seconds(seconds));
        f.setNode(node);
        return f;
    }

    private FontAwesomeIconView makeIcon(FontAwesomeIcon f, String size){
        FontAwesomeIconView icon = new FontAwesomeIconView(f);
        icon.setSize(size);
        return icon;
    }

    private Button makeBtn(String text, double width, double height, Font f, Color textCol, Color background){
        Button b = new Button(text);
        if (width != -1) b.setPrefWidth(width);
        if (height != -1) b.setPrefHeight(height);
        b.setFont(f);
        b.setTextFill(textCol);
        b.setBackground(new Background(new BackgroundFill(background, CornerRadii.EMPTY, Insets.EMPTY)));
        return b;
    }

    private Rectangle makeRect(double width, double height, Color fill){
        Rectangle r = new Rectangle();
        r.setWidth(width);
        r.setHeight(height);
        r.setFill(fill);
        return r;
    }





    public void setSideIcons(double size){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
