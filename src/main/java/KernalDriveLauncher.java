import com.kernaldrive.gui.MainScreen;
import com.kernaldrive.gui.splashscreen.SplashScreen;
import com.kernaldrive.metadata.MediaManager;
import com.kernaldrive.metadata.MovieGroup;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class KernalDriveLauncher {

    //This method starts up the application itself, first calls the MediaManager to retrieve the groups
    //from the users, and then initiate the splash screen
    public void startApplication(Stage primaryStage) throws IOException {
        MediaManager manager = new MediaManager();
        SplashScreen splashScreen = new SplashScreen(manager, primaryStage);
        MainScreen mainScreen = new MainScreen();
        Scene splashScreenScene = splashScreen.getScene();
        primaryStage.setScene(splashScreenScene);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
        splashScreen.setSplashScreen(mainScreen);
    }
}
