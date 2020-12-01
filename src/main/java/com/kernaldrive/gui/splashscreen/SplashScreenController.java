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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;


public class SplashScreenController {
    private String boldFontPath = "file:varela\\Varela-Regular.otf";
    private Font loadFont = Font.loadFont(boldFontPath, 22);
    private Font progressFont = Font.loadFont(boldFontPath, 18);
    private Font advanceButtonFont = Font.loadFont(boldFontPath, 16);
    private ProgressBar progressBar;
    private double screenWidth;

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
        this.screenWidth = width;
        splashScreen.setMinWidth(width);
        splashScreen.setMinHeight(height);
        splashScreen.setStyle("-fx-background-color: linear-gradient(to bottom left, rgba(40,43,82, 0.8) 20% , rgba(21,102,123, 0.8))");
        imageRow.setPadding(new Insets(height/25,0,height / 15,0));
        imageRow.setMinWidth(width);
        leftMargin.setMinWidth(width*0.325);
        leftMargin.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        logo.setFitWidth(width/3.5);
        loading.setText("Scanning " + movieGroup.getName() + " Group...");
        loading.setTextFill(Color.WHITE);
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

        progressBar = new ProgressBar();
        progressBar.setProgress(0);
        progressBar.setMinWidth((width / 10) * 9);
        progressBar.setMinHeight(height / 20);
        progressBar.setPadding(new Insets(0,0,0,width*0.1));
        screenContent.getChildren().addAll(progressBar);
    }

    /**
     * This function sets the increment for the progress bar
     */

    public void setProgressBar(String groupName, int moviesScanned, double toValue, double interval, String newText, Button advanceButton){
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
                if (moviesScanned > 0){
                    Button newGroup = new Button("Scanned " + groupName + "Group (" + moviesScanned + " movies)");
                    newGroup.setTextFill(Color.WHITE);
                    FontIcon icon = new FontIcon("fa-check-circle");
                    icon.setFill(Color.WHITE);
                    newGroup.setGraphic(icon);
                    newGroup.setFont(progressFont);
                    newGroup.setAlignment(Pos.BASELINE_LEFT);
                    newGroup.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                    progressReport.getChildren().add(newGroup);
                }
                if (toValue == 1.0){
                    Button advanceButtonLeftMargin = new Button("");
                    advanceButtonLeftMargin.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                    advanceButtonLeftMargin.setMinWidth(screenWidth*0.325);
                    screenContent.getChildren().remove(progressBar);
                    advanceButton.setAlignment(Pos.CENTER);
                    advanceButton.setMinWidth(screenWidth / 3.5);
                    advanceButton.setTextFill(Color.WHITE);
                    advanceButton.setFont(advanceButtonFont);
                    advanceButton.setPadding(new Insets(5,5,5,5));
                    advanceButton.setStyle("-fx-background-radius: 5px; -fx-background-color: #204666");
                    HBox advanceButtonHBox = new HBox(advanceButtonLeftMargin, advanceButton);
                    screenContent.getChildren().add(advanceButtonHBox);
                }
            }
        });
    }
}