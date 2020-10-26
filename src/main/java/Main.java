import com.kernaldrive.gui.MainScreen;

import com.kernaldrive.metadata.MediaManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        MainScreen mainScreen = new MainScreen();
        Scene s = mainScreen.getMainScreenScene();
        primaryStage.setScene(s);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
        mainScreen.setScreen();
    }
}
