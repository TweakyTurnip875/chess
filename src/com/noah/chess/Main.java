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
				
				if(j == 1) {
					cell[i][j].setToken(new PawnB(i, j));
				} else if((i == 0 && j == 0) || (i == 7 && j == 0)) {
					cell[i][j].setToken(new PawnB(i, j));
				} else if((i == 1 && j == 0) || (i == 6 && j == 0)) {
					cell[i][j].setToken(new PawnB(i, j));
				} else if((i == 2 && j == 0) || (i == 5 && j == 0)) {
					cell[i][j].setToken(new PawnB(i, j));
				} else if(i == 3 && j == 0) {
					cell[i][j].setToken(new PawnB(i, j));
				} else if(i == 4 && j == 0) {
					cell[i][j].setToken(new PawnB(i, j));
				}
				
				
				if(j == 6) {
					cell[i][j].setToken(new PawnW(i, j));
				} else if((i == 0 && j == 7) || (i == 7 && j == 7)) {
					cell[i][j].setToken(new PawnW(i, j));
				} else if((i == 1 && j == 7) || (i == 6 && j == 7)) {
					cell[i][j].setToken(new PawnW(i, j));
				} else if((i == 2 && j == 7) || (i == 5 && j == 7)) {
					cell[i][j].setToken(new PawnW(i, j));
				} else if(i == 3 && j == 7) {
					cell[i][j].setToken(new PawnW(i, j));
				} else if(i == 4 && j == 7) {
					cell[i][j].setToken(new PawnW(i, j));
				}
			}
		}
		
		Scene s = new Scene(pane, 500, 500);
		
		stage.setTitle("Chess");
		stage.setResizable(false);
		stage.setScene(s);
		stage.show();
	}
	
	

	public ChessPiece history;
	public class Cell<E> extends Pane {
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
				history = (ChessPiece)token;
				
				System.out.println(history);
				
				System.out.println(((ChessPiece) this.token).getIndexOne() + " " + ((ChessPiece) this.token).getIndexTwo());
				
			}
			this.setOnMouseReleased(e -> {
				if(e.getButton().equals(MouseButton.PRIMARY)) {
					if(e.getClickCount() == 2) {
					handlePlacement();
						if(token != null) {
							token = null;
						}
					}
				}
			});
			
		}
		public void handlePlacement() {

			
			if(token == null) {
				history.setIndexOne(getIndexOne());
				history.setIndexTwo(getIndexTwo());
				try {
					setToken(history);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
		public int getIndexOne() {
			return indexOne;
		}
		public int getIndexTwo() {
			return indexTwo;
		}
		public void handleToken() throws FileNotFoundException {

			if(((ChessPiece) this.token).getImageString().equals(pawn_b)) {
				t = new Image("File:" + ((PawnB) this.token).getImageString());
				tV = ((PawnB) this.token).getImageSettings();
				
				getChildren().add(tV);
			}
			if(((ChessPiece) this.token).getImageString().equals(pawn_w)) {
				t = new Image("File:" + ((PawnW) this.token).getImageString());
				tV = ((PawnW) this.token).getImageSettings();
				
				getChildren().add(tV);
			}

		}
		
		public ChessPiece getToken() {
			return token;
		}
		public void setToken(ChessPiece token) throws FileNotFoundException {
			this.token = token;
			
			this.handleToken();
			
		}

	}
	public interface ChessPiece {
		
		public int getIndexOne();
		public void setIndexOne(int indexOne);
		public int getIndexTwo();
		public void setIndexTwo(int indexTwo);
		public Image getImage();
		public void setImage(Image t);
		public String getImageString();
		public void setImageString(String token);
		public ImageView getImageSettings();
	}
	
	public class PawnW implements ChessPiece {
		private String token;
		private int indexOne, indexTwo;
		private Image t;
		private ImageView tV;
		
		public PawnW(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			token = pawn_w;
			t = new Image("File:" + token); 
			
			tV = new ImageView();
			
			tV.setFitHeight(45);
			tV.setFitWidth(40);
			tV.setTranslateX(10);
			tV.setTranslateY(7);
			
			tV.setImage(t);
			
			
		}
		
		public Image getImage() {
			return t;
		}
		public void setImage(Image t) {
			this.t = t;
		}
		public String getImageString() {
			return token;
		}
		public void setImageString(String token) {
			this.token = token;
		}

		public ImageView getImageSettings() {
			return tV;
		}
		public int getIndexOne() {
			return indexOne;
		}
		public void setIndexOne(int indexOne) {
			this.indexOne = indexOne;
		}
		public int getIndexTwo() {
			return indexTwo;
		}
		public void setIndexTwo(int indexTwo) {
			this.indexTwo = indexTwo;
		}
		
	}
	public class PawnB implements ChessPiece {
		private String token;
		private int indexOne, indexTwo;
		private Image t;
		private ImageView tV;
		
		public PawnB(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			token = pawn_b;
			t = new Image("File:" + token); 
			
			tV = new ImageView();
			
			tV.setFitHeight(45);
			tV.setFitWidth(40);
			tV.setTranslateX(10);
			tV.setTranslateY(7);
			
			tV.setImage(t);
			
			
		}
		
		public Image getImage() {
			return t;
		}
		public void setImage(Image t) {
			this.t = t;
		}
		public String getImageString() {
			return token;
		}
		public void setImageString(String token) {
			this.token = token;
		}
		public ImageView getImageSettings() {
			return tV;
		}
		public int getIndexOne() {
			return indexOne;
		}
		public void setIndexOne(int indexOne) {
			this.indexOne = indexOne;
		}
		public int getIndexTwo() {
			return indexTwo;
		}
		public void setIndexTwo(int indexTwo) {
			this.indexTwo = indexTwo;
		}
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
