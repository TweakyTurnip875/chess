package com.noah.chess;

import javafx.stage.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class Main extends Application {
	
	String pawn_w = "images/white/pawn_w.png",
		   rook_w = "images/white/rook_w.png",
		   knight_w = "images/white/knight_w.png",
		   bishop_w = "images/white/bishop_w.png",
		   queen_w = "images/white/queen_w.png",
		   king_w = "images/white/king_w.png";
	
	String pawn_b = "images/black/pawn_b.png",
		   rook_b = "images/black/rook_b.png",
		   knight_b = "images/black/knight_b.png",
		   bishop_b = "images/black/bishop_b.png",
		   queen_b = "images/black/queen_b.png",
		   king_b = "images/black/king_b.png";
	
	private char turn = 'W';
	Cell[][] cell = new Cell[8][8];
	
	GridPane pane;
	
	@Override
	public void start(Stage stage) throws FileNotFoundException {
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
				
				if(j == 1) {
					cell[i][j].setToken(pawn_b);
				} else if((i == 0 && j == 0) || (i == 7 && j == 0)) {
					cell[i][j].setToken(rook_b);
				} else if((i == 1 && j == 0) || (i == 6 && j == 0)) {
					cell[i][j].setToken(knight_b);
				} else if((i == 2 && j == 0) || (i == 5 && j == 0)) {
					cell[i][j].setToken(bishop_b);
				} else if(i == 3 && j == 0) {
					cell[i][j].setToken(queen_b);
				} else if(i == 4 && j == 0) {
					cell[i][j].setToken(king_b);
				}
				
				
				if(j == 6) {
					cell[i][j].setToken(pawn_w);
				} else if((i == 0 && j == 7) || (i == 7 && j == 7)) {
					cell[i][j].setToken(rook_w);
				} else if((i == 1 && j == 7) || (i == 6 && j == 7)) {
					cell[i][j].setToken(knight_w);
				} else if((i == 2 && j == 7) || (i == 5 && j == 7)) {
					cell[i][j].setToken(bishop_w);
				} else if(i == 3 && j == 7) {
					cell[i][j].setToken(queen_w);
				} else if(i == 4 && j == 7) {
					cell[i][j].setToken(king_w);
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
		private Image t;
		private ImageView tV;
		
		public Cell() {
			this.setPrefSize(2000, 2000);
		}
		
		public void handleToken(String token) throws FileNotFoundException {
			tV = new ImageView();
			tV.setFitHeight(45);
			tV.setFitWidth(25);
			tV.setTranslateX(18);
			tV.setTranslateY(7);
			
			if(this.token.equals(pawn_w)) {
				t = new Image("File:" + pawn_w);
				
				tV.setFitHeight(45);
				tV.setFitWidth(40);
				tV.setTranslateX(10);
				tV.setTranslateY(7);
				
				tV.setImage(t);
				
				getChildren().add(tV);
			} else if(this.token.equals(pawn_b)) {
				t = new Image("File:" + pawn_b);
				
				tV.setFitHeight(45);
				tV.setFitWidth(40);
				tV.setTranslateX(10);
				tV.setTranslateY(7);
				
				tV.setImage(t);
				
				getChildren().add(tV);
			}
			
			if(this.token.equals(rook_w)) {
				t = new Image("File:" + rook_w);
				
				tV.setImage(t);
				
				getChildren().add(tV);
			} else if(this.token.equals(rook_b)) {
				t = new Image("File:" + rook_b);
				
				tV.setImage(t);
				
				getChildren().add(tV);
			}
			
			if(this.token.equals(knight_w)) {
				t = new Image("File:" + knight_w);
				
				tV.setImage(t);
				
				getChildren().add(tV);
			} else if(this.token.equals(knight_b)) {
				t = new Image("File:" + knight_b);
				
				tV.setImage(t);
				
				getChildren().add(tV);
			}
			
			if(this.token.equals(bishop_w)) {
				t = new Image("File:" + bishop_w);
				
				tV.setImage(t);
				
				getChildren().add(tV);
			}
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) throws FileNotFoundException {
			this.token = token;
			
			handleToken(token);
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
