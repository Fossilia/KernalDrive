package com.company;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;

//FOR FONTS
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

//FOR IMAGES
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;

import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;

public class Main extends Application {

    Button fileButton, backButton;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Homepage - KernalDrive");
        Pane layout = new Pane();
        Pane layout2 = new Pane();  //For About Us Page
        Scene scene = new Scene(layout); //, 1500, 700);
        Scene scene1 = new Scene(layout2, 1500,700);    //For About Us Page

        layout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        layout2.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        FileChooser fileChooser = new FileChooser();

        //ADDING BUTTON-------------------------------
        fileButton = new Button();
        fileButton.setText("File Chooser");
        fileButton.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 25));
        fileButton.setStyle("-fx-text-fill: #0054b4;");
        fileButton.setLayoutX(1050);
        fileButton.setLayoutY(700);
        layout.getChildren().addAll(fileButton);

        fileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
            }
        });

        //ADDING LOGO-------------------------------
        FileInputStream inputStream = new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\LOGO.png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);
        imageView.setX(450);
        imageView.setY(100);
        layout.getChildren().add(imageView);

        //ADDING LABEL
        Label label1 = new Label("Click Here to Choose a Media File:");
        label1.setStyle("-fx-text-fill: #0054B4;");
        label1.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 25));
        label1.setMaxWidth(Double.MAX_VALUE);
        label1.setLayoutX(615);
        label1.setLayoutY(710);
        layout.getChildren().add(label1);

        /*//ADDING TEXTFIELD-------------------------------
        TextField textField = new TextField ("Type Something Here...");
        textField.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 25));
        textField.setStyle("-fx-text-fill: #0054B4;");
        textField.setLayoutX(480);
        textField.setLayoutY(600);
        textField.setPrefWidth(1000);
        */

        //ADDING SETTINGS ICON-------------------------------
        FileInputStream inputStream1 = new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Settings.png");
        Image settings = new Image(inputStream1);
        ImageView imageView1 = new ImageView(settings);
        imageView1.setFitHeight(115);
        imageView1.setPreserveRatio(true);
        imageView1.setX(1780);
        imageView1.setY(0);
        imageView1.setPickOnBounds(true);

        //Clicking Settings Icon
        imageView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Settings - Currently Unavailable");
                alert.setTitle("Settings");
                alert.setContentText("Hello, we currently do not have any settings to change! But that may change after our first few iterations");
                alert.show();
            }
        });
        layout.getChildren().add(imageView1);

        //ADDING HOME ICON-------------------------------
        FileInputStream inputStream2 = new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Home.png");
        Image home = new Image(inputStream2);
        ImageView imageView2 = new ImageView(home);
        imageView2.setFitHeight(115);
        imageView2.setPreserveRatio(true);
        imageView2.setX(1780);
        imageView2.setY(115);
        layout.getChildren().add(imageView2);

        //ADDING ABOUT US ICON-------------------------------
        FileInputStream inputStream3 = new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\About-Us.png");
        Image aboutUs = new Image(inputStream3);
        ImageView imageView3 = new ImageView(aboutUs);
        imageView3.setFitHeight(115);
        imageView3.setPreserveRatio(true);
        imageView3.setX(1780);
        imageView3.setY(230);
        imageView3.setPickOnBounds(true);

        //Clicking About Us Icon
        imageView3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                primaryStage.setScene(scene1);
                Label label2 = new Label("About Us");
                label2.setStyle("-fx-text-fill: #0054B4;");
                try {
                    label2.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 45));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                label2.setMaxWidth(Double.MAX_VALUE);
                label2.setLayoutX(630);
                label2.setLayoutY(50);

                Label label3 = new Label("We are 5 Computer Science students who attend the University of Windsor.\n" +
                        "We are excited to keep working on this project! ");
                label3.setStyle("-fx-text-fill: #0054B4;");

                try {
                    label3.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 30));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                label3.setMaxWidth(Double.MAX_VALUE);
                label3.setLayoutX(200);
                label3.setLayoutY(300);

                backButton = new Button();
                backButton.setText("Back");

                try {
                    backButton.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 25));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                backButton.setStyle("-fx-text-fill: #0054b4;");
                backButton.setLayoutX(680);
                backButton.setLayoutY(490);

                backButton.setOnAction(event1 -> primaryStage.setScene(scene));
                primaryStage.setMaximized(true);
                layout2.getChildren().addAll(label2, label3, backButton);

            }
        });

        layout.getChildren().add(imageView3);

        HBox hb = new HBox();
        hb.setSpacing(0);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);        //starts program as full screen
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);   //sets up program as javafx application
    }
}