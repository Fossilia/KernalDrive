import com.kernaldrive.gui.MainScreen;
import com.kernaldrive.gui.splashscreen.SplashScreen;
import com.kernaldrive.metadata.MediaManager;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class KernalDriveLauncher {

    /**
     * This method starts up the application itself, first calls the MediaManager to retrieve the groups
     from the user, and then initiate the splash screen. A MainScreen object is created because as the
     splash screen is loading the main page, a MainScreen object is required to load the resources as
     it is the object that will control the main page
     **/
    public void startApplication(Stage primaryStage) throws IOException {
        MediaManager manager = new MediaManager();
        SplashScreen splashScreen = new SplashScreen(manager, primaryStage);
        MainScreen mainScreen = new MainScreen();
        Scene splashScreenScene = splashScreen.getScene();
        primaryStage.setScene(splashScreenScene);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
        //This essentially controls the loading of the resources and the progress bar
        splashScreen.setSplashScreen(mainScreen);
    }
}
