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
			
			//if the piece to move is in the same column and is equal to its current space - 1
			if(((ChessPiece) history).getImageString().equals(pawn_w)) {
				if(indexOne == histIndOne && indexTwo == histIndTwo - 1) {
					history.setIsValid(true); // allows piece to move if it meets the requirements
					cell[histIndOne][histIndTwo].setToken(null); // set the original space to null
				}
			}

			if(history.getIsValid() == true) {
				// sets the piece to move indexes to the current cell location
				history.setIndexOne(getIndexOne());
				history.setIndexTwo(getIndexTwo());
				
				// sets the space to move tos token with the piece to move
				cell[indexOne][indexTwo].setToken(history);
			}
			// makes sure the piece can't move into any invalid spaces after isValid is set to true
			history.setIsValid(false);
		}
		public int getIndexOne() {
			return indexOne;
		}
		public int getIndexTwo() {
			return indexTwo;
		}
		public void handleToken() throws FileNotFoundException {
			if(token != null) {
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
		public boolean getIsValid();
		public void setIsValid(boolean isValid);
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
		private boolean isValid = false;
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
		public boolean getIsValid() {
			return isValid;
		}
		public void setIsValid(boolean isValid) {
			this.isValid = isValid;
		}
		
	}
	public class PawnB implements ChessPiece {
		private String token;
		private boolean isValid = false;
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
		public boolean getIsValid() {
			return isValid;
		}
		public void setIsValid(boolean isValid) {
			this.isValid = isValid;
		}
	}
	public static void main(String[] args) {
		launch(args);
	}

}
