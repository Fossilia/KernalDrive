package com.kernaldrive.gui;

import com.kernaldrive.metadata.Movie;
import com.kernaldrive.metadata.MovieGroup;
import javafx.application.Platform;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.swing.*;
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
    private String boldFontPath = "file:C:\\Users\\ahmed\\IdeaProjects\\KernalDrive\\varela\\Varela-Regular.otf";
    private Font movieFont = Font.loadFont(boldFontPath, 20);
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
Button searchBarIcon;
@FXML
TextField searchBarInput;
@FXML
HBox titleBar;
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
        groupScreens[menuPos].setTitle("HOME");

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
                    addContent();
                }
            });
    }

    public void setContent(){
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
    public void addContent(){
        setTopBar(groupScreens[menuPos].getTitle());
        scrollPaneVBox.getChildren().clear();
        ArrayList<HBox> pageSections = groupScreens[menuPos].getContent(contentPageWidth, contentPageHeight, movieFont, visibleNodes);
        for (int i = 0; i < pageSections.size(); i++){
            scrollPaneVBox.getChildren().add(pageSections.get(i));
        }

        Button b = new Button("");
        b.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        b.setMinHeight(((contentPageWidth / 7.5) * 1.5) / 2);
        b.setId(scrollPaneVBox.getChildren().get(scrollPaneVBox.getChildren().size() - 1).getId());
        scrollPaneVBox.getChildren().add(b);


        contentPageScrollPane.vvalueProperty().addListener((obs) -> {
            groupScreens[menuPos].addRows(contentPageWidth, contentPageHeight, movieFont, visibleNodes, contentPageScrollPane, scrollPaneVBox, b);
        });
        contentPageScrollPane.hvalueProperty().addListener((obs) -> {
            groupScreens[menuPos].addRows(contentPageWidth, contentPageHeight, movieFont, visibleNodes, contentPageScrollPane, scrollPaneVBox, b);
        });
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
        searchBarInput.setMinHeight(searchBarIcon.getHeight());
    }

    private void setTopBar(String title){
        topBar.setPrefWidth(contentPageWidth);
        mainTopBar.setPrefWidth(contentPageWidth);
        titleBar.setPrefWidth(contentPageWidth);
        topBar.setPadding(new Insets(contentPageHeight / 50, 0,0,0));
        searchBarIcon.setFont(titleFont);
        searchBarIcon.setPadding(new Insets(0,0,0,contentPageWidth/20));
        searchBarInput.setMinHeight(searchBarIcon.getHeight());
        configureSearchBar();
        titleButton = makeButton(title, titleFont, DARKBLUE, Color.WHITE, (contentPageWidth / 5) * 4, new Insets(0,0,0,contentPageWidth/20));
        titleButton.setAlignment(Pos.BASELINE_LEFT);
        titleButton.setStyle("-fx-background-color: transparent");
        titleBar.getChildren().add(titleButton);
        //topBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void configureSearchBar(){
        searchBarInput.textProperty().addListener((observable, oldValue, newValue) -> {
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
        });

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

    @FXML
    private void loadMovie() throws Exception {
        System.out.println("1");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:///C:\\Users\\idree\\Documents\\Java Projects\\KernalDrive\\src\\main\\java\\com\\kernaldrive\\gui\\moviepage\\MoviePageTest.fxml"));
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
    }
}
