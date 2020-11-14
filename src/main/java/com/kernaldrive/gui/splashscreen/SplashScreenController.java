package com.kernaldrive.gui.splashscreen;

import com.kernaldrive.metadata.MovieGroup;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.kordamp.ikonli.Ikonli;
import org.kordamp.ikonli.javafx.FontIcon;


public class SplashScreenController {
    private String boldFontPath = "file:C:\\Users\\HP\\IdeaProjects\\untitled\\varela\\Varela-Regular.otf";
    private Font loadFont = Font.loadFont(boldFontPath, 22);
    private Font progressFont = Font.loadFont(boldFontPath, 18);

    //private Label loading;
    private ProgressBar progressBar;
    private boolean updated;

    private Color BACKGROUND = Color.rgb(153, 201, 255);
@FXML
AnchorPane splashScreen;
@FXML
VBox screenContent;
@FXML
HBox imageRow;
@FXML
Button leftMargin;
@FXML
ImageView logo;
@FXML
Label loading;
@FXML
ScrollPane progressUpdate;
@FXML
VBox progressReport;

    public void setSplashScreen(double width, double height, MovieGroup movieGroup){
        splashScreen.setMinWidth(width);
        splashScreen.setMinHeight(height);
        //splashScreen.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        imageRow.setPadding(new Insets(height/25,0,height / 15,0));
        imageRow.setMinWidth(width);

        leftMargin.setMinWidth(width/3);
        leftMargin.setBackground(new Background(new BackgroundFill(BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));
        logo.setFitWidth(width/3.5);

        loading.setText("Scanning " + movieGroup.getName() + " Group...");
        loading.setPadding(new Insets(0,0,height/25,0));
        loading.setFont(loadFont);
        loading.setMinWidth(width);
        loading.setAlignment(Pos.CENTER);

        progressUpdate.setMaxHeight(height / 6);
        progressUpdate.setMaxWidth((width / 10) * 9);
        progressUpdate.setPadding(new Insets(0,0,0,width*0.1));
        progressUpdate.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        progressReport.setPadding(new Insets(0,0,0,width*0.1));
        progressReport.setMinWidth((width / 10) * 9);
        progressReport.setMaxHeight(height / 6);
        //progressReport.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        //progressUpdate.setPadding(new Insets(0, 0, 0, width/3));

        progressBar = new ProgressBar();
        progressBar.setProgress(0);
        progressBar.setMinWidth((width / 10) * 9);
        progressBar.setMinHeight(height / 20);
        progressBar.setPadding(new Insets(0,0,0,width*0.1));
        screenContent.getChildren().addAll(progressBar);
        //screenContent.getChildren().addAll(loading, scrollPane, progressBar);
    }

    public void setProgressBar(String groupName, int movieSize, double toValue, double interval, String newText, Button advanceButton){
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(progressBar.progressProperty(), toValue);
        KeyFrame keyFrame = new KeyFrame(new Duration(interval), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        progressBar.setProgress(toValue);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loading.setText(newText);
                if (movieSize > 0){
                    Button newGroup = new Button("Scanned " + groupName + "Group (" + movieSize + " movies)");
                    FontIcon icon = new FontIcon("fa-check-circle");
                    newGroup.setGraphic(icon);
                    newGroup.setFont(progressFont);
                    newGroup.setAlignment(Pos.BASELINE_LEFT);
                    newGroup.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                    progressReport.getChildren().add(newGroup);
                }
                if (toValue == 1.0){
                    screenContent.getChildren().remove(progressBar);
                    advanceButton.setAlignment(Pos.CENTER);
                    advanceButton.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                    screenContent.getChildren().add(advanceButton);
                }
            }
        });
    }
}
