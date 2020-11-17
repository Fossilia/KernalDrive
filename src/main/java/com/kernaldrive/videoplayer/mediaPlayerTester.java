package com.kernaldrive.videoplayer;


import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class mediaPlayerTester extends Application {
	private mediaPlayer mp = new mediaPlayer("file path");
	Button playBtn = new Button("call");
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Media Player Tester");
		StackPane layout = new StackPane();
		
		layout.getChildren().add(playBtn);
		playBtn.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				mp.start();
			}
		});
		Scene scene = new Scene(layout, 600, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
	    launch(args);
	}
}
