package com.noah.chess;

import javafx.stage.*;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class Main extends Application {
	
	private char turn = 'W';
	Cell[][] cell = new Cell[8][8];
	
	GridPane pane;
	
	@Override
	public void start(Stage stage) {
		pane = new GridPane();
		pane.setCursor(Cursor.OPEN_HAND);
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				pane.add(cell[i][j] = new Cell(), i, j);
				
				if(i % 2 == 0) {
					if(j % 2 == 0) {
						cell[i][j].setStyle("-fx-background-color: white;");
					} else {
						cell[i][j].setStyle("-fx-background-color: DarkSeaGreen;");
					}
				} else {
					if(j % 2 == 0) {
						cell[i][j].setStyle("-fx-background-color: DarkSeaGreen;");
					} else {
						cell[i][j].setStyle("-fx-background-color: white;");
					}
				}
			}
		}
		
		Scene s = new Scene(pane, 500, 500);
		
		stage.setTitle("Chess");
		stage.setResizable(false);
		stage.setScene(s);
		stage.show();
	}
	
	public class Cell extends Pane {
		private String token = " ";
		private Text t;
		
		public Cell() {
			this.setPrefSize(2000, 2000);
		}
		
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
