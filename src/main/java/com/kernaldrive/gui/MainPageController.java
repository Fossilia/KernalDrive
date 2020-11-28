package com.kernaldrive.gui;

import com.kernaldrive.metadata.Movie;
import com.kernaldrive.metadata.MovieGroup;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.kernaldrive.gui.moviepage.MoviePageController;
import com.kernaldrive.metadata.Movie;
import com.kernaldrive.metadata.TmdbFilenameSearch;
import com.kernaldrive.metadata.TmdbMovieExtractor;
import com.sun.nio.sctp.PeerAddressChangeNotification;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Window;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikonli;
import org.kordamp.ikonli.javafx.FontIcon;
import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainPageController {
    private ObservableList<Node> visibleNodes = FXCollections.observableArrayList();
    private int menuPos;
    private GroupScreen[] groupScreens;
    private double contentPageWidth;
    private double contentPageHeight;
    private Task<Void> searchTask;

    private String currMenuItem = "homeMenuItem";
    private String boldFontPath = "file:varela\\Varela-Regular.otf";
    private Font movieFont = Font.loadFont(boldFontPath, 16);
    private Font sideBarMenuFont = Font.loadFont(boldFontPath, 18);
    private Font titleFont = Font.loadFont(boldFontPath, 22);

    private Color LIGHTBLUE = Color.rgb(153, 201, 255);
    private Color SIDEBARCOLOR = Color.rgb(32, 71, 102);
    private Color DARKBLUE = Color.rgb(26, 158, 197);
    private Button titleButton;

@FXML
HBox screenWrapper;
@FXML
VBox sideBar;
@FXML
Button userIconBtn;
@FXML
FontIcon userIcon;
@FXML
Label userGreeting;
@FXML
HBox homeMenuItem;
@FXML
Button homeBtn;
@FXML
FontIcon homeIcon;
@FXML
Rectangle homeRect;
@FXML
VBox mainContent;
@FXML
VBox topBar;
@FXML
HBox mainTopBar;
@FXML
Button pageTitleButton;
@FXML
VBox searchBarVBox;
@FXML
Button searchBarTopMargin;
@FXML
HBox searchBarHBox;
@FXML
Button searchBarIcon;
@FXML
ComboBox comboBoxSearchBar;
@FXML
Button recommendedButton;
@FXML
Button libraryButton;
@FXML
Button topBarMiddleMargin;
@FXML
ScrollPane contentPageScrollPane;
@FXML
VBox scrollPaneVBox;

    //This function sets the side bar for the main page
    public void setSideBar(double width, double height, ArrayList<MovieGroup> groups){
        //These two variables track the width and height of the content pages
        contentPageWidth = (width * 6) - width;
        contentPageHeight = height;

        //This list contains the different group screens that the user can move through by navigating
        //through the sidebar
        groupScreens = new GroupScreen[groups.size() + 3];
        for (int i = 0; i < groupScreens.length; i++){
            groupScreens[i] = new GroupScreen();
        }

        //This variable keeps track of which side bar item one is on, at the beginning one is on the home
        //screen which is positon 0
        menuPos = 0;
        screenWrapper.setPrefHeight(height);
        screenWrapper.setPrefWidth(width*6);
        sideBar.setPrefSize(width, height);
        userIcon.setIconSize(40);
        userIconBtn.setMinWidth(width);
        userIconBtn.setPadding(new Insets(height/50,0,0,0));
        userGreeting.setMinWidth(width);
        userGreeting.setPadding(new Insets(height/50, 0,height/40,0));

        homeMenuItem.setPrefWidth(width);
        homeMenuItem.setPadding(new Insets(0,0, height/50,0));
        homeBtn.setFont(sideBarMenuFont);
        homeBtn.setPadding(new Insets(0,0,0,width/8));
        homeIcon.setIconSize((int)sideBarMenuFont.getSize());
        homeBtn.setMinWidth((width / 45) * 44);
        homeRect.setWidth(width / 45);
        homeRect.setHeight(homeBtn.getHeight());
        addEventListener(menuPos, homeMenuItem, homeBtn, homeIcon, homeRect);
        groupScreens[menuPos].setMovieGroups(groups);
        groupScreens[menuPos].setTitle("Home");

        //The remaining code in this function adds each of the side bar menu items
        ObservableList<Node> boxChildren = sideBar.getChildren();
        int pos = 1;
        for (int i = 0; i < groups.size(); i++){
            String iconCode = "fa-film";
            groupScreens[pos].addMovieGroups(groups.get(i));
            groupScreens[pos].setTitle(groups.get(i).getName());
            HBox box = addToSideBar(pos, groups.get(i).getName(), iconCode, width);
            pos++;
            box.setPadding(new Insets(0,0, height/50,0));
            boxChildren.add(box);
        }
        HBox settingItem = addToSideBar(menuPos, "Settings", "fa-gear", width);
        groupScreens[pos].setTitle("SETTINGS");
        pos++;
        settingItem.setPadding(new Insets(height/25,0, height/50,0));

        HBox signOffItem = addToSideBar(menuPos, "Sign Off", "fa-power-off", width);
        groupScreens[pos].setTitle("SIGN OFF");
        pos++;
        signOffItem.setPadding(new Insets(0,0, height/50,0));
        boxChildren.addAll(settingItem, signOffItem);
    }

    private HBox addToSideBar(int pos, String menuText, String iconCode, double width){
        Button b = makeButton(menuText, sideBarMenuFont, LIGHTBLUE, SIDEBARCOLOR, (width / 45)*44, new Insets(0,0,0,width/8));
        FontIcon icon = makeIcon(iconCode, LIGHTBLUE, (int)sideBarMenuFont.getSize());
        b.setGraphic(icon);
        b.setAlignment(Pos.BASELINE_LEFT);

        Rectangle rectangle = makeRect(width/45, homeBtn.getHeight(), SIDEBARCOLOR);
        rectangle.setArcHeight(8);
        rectangle.setArcWidth(8);

        HBox hbox = new HBox(b, rectangle);
        hbox.setId(menuText+" item");
        addEventListener(pos, hbox, b, icon, rectangle);
        return hbox;
    }

    public void addEventListener(int pos, HBox hBox, Button button, FontIcon icon, Rectangle rectangle){
        hBox.addEventHandler(MouseEvent.MOUSE_ENTERED,
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    button.setTextFill(DARKBLUE);
                    icon.setFill(DARKBLUE);
                    rectangle.setFill(DARKBLUE);
                }
            });
        hBox.addEventHandler(MouseEvent.MOUSE_EXITED,
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!hBox.getId().equals(currMenuItem)) {
                        button.setTextFill(LIGHTBLUE);
                        icon.setFill(LIGHTBLUE);
                        rectangle.setFill(SIDEBARCOLOR);
                    }
                }
            });
        button.addEventHandler(MouseEvent.MOUSE_CLICKED,
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ObservableList<Node> sideBarItems = sideBar.getChildren();
                    for (int i = 0; i < sideBarItems.size(); i++){
                        if (sideBarItems.get(i).getClass().getName().equals("javafx.scene.layout.HBox")){
                            if (sideBarItems.get(i).getId().equals(currMenuItem)){
                                HBox itemBox = (HBox)sideBarItems.get(i);
                                Button menuBtn = (Button)itemBox.getChildren().get(0);
                                menuBtn.setTextFill(LIGHTBLUE);
                                FontIcon icon = (FontIcon)menuBtn.getGraphic();
                                icon.setFill(LIGHTBLUE);

                                Rectangle menuRect = (Rectangle)itemBox.getChildren().get(1);
                                menuRect.setFill(SIDEBARCOLOR);
                                menuRect.setStroke(SIDEBARCOLOR);
                            }
                        }
                    }
                    button.setTextFill(DARKBLUE);
                    icon.setFill(DARKBLUE);
                    rectangle.setFill(DARKBLUE);
                    currMenuItem = hBox.getId();
                    menuPos = pos;
                    try {
                        addContent();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void setContent() throws FileNotFoundException {
        mainContent.setPrefWidth(contentPageWidth);
        mainContent.setPrefHeight(contentPageHeight);
        mainContent.setStyle("-fx-background-color: linear-gradient(to bottom left, rgba(40,43,82, 0.8) 20% , rgba(21,102,123, 0.8))");

        contentPageScrollPane.setMaxWidth(contentPageWidth);
        contentPageScrollPane.setMinHeight(contentPageHeight - topBar.getHeight());
        scrollPaneVBox.setPadding(new Insets(contentPageWidth/20,0,0,0));
        scrollPaneVBox.setMaxWidth(contentPageWidth);
        addContent();

    }
        //contentContainer.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
    public void addContent() throws FileNotFoundException {
        setTopBar(groupScreens[menuPos].getTitle());
        scrollPaneVBox.getChildren().clear();
        ArrayList<HBox> pageSections = groupScreens[menuPos].getContent(contentPageWidth, contentPageHeight, movieFont, visibleNodes);
        for (int i = 0; i < pageSections.size(); i++) {
            scrollPaneVBox.getChildren().add(pageSections.get(i));
        }

        Button b = new Button("");
        b.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        b.setMinHeight(((contentPageWidth / 7.5) * 1.5) / 2);
        b.setId(scrollPaneVBox.getChildren().get(scrollPaneVBox.getChildren().size() - 1).getId());
        scrollPaneVBox.getChildren().add(b);


        /*contentPageScrollPane.vvalueProperty().addListener((obs) -> {
            groupScreens[menuPos].addRows(contentPageWidth, contentPageHeight, movieFont, visibleNodes, contentPageScrollPane, scrollPaneVBox, b);
        });
        contentPageScrollPane.hvalueProperty().addListener((obs) -> {
            groupScreens[menuPos].addRows(contentPageWidth, contentPageHeight, movieFont, visibleNodes, contentPageScrollPane, scrollPaneVBox, b);
        });*/

    }

    public void fixPane(){
        fixBlurryText(contentPageScrollPane);
        ObservableList<Node> sideBarChildren = sideBar.getChildren();
        for (int i = 2; i < sideBarChildren.size(); i++){
            HBox box = (HBox)sideBarChildren.get(i);
            Button b = (Button)sideBarChildren.get(0);
            Rectangle r = (Rectangle) box.getChildren().get(1);
            r.setHeight(homeBtn.getHeight());
        }
        searchBarVBox.setMinWidth(searchBarIcon.getWidth() + (contentPageWidth / 5));
        //searchBarVBox.setMaxHeight(comboBoxSearchBar.getHeight());
        searchBarHBox.setMinWidth(searchBarIcon.getWidth() + (contentPageWidth / 5));
        searchBarHBox.setMaxHeight(comboBoxSearchBar.getHeight());
        searchBarHBox.setPadding(new Insets(2.5, 3, 2.5, 3)); //margins around the whole grid;
        //searchBarTopMargin.setMinHeight((pageTitleButton.getHeight() - searchBarHBox.getHeight()) / 2);
        recommendedButton.setMinHeight(pageTitleButton.getHeight());
        //recommendedButton.setFont(movieFont);
        //recommendedButton.setPadding(new Insets());
        libraryButton.setMinHeight(pageTitleButton.getHeight());
        System.out.println(contentPageWidth +  ", " + pageTitleButton.getWidth() + ", " + recommendedButton.getWidth() + ", " + libraryButton.getWidth());
        topBarMiddleMargin.setMinWidth(contentPageWidth - (pageTitleButton.getWidth()+recommendedButton.getWidth()+libraryButton.getWidth() + (contentPageWidth / 3.8)));
        //libraryButton.setFont(movieFont);
        comboBoxSearchBar.setMinWidth(contentPageWidth / 5);
        comboBoxSearchBar.getEditor().setMinWidth(contentPageWidth/5);
        //comboBoxSearchBar.setMinHeight(searchBarIcon.getHeight());
    }

    private <T> void setTopBar(String title){
        topBar.setPrefWidth(contentPageWidth);
        mainTopBar.setPrefWidth(contentPageWidth);
        mainTopBar.setPadding(new Insets(15,0,15,0));
        //pageTitleButton.setMinHeight(contentPageHeight/20);
        pageTitleButton.setText(title);
        //pageTitleButton.setFont(sideBarMenuFont);
        //topBar.setPadding(new Insets(contentPageHeight / 50, 0,0,0));
        //comboBoxSearchBar.getEditor().setPrefWidth(contentPageWidth/2);
        ComboBoxListViewSkin<T> comboBoxListViewSkin = new ComboBoxListViewSkin<T>(comboBoxSearchBar);
        comboBoxListViewSkin.getPopupContent().addEventFilter(KeyEvent.ANY, (event) -> {
            if( event.getCode() == KeyCode.SPACE ) {
                event.consume();
            }
        });
        comboBoxSearchBar.setSkin(comboBoxListViewSkin);
        configureSearchBar();
        titleButton = makeButton(title, titleFont, DARKBLUE, Color.WHITE, (contentPageWidth / 5) * 4, new Insets(0,0,0,contentPageWidth/20));
        titleButton.setAlignment(Pos.BASELINE_LEFT);
        titleButton.setStyle("-fx-background-color: transparent");
        //topBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void configureSearchBar(){
        comboBoxSearchBar.getEditor().textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                comboBoxSearchBar.getItems().clear();
                comboBoxSearchBar.hide(); //before you set new visibleRowCount value
                if (!newValue.isEmpty()) {
                    ArrayList<ArrayList<String>> searchResults = groupScreens[0].matchMoviesBySearch(newValue);
                    ArrayList<String> movieTitles = searchResults.get(0);
                    ArrayList<String> moviePosters = searchResults.get(1);
                    comboBoxSearchBar.getItems().addAll(movieTitles);
                    //System.out.println(movieTitles.size());
                    comboBoxSearchBar.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                        @Override
                        public ListCell<String> call(ListView<String> p) {
                            final ListCell<String> cell = new ListCell<String>() {
                                {
                                    super.setMinWidth(contentPageWidth/7);
                                }
                                @Override
                                protected void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    setText(item);
                                    if (item == null || empty) {
                                        setGraphic(null);
                                    } else {
                                        Image icon = null;
                                        try {
                                            int iconNumber = this.getIndex();
                                            if (iconNumber < moviePosters.size())
                                                icon = new Image(new FileInputStream(moviePosters.get(iconNumber)));
                                        } catch(NullPointerException | FileNotFoundException ex) {
                                            // in case the above image doesn't exist, use a default one
                                            String iconPath = "posters/120.jpg";
                                            try {
                                                icon = new Image(new FileInputStream(iconPath));
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        ImageView iconImageView = new ImageView(icon);
                                        iconImageView.setFitHeight(contentPageHeight/15);
                                        iconImageView.setPreserveRatio(true);
                                        setGraphic(iconImageView);
                                    }
                                }
                            };
                            return cell;
                        }
                    });
                    if (movieTitles.size() > 6){
                        comboBoxSearchBar.setVisibleRowCount(6);
                    }
                    else comboBoxSearchBar.setVisibleRowCount(movieTitles.size());
                    comboBoxSearchBar.show();
                }
            }
        });
        /*searchBarInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (searchTask.isRunning()) System.out.println("True");
            searchTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    groupScreens[0].matchMoviesBySearch(newValue, this);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                    return null;
                }
            };
            new Thread(searchTask).start();
        });*/

    }

    private void fixBlurryText(ScrollPane scrollPane) {
        StackPane stackPane = (StackPane) scrollPane.lookup("ScrollPane .viewport");
        stackPane.setCache(false);
    }

    private Button makeButton(String text, Font f, Color textCol, Color buttonBackground, double width, Insets padding){
        Button b = new Button(text);
        b.setFont(f);
        b.setTextFill(textCol);
        b.setBackground(new Background(new BackgroundFill(buttonBackground, CornerRadii.EMPTY, Insets.EMPTY)));
        b.setMinWidth(width);
        b.setPadding(padding);
        return b;
    }

    private FontIcon makeIcon(String iconCode, Color c, int size){
        FontIcon icon = new FontIcon(iconCode);
        icon.setIconColor(c);
        icon.setIconSize(size);
        return icon;
    }

    private Rectangle makeRect(double width, double height, Color c){
        Rectangle r = new Rectangle();
        r.setFill(c);
        r.setWidth(width);
        r.setHeight(height);
        return r;
    }

    /*@FXML
    private void loadMovie() throws Exception {
        System.out.println("1");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:///C:\\Users\\Faisal\\Documents\\Github\\KernalDrive\\src\\main\\java\\com\\kernaldrive\\gui\\moviepage\\MoviePageTest.fxml"));
        //contentContainer = loader.load();
        //contentContainer.getScene().setRoot(loader.load());
        mainContent.getChildren().removeAll(contentPage);
        mainContent.getChildren().add(loader.load());
        MoviePageController moviePageController = loader.getController();
        TmdbFilenameSearch tmdbFilenameSearch = new TmdbFilenameSearch();
        TmdbMovieExtractor tmdbMovieExtractor = new TmdbMovieExtractor();
        int movieID = tmdbFilenameSearch.searchTmdb("Star wars revenge of the sith 2005");
        Movie movie = tmdbMovieExtractor.extractMovieInfo("", movieID);
        moviePageController.setMovie(movie);
        moviePageController.setLabels();
        System.out.println("2");
    }*/
}
