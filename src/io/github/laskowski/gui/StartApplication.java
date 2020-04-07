package io.github.laskowski.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = new FXMLLoader(getClass().getResource("MainWindow.fxml")).load();
		primaryStage.setTitle("JSON Formatter");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
