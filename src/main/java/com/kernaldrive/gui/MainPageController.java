package com.kernaldrive.gui;

import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.sun.nio.sctp.PeerAddressChangeNotification;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import org.kordamp.ikonli.Ikonli;
import org.kordamp.ikonli.javafx.FontIcon;
import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MainPageController {
    private String currMenuItem = "homeMenuItem";
    private String boldFontPath = "file:C:\\Users\\idree\\Documents\\Java Projects\\KernalDrive\\varela\\Varela-Regular.otf";
    private Font movieFont = Font.loadFont(boldFontPath, 13);
    private Font sideBarMenuFont = Font.loadFont(boldFontPath, 18);
    private Font titleFont = Font.loadFont(boldFontPath, 22);



    private Color LIGHTBLUE = Color.rgb(153, 201, 255);
    private Color SIDEBARCOLOR = Color.rgb(238, 243, 244);
    private Color DARKBLUE = Color.rgb(0, 84, 180);

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
HBox titleBar;
@FXML
ScrollPane contentPage;
@FXML
VBox contentContainer;

    public void setSideBar(double width, double height, HashMap<String, String> groups){
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
        ObservableList<Node> boxChildren = sideBar.getChildren();
        for (String group: groups.keySet()){
            String groupType = groups.get(group);
            String iconCode = "";
            switch (groupType){
                case("Movie"):
                    iconCode = "fa-film";
                    break;
                case("TV"):
                    iconCode = "fa-tv";
                    break;
            }
            HBox box = addToSideBar(group, iconCode, width);
            box.setPadding(new Insets(0,0, height/50,0));
            boxChildren.add(box);
        }
        HBox settingItem = addToSideBar("Settings", "fa-gear", width);
        settingItem.setPadding(new Insets(height/25,0, height/50,0));

        HBox signOffItem = addToSideBar("Sign Off", "fa-power-off", width);
        signOffItem.setPadding(new Insets(0,0, height/50,0));
        boxChildren.addAll(settingItem, signOffItem);
    }

    private HBox addToSideBar(String menuText, String iconCode, double width){
        Button b = makeButton(menuText, sideBarMenuFont, LIGHTBLUE, SIDEBARCOLOR, (width / 45)*44, new Insets(0,0,0,width/8));
        FontIcon icon = makeIcon(iconCode, LIGHTBLUE, (int)sideBarMenuFont.getSize());
        b.setGraphic(icon);
        b.setAlignment(Pos.BASELINE_LEFT);

        Rectangle rectangle = makeRect(width/45, homeBtn.getHeight(), SIDEBARCOLOR);
        rectangle.setArcHeight(8);
        rectangle.setArcWidth(8);

        HBox hbox = new HBox(b, rectangle);
        hbox.setId(menuText+" item");
        hbox.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        b.setTextFill(DARKBLUE);
                        icon.setFill(DARKBLUE);
                        rectangle.setFill(DARKBLUE);
                    }
                });
        hbox.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!hbox.getId().equals(currMenuItem)) {
                            b.setTextFill(LIGHTBLUE);
                            icon.setFill(LIGHTBLUE);
                            rectangle.setFill(SIDEBARCOLOR);
                        }
                    }
                });
        b.addEventHandler(MouseEvent.MOUSE_CLICKED,
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
                        b.setTextFill(DARKBLUE);
                        icon.setFill(DARKBLUE);
                        rectangle.setFill(DARKBLUE);
                        currMenuItem = hbox.getId();
                    }
                });
        return hbox;
    }

    public void setContent(double width, double height, HashMap<String, String> movies){
        mainContent.setPrefWidth(width);
        mainContent.setPrefHeight(height);

        setTopBar(width, height);

        contentPage.setMaxWidth(width);
        contentPage.setMinHeight(height - topBar.getHeight());
        contentContainer.setPadding(new Insets(width/20,0,0,0));
        contentContainer.setMaxWidth(width);

        int movieRow = 0;
        double total = (width/40) * 2;
        while(true){
            movieRow++;
            total += width / 7.5;
            total += (width / 40) * 2;
            if (total > width) break;
        }

        VBox[] boxes = new VBox[movieRow];
        for (int i = 0; i < movieRow; i++){
            boxes[i] = new VBox();
            boxes[i].setPadding(new Insets(0,width/40,0,0));
        }

        int size;
        if (movies.size() % movieRow == 0) size = movies.size()/movieRow;
        else size = (movies.size()/movieRow) + 1;

        HBox[] rows = new HBox[size];
        for (int i = 0; i < rows.length; i++){
            rows[i] = new HBox();
            rows[i].setPadding(new Insets(height/20,0,0,width/40));
            rows[i].setMaxWidth(width);
        }

        int count = 0;
        for (String movie: movies.keySet()){
            VBox movieBox = new VBox();
            movieBox.setMaxWidth(width / 7.5);
            movieBox.setPadding(new Insets(0,width/40,0,0));

            String imagePath = movies.get(movie);
            //System.out.println(imagePath);
            ImageView moviePoster = new ImageView(new Image(imagePath));
            moviePoster.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0.8,0), 30, 0, 0, 0)");
            moviePoster.setFitWidth((width / 7.5));
            moviePoster.setFitHeight((width / 7.5) * 1.5);

            Label movieName = new Label(movie);
            movieName.setFont(movieFont);
            movieName.setMinHeight(Region.USE_PREF_SIZE);
            movieName.setPrefHeight(Region.USE_COMPUTED_SIZE);
            movieName.setPadding(new Insets(0,0,0,0));
            movieName.setWrapText(true);
            movieBox.getChildren().addAll(moviePoster, movieName);

            rows[count / 6].getChildren().add(movieBox);
            count++;
        }
        for (int i = 0; i < rows.length; i++){
            contentContainer.getChildren().add(rows[i]);
        }
        fixBlurryText(contentPage);
        System.out.println("True");
        //contentContainer.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void setTopBar(double width, double height){
        topBar.setPrefWidth(width);
        titleBar.setPrefWidth(width);
        topBar.setPadding(new Insets(height / 50, 0,0,0));

        Button titleButton = makeButton("HOME", titleFont, DARKBLUE, Color.WHITE, (width / 5) * 4, new Insets(0,0,0,width/20));
        titleButton.setAlignment(Pos.BASELINE_LEFT);
        titleBar.getChildren().add(titleButton);
        //topBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
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
}
