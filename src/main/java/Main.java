import com.kernaldrive.gui.MainScreen;

import com.kernaldrive.metadata.MediaManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        KernalDriveLauncher k = new KernalDriveLauncher();
        k.startApplication(primaryStage);
        /*Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight() - 30;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:///C:\\Users\\HP\\IdeaProjects\\untitled\\src\\main\\java\\com\\kernaldrive\\gui\\SplashScreen.fxml"));
        Parent root = loader.load();
        Scene splashScreen = new Scene(root, screenWidth, screenHeight - 20);
        primaryStage.setScene(splashScreen);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
        MainScreen mainScreen = new MainScreen();
        Scene s = mainScreen.getMainScreenScene();
        primaryStage.setScene(s);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
        mainScreen.setScreen();*/

        //File f = new File("filecss.css");
        //s.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
    }
}
