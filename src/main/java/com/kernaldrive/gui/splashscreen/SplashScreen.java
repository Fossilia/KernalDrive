package com.kernaldrive.gui.splashscreen;

import com.kernaldrive.filescanning.MovieScanner;
import com.kernaldrive.gui.MainScreen;
import com.kernaldrive.metadata.MediaManager;
import com.kernaldrive.metadata.MovieGroup;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SplashScreen{
    private double screenWidth;
    private double screenHeight;
    private MediaManager manager;
    private ArrayList<MovieGroup> movieGroupList;
    private Stage primaryStage;
    private FXMLLoader loader;
    private MovieScanner movieScanner;

    public SplashScreen(ArrayList<MovieGroup> MovieGroupList, Stage primaryStage) throws MalformedURLException {

        //this.manager = manager;
        this.movieGroupList = MovieGroupList;
        this.primaryStage = primaryStage;
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        screenWidth = screenBounds.getWidth();
        screenHeight = screenBounds.getHeight();
        loader = new FXMLLoader();
        //loader.setLocation(new URL("file:///C:\\Users\\Faisal\\Documents\\GitHub\\KernalDrive-v2\\src\\main\\java\\com\\kernaldrive\\gui\\splashscreen\\SplashScreen.fxml"));
        loader.setLocation( new URL(new URL("file:"), "./src/main/java/com/kernaldrive/gui/splashscreen/SplashScreen.fxml"));
    }

    /**Sets up the splash screen by invoking the controller for this class. This controller
     * takes the base splash screen and first adjusts it to fit the screen size through the
     * setSplashScreen() method in the controller. After setting up the screen, loadResources()
     * is called that loads the resources of the main page and controls the progress bar
     **/
    public void setSplashScreen(MainScreen mainScreen) throws IOException {
        SplashScreenController splashScreenController = loader.getController();
        splashScreenController.setSplashScreen(screenWidth, screenHeight - 30, movieGroupList.get(0));
        loadResources(splashScreenController, mainScreen);
    }

    /**
     * This function controls the loading of the resources on the main page. We first need to load
     * the scene from the mainScreen object because in order to add the content to the main page,
     * the controller for the mainScreen needs to be invoked which can only be invoked once the
     * scene is invoked. The advanceButton button is the button that appears after the progress bar
     * is done loading. It then creates a thread that loads the main screen's resources in the background
     * and updates the progress bar as the resources are loading
     **/
    private void loadResources(SplashScreenController splashScreenController, MainScreen mainScreen) throws IOException {
        Scene mainScreenScene = mainScreen.getMainScreenScene();
        Button advanceButton = new Button("Advance to Home");
        advanceButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    mainScreen.displayPage(primaryStage, mainScreenScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        new Thread(){
            public void run(){
                //ArrayList<MovieGroup> movieGroups = manager.getMovieGroups();

                //The overall increment after all the groups are scanned is 40% of the bar, so we divide it by
                //the number of groups to get the increment of the progress bar after each group is scanned. The
                //similar logic is employed with the time interval that each increment takes
                double eachGroupIncrement = 0.4 / movieGroupList.size();
                double eachGroupIncrementTime = 3000 / movieGroupList.size();
                movieScanner = new MovieScanner();
                for (int i = 0; i < movieGroupList.size(); i++){
                    movieScanner.scan(movieGroupList.get(i));
                    if (i + 1 == movieGroupList.size()) splashScreenController.setProgressBar(movieGroupList.get(i).getName(), movieGroupList.get(i).getMovies().size(), eachGroupIncrement*(i + 1), eachGroupIncrementTime, "Building Side Bar...", advanceButton);
                    else splashScreenController.setProgressBar(movieGroupList.get(i).getName(), movieGroupList.get(i).getMovies().size(), eachGroupIncrement*(i + 1), eachGroupIncrementTime, "Scanning " + movieGroupList.get(i + 1).getName() + " Group...", advanceButton);
                    try {
                        Thread.sleep((long) eachGroupIncrementTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    long startTime = System.nanoTime();
                    mainScreen.setSideBar(movieGroupList);
                    splashScreenController.setProgressBar("", -1, 0.6, 1000, "Building Content Page...", advanceButton);
                    mainScreen.setContentPage();
                    splashScreenController.setProgressBar("", -1, 1.0, 3000, "Completed!", advanceButton);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
                    System.out.println("TOTAL GUI SCAN TIME WAS: "+((float)duration/1000000000)+" SECONDS");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public Scene getScene() throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root, screenWidth, screenHeight - 20);
        return scene;
    }


}
