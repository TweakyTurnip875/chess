package com.noah.chess;

import javafx.stage.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		Pane p = new Pane();
		
		Scene s = new Scene(p, 700, 500);
		
		stage.setTitle("Chess");
		stage.setResizable(false);
		stage.setScene(s);
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}
