import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Launch the KernalDriveLauncher class that launches the application
        KernalDriveLauncher k = new KernalDriveLauncher();
        k.startApplication(primaryStage);
    }
}
