package com.noah.chess;

import javafx.stage.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.noah.chess.pieces.ChessPiece;
import com.noah.chess.pieces.PawnB;
import com.noah.chess.pieces.PawnW;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.input.*;
import javafx.event.*;

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
				pane.add(cell[i][j] = new Cell(i, j), i, j);
				
				if(i % 2 == 0) {
					if(j % 2 == 0) {
						cell[i][j].setStyle("-fx-background-color: #b5b5b3;");
					} else {
						cell[i][j].setStyle("-fx-background-color: #757573;");
					}
				} else {
					if(j % 2 == 0) {
						cell[i][j].setStyle("-fx-background-color: #757573;");
					} else {
						cell[i][j].setStyle("-fx-background-color: #b5b5b3;");
					}
				}
				
			}
			
		}
		cell[0][1].setToken(new PawnB(0, 1));
		cell[1][1].setToken(new PawnB(1, 1));
		cell[2][1].setToken(new PawnB(2, 1));
		cell[3][1].setToken(new PawnB(3, 1));
		cell[4][1].setToken(new PawnB(4, 1));
		cell[5][1].setToken(new PawnB(5, 1));
		cell[6][1].setToken(new PawnB(6, 1));
		cell[7][1].setToken(new PawnB(7, 1));
		

		cell[0][6].setToken(new PawnW(0, 6));
		cell[1][6].setToken(new PawnW(1, 6));
		cell[2][6].setToken(new PawnW(2, 6));
		cell[3][6].setToken(new PawnW(3, 6));
		cell[4][6].setToken(new PawnW(4, 6));
		cell[5][6].setToken(new PawnW(5, 6));
		cell[6][6].setToken(new PawnW(6, 6));
		cell[7][6].setToken(new PawnW(7, 6));
		
		Scene s = new Scene(pane, 500, 500);
		
		stage.setTitle("Chess");
		stage.setResizable(false);
		stage.setScene(s);
		stage.show();
	}
	
	

	public ChessPiece history;
	public int histIndOne, histIndTwo;
	
	public class Cell extends Pane {
		private ChessPiece token;
		
		private int indexOne;
		private int indexTwo;
		
		private Image t;
		private ImageView tV;
		
		
		public Cell(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			this.setPrefSize(2000, 2000);
			this.setOnMouseClicked(e -> {
				try {
					if(e.getButton().equals(MouseButton.PRIMARY)) {
						handleSelection();
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
		public void handleSelection() throws FileNotFoundException {
			
			if(token != null) {
				history = (ChessPiece)cell[indexOne][indexTwo].getToken();
				
				histIndOne = history.getIndexOne();
				histIndTwo = history.getIndexTwo();
				
				System.out.println(history);
				
				System.out.println(((ChessPiece) this.token).getIndexOne() + " " + ((ChessPiece) this.token).getIndexTwo());
				
			}
			this.setOnMouseReleased(e -> {
				if(e.getButton().equals(MouseButton.PRIMARY)) {

					if(e.getClickCount() == 2) {
						try {
							handlePlacement();
							
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					
				}
			});
			
		}
		public void handlePlacement() throws FileNotFoundException {
			
			cell[histIndOne][histIndTwo].setToken(null);
			
			if(token == null) {
				history.setIndexOne(getIndexOne());
				history.setIndexTwo(getIndexTwo()); 
				
				System.out.println(handleIsValid());
				if(handleIsValid() == true) {
					cell[indexOne][indexTwo].setToken(history);
					cell[indexOne][indexTwo].setToken(cell[indexOne][indexTwo].getToken());
				}
				
				
			}

		}
		public int getIndexOne() {
			return indexOne;
		}
		public int getIndexTwo() {
			return indexTwo;
		}
		public boolean handleIsValid() {
				return history.getIsValid();
		}
		public void handleToken() throws FileNotFoundException {
			
			
		}
		
		public ChessPiece getToken() {
			return token;
		}
		public void setToken(ChessPiece token) throws FileNotFoundException {

			this.token = token;

			if(token instanceof PawnW) {
				t = token.getImage();
				tV = token.getImageSettings();
				
					if(token.getIsValid()) {
						getChildren().add(tV);
					}
			}
		}
	}

	




	public static void main(String[] args) {
		launch(args);
	}

}
