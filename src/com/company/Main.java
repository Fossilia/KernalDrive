package com.company;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;

//FOR FONTS
import javafx.scene.text.Font;

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
import javafx.stage.*;

import java.awt.*;
import java.awt.TextArea;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

public class Main extends Application {

    private Button folderButton, backButton, addGroupButton;
    private ArrayList<File> groupPaths;
    private ArrayList<MediaGroup> mediaGroups;



    @Override
    public void start(Stage primaryStage) throws Exception {

        groupPaths = new ArrayList<File>();
        mediaGroups = new ArrayList<MediaGroup>();

        primaryStage.initStyle(StageStyle.UTILITY);     //removes minimize and fullscreen button

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        primaryStage.setTitle("Homepage - KernalDrive");

        Pane layout = new Pane();
        Pane layout2 = new Pane();  //For About Us Page

        Scene scene = new Scene(layout, 1920, 1080);
        Scene scene1 = new Scene(layout2, 1920,1080);    //For About Us Page



        //CSS SHEETS
        scene.getStylesheets().add("/com/company/list-view.css");
        scene1.getStylesheets().add("/com/company/list-view.css");

        //CREATING A LISTVIEW OF THE PATH
        ListView<String> folderPath = new ListView<>();
        folderPath.setLayoutX((screenWidth/30)*11);
        folderPath.setLayoutY((screenHeight/23)*15);
        folderPath.setPrefSize((screenWidth/30)*12.1, (screenHeight/20)*2.7);
        folderPath.getItems().add("Below is a List of Your Directory Paths:");
        folderPath.setStyle("-fx-text-fill: #0054B4;");

        layout.getChildren().add(folderPath);

        layout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        layout2.setBackground(new Background(new BackgroundFill(Color.rgb(33, 32, 31), CornerRadii.EMPTY, Insets.EMPTY)));

        //ADDING TEXTFIELD-------------------------------
        TextField textField = new TextField ();
        textField.setPromptText("Enter Group Name...");
        textField.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 25));
        textField.setStyle("-fx-text-fill: #0054B4;");
        textField.setLayoutX((screenWidth/30)*6.5);
        textField.setLayoutY((screenHeight/20)*11.1);
        textField.setPrefWidth(700);
        layout.getChildren().add(textField);


        //ADDING COMBOBOX---------------------------
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Movies",
                        "TV"
                );

        ComboBox comboBox = new ComboBox(options);

        comboBox.getEditor().setFont(Font.font("Varela-Regular.ttf"));
        comboBox.setPromptText("Group Type");
        comboBox.setLayoutX((screenWidth/20*12.7));
        comboBox.setLayoutY((screenHeight/20)*11.1);
        comboBox.setPrefWidth(260);
        comboBox.setStyle("-fx-text-fill: #0054B4;");
        comboBox.setStyle("-fx-font-size: 25");

        layout.getChildren().add(comboBox);

        //ADDING BUTTON-------------------------------
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");

        folderButton = new Button();
        folderButton.setText("Add Directories");
        folderButton.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 25));
        folderButton.setStyle("-fx-text-fill: #0054b4;");
        folderButton.setLayoutX((screenWidth/30)*6.5);
        folderButton.setLayoutY((screenHeight/23)*15);
        //folderButton.setMinWidth();
        layout.getChildren().addAll(folderButton);

        addGroupButton = new Button();
        addGroupButton .setText("Add Group");
        addGroupButton .setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 25));
        addGroupButton .setStyle("-fx-text-fill: #0054b4;");
        addGroupButton .setLayoutX((screenWidth/30)*6.5);
        addGroupButton .setLayoutY((screenHeight/23)*17);
        layout.getChildren().addAll(addGroupButton );

        folderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File selectedDirectory = directoryChooser.showDialog(primaryStage);

                if(selectedDirectory == null){
                    //No Directory selected
                }else{
                    //textArea.append(selectedDirectory.getAbsolutePath());
                    //System.out.println(selectedDirectory.);

                    groupPaths.add(selectedDirectory);
                    folderPath.getItems().add(selectedDirectory.toString());

                }
            }
        });

        addGroupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addGroup(textField.getText(), comboBox.getValue().toString());
                clearScreen(textField, comboBox, folderPath);
            }
        });

        //ADDING LOGO-------------------------------
        FileInputStream inputStream = new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\LOGO.png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight((screenHeight/8)*3);
        imageView.setPreserveRatio(true);
        imageView.setX((screenWidth/30)*6.5);
        imageView.setY((screenHeight/20)*2);
        layout.getChildren().add(imageView);

        //ADDING SETTINGS ICON-------------------------------
        FileInputStream inputStream1 = new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Settings.png");
        Image settings = new Image(inputStream1);
        ImageView imageView1 = new ImageView(settings);
        imageView1.setFitHeight((screenHeight/18)*2.5);
        imageView1.setPreserveRatio(true);
        imageView1.setX((screenWidth/14)*12.9);
        imageView1.setY((screenHeight/20)*1.9);
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
        imageView2.setFitHeight((screenHeight/18)*2.5);
        imageView2.setPreserveRatio(true);
        imageView2.setX((screenWidth/14)*12.9);
        imageView2.setY((screenHeight/23)*8.7);
        layout.getChildren().add(imageView2);

        //ADDING ABOUT US ICON-------------------------------
        FileInputStream inputStream3 = new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\About-Us.png");
        Image aboutUs = new Image(inputStream3);
        ImageView imageView3 = new ImageView(aboutUs);
        imageView3.setFitHeight((screenHeight/18)*2.5);
        imageView3.setPreserveRatio(true);
        imageView3.setX((screenWidth/14)*12.9);
        imageView3.setY((screenHeight/23)*15);
        imageView3.setPickOnBounds(true);

        //Clicking About Us Icon
        imageView3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                primaryStage.setScene(scene1);

                Label label2 = new Label("About Us");
                label2.setStyle("-fx-text-fill: #e8f4f8;");
                try {
                    label2.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 60));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                label2.setMaxWidth(Double.MAX_VALUE);
                label2.setLayoutX((screenWidth/20)*8.5);
                label2.setLayoutY((screenHeight/20)*1);

                Label label3 = new Label("We are 5 Computer Science students who attend the University of Windsor.");
                label3.setStyle("-fx-text-fill: #e8f4f8;");

                try {
                    label3.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 30));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                label3.setMaxWidth(Double.MAX_VALUE);
                label3.setLayoutX((screenWidth/20)*4.5);
                label3.setLayoutY((screenHeight/20)*15);

                //AHMED'S PICTURE / LABEL
                FileInputStream inputStream4 = null;
                try {
                    inputStream4 = new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Ahmed.png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image ahmed = new Image(inputStream4);
                ImageView imageView3 = new ImageView(ahmed);
                imageView3.setPreserveRatio(true);
                imageView3.setX((screenWidth/14)*2);
                imageView3.setY((screenHeight/23)*5);
                layout2.getChildren().add(imageView3);

                Label ahmedTag = new Label("Ahmed Nuur");
                ahmedTag.setFont(Font.font("Varela-Regular", 25));
                ahmedTag.setLayoutX((screenWidth/14)*2.4);
                ahmedTag.setLayoutY((screenHeight/23)*10.7);
                ahmedTag.setStyle("-fx-text-fill: #e8f4f8");
                layout2.getChildren().add(ahmedTag);

                //WESAM'S PICTURE / LABEL
                FileInputStream inputStream5 = null;
                try {
                    inputStream5 = new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Wesam.png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image wesam = new Image(inputStream5);
                ImageView imageView4 = new ImageView(wesam);
                imageView4.setPreserveRatio(true);
                imageView4.setX((screenWidth/14)*6);
                imageView4.setY((screenHeight/23)*5);
                layout2.getChildren().add(imageView4);

                Label wesamTag = new Label("Wesam Hussain");
                wesamTag.setFont(Font.font("Varela-Regular", 25));
                wesamTag.setLayoutX((screenWidth/14)*6.3);
                wesamTag.setLayoutY((screenHeight/23)*10.7);
                wesamTag.setStyle("-fx-text-fill: #e8f4f8");
                layout2.getChildren().add(wesamTag);

                //FAISAL'S PICTURE / LABEL
                FileInputStream inputStream6 = null;
                try {
                    inputStream6 = new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Faisal.png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image faisal = new Image(inputStream6);
                ImageView imageView5 = new ImageView(faisal);
                imageView5.setPreserveRatio(true);
                imageView5.setX((screenWidth/14)*10);
                imageView5.setY((screenHeight/23)*5);
                layout2.getChildren().add(imageView5);

                Label faisalTag = new Label("Faisal Bagalagel");
                faisalTag.setFont(Font.font("Varela-Regular", 25));
                faisalTag.setLayoutX((screenWidth/14)*10.3);
                faisalTag.setLayoutY((screenHeight/23)*10.7);
                faisalTag.setStyle("-fx-text-fill: #e8f4f8");
                layout2.getChildren().add(faisalTag);


                //IDREES'S PICTURE / LABEL
                FileInputStream inputStream7 = null;
                try {
                    inputStream7 = new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Idrees.png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image idrees = new Image(inputStream7);
                ImageView imageView6 = new ImageView(idrees);
                imageView6.setPreserveRatio(true);
                imageView6.setX((screenWidth/14)*4);
                imageView6.setY((screenHeight/23)*9);
                layout2.getChildren().add(imageView6);

                Label idreesTag = new Label("Idrees Syed");
                idreesTag.setFont(Font.font("Varela-Regular", 25));
                idreesTag.setLayoutX((screenWidth/14)*4.5);
                idreesTag.setLayoutY((screenHeight/23)*14.7);
                idreesTag.setStyle("-fx-text-fill: #e8f4f8");
                layout2.getChildren().add(idreesTag);

                //IBRAHIM'S PICTURE / LABEL
                FileInputStream inputStream8 = null;
                try {
                    inputStream8 = new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Ibrahim.png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image ibrahim = new Image(inputStream8);
                ImageView imageView7 = new ImageView(ibrahim);
                imageView7.setPreserveRatio(true);
                imageView7.setX((screenWidth/14)*8);
                imageView7.setY((screenHeight/23)*9);
                layout2.getChildren().add(imageView7);

                Label ibrahimTag = new Label("Ibrahim Aloran");
                ibrahimTag.setFont(Font.font("Varela-Regular", 25));
                ibrahimTag.setLayoutX((screenWidth/14)*8.4);
                ibrahimTag.setLayoutY((screenHeight/23)*14.7);
                ibrahimTag.setStyle("-fx-text-fill: #e8f4f8");
                layout2.getChildren().add(ibrahimTag);

                backButton = new Button();
                backButton.setText("Back");

                try {
                    backButton.setFont(Font.loadFont(new FileInputStream("C:\\Users\\ahmed\\OneDrive\\Documents\\GitHub\\KernalDrive\\src\\Varela-Regular.ttf"), 25));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                backButton.setStyle("-fx-text-fill: #0054b4;");
                backButton.setLayoutX((screenWidth/20)*8.6);
                backButton.setLayoutY((screenHeight/20)*17);

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

    public void addGroup(String groupName, String groupType){
        if(groupType == "Movies"){
            MediaGroup mediaGroup = new MovieGroup(groupName);
            copyPaths(mediaGroup.getPaths());
            //printGroups(mediaGroup.getPaths());
        }

        if(groupType == "TV"){
            MediaGroup mediaGroup = new ShowGroup(groupName);
            copyPaths(mediaGroup.getPaths());
        }
    }

    public void clearScreen(TextField textField, ComboBox comboBox, ListView folderPath){
        textField.clear();
        comboBox.valueProperty().set(null);
        groupPaths.clear();
        folderPath.getItems().clear();
    }

    private void printGroups(ArrayList<File> groupPaths){
        for(File a : groupPaths){
            System.out.println(a);
        }
    }

   public void copyPaths(ArrayList<File> groupPaths){
        for(File filePath : this.groupPaths){
            groupPaths.add(filePath);
        }
    }

    public static void main(String[] args) {
        launch(args);   //sets up program as javafx application
    }
}