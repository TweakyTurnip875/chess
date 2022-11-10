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

import com.noah.chess.pieces.ChessPiece;
import com.noah.chess.pieces.pieceClasses.*;


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
	
	String[] images = {pawn_w, pawn_b, rook_w, rook_b, knight_w, knight_b, bishop_w, bishop_b, king_w, king_b, queen_w, queen_b};
	String[] imagesWhite = {pawn_w, rook_w, knight_w, bishop_w, king_w, queen_w};
	String[] imagesBlack = {pawn_b, rook_b, knight_b, bishop_b, king_b, queen_b};
	
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
		for(int i = 0; i < 8; i++) {
			cell[i][1].setToken(new Pawn(i, 1, pawn_b));
			cell[i][6].setToken(new Pawn(i, 6, pawn_w));
		}
		
		cell[0][0].setToken(new Rook(0, 0, rook_b));
		cell[7][0].setToken(new Rook(7, 0, rook_b));
		
		cell[1][0].setToken(new Knight(1, 0, knight_b));
		cell[6][0].setToken(new Knight(6, 0, knight_b));
		
		cell[2][0].setToken(new Bishop(2, 0, bishop_b));
		cell[5][0].setToken(new Bishop(5, 0, bishop_b));
		
		cell[3][0].setToken(new King(3, 0, king_b));
		
		cell[4][0].setToken(new Queen(4, 0, queen_b));

		
		cell[0][7].setToken(new Rook(0, 7, rook_w));
		cell[7][7].setToken(new Rook(7, 7, rook_w));
		
		cell[1][7].setToken(new Knight(1, 7, knight_w));
		cell[6][7].setToken(new Knight(6, 7, knight_w));
		
		cell[2][7].setToken(new Bishop(2, 7, bishop_w));
		cell[5][7].setToken(new Bishop(5, 7, bishop_w));
		
		cell[3][7].setToken(new King(3, 7, king_w));
		
		cell[4][7].setToken(new Queen(4, 7, queen_w));
		
		
		
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
	
		private void handleRookCheck(King k) {

				if(indexOne == k.getIndexOne()) {
					for(int i = indexTwo + 1; i < k.getIndexTwo() - 1; i++) {
						if(cell[indexOne][i].getToken() == null) {
							
							k.setIsChecked(true);
							k.setCheckedBy(rook_b);
						
							
						} else if(cell[indexOne][i].getToken() != null)  {
							
							k.setIsChecked(false);
							k.setCheckedBy(null);
							
							break;
						}
	
					}
				}
				
			
				if(indexOne == k.getIndexOne()) {
					for(int i = indexTwo - 1; i > k.getIndexTwo() + 1; i--) {
						if(cell[indexOne][i].getToken() == null) {
						
							k.setIsChecked(true);
							k.setCheckedBy(rook_w);
						//System.out.println(i);
						} else if(cell[indexOne][i].getToken() != null)  {
							k.setIsChecked(false);
							k.setCheckedBy(null);
							
							break;
						}
	
					}
				}
			
		}
		private void handleKnightCheck(King k) {

			int p = indexOne;
			int q = indexTwo;
				
			int X[] = {-1, -2, 1, 2, 1, -2, -1, 2};
			int Y[] = {-2, -1, 2, 1, -2, 1, 2, -1};
				
			for(int i = 0; i < 8; i++) {
					
				int x = p + X[i];
				int y = q + Y[i];
					
				if(x == k.getIndexOne() && y == k.getIndexTwo()) {
					if(cell[histIndOne][histIndTwo].getToken().getImageString().equals(knight_w) && k.getImageString().equals(king_b)) {
						k.setIsChecked(true);
						k.setCheckedBy(knight_w);
					} else if(cell[histIndOne][histIndTwo].getToken().getImageString().equals(knight_b) && k.getImageString().equals(king_w)) {
						k.setIsChecked(true);
						k.setCheckedBy(knight_b);
					}
				}
			}
		}
		
		
		public void handleSelection() throws FileNotFoundException {

			if(token != null) {
				if(turn == 'W') {
					for(int i = 0; i < imagesWhite.length; i++) {
						if(cell[indexOne][indexTwo].getToken().getImageString().equals(imagesWhite[i])) {
							history = cell[indexOne][indexTwo].getToken();
							break;
						}
					}
				} else if(turn == 'B') {
					for(int i = 0; i < imagesBlack.length; i++) {
						if(cell[indexOne][indexTwo].getToken().getImageString().equals(imagesBlack[i])) {
							history = cell[indexOne][indexTwo].getToken();
							break;
						}
					}
				}
				

				
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
			boolean[] knightConds = {(indexOne == histIndOne - 1), 
									  (indexOne == histIndOne + 1), 
									  (indexTwo == histIndTwo - 2),
									  (indexTwo == histIndTwo + 2),
									  (indexOne == histIndOne - 2),
									  (indexOne == histIndOne + 2),
									  (indexTwo == histIndTwo - 1),
									  (indexTwo == histIndTwo + 1)};
			
			King kingWhite = null, kingBlack = null;
			
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(cell[i][j].getToken() != null) {
						if(cell[i][j].getToken().getImageString().equals(king_w)) {
							kingWhite = (King) cell[i][j].getToken();
						} else if(cell[i][j].getToken().getImageString().equals(king_b)) {
							kingBlack = (King) cell[i][j].getToken();
						}
					}
				}
			}
			
			if(history.getImageString().equals(pawn_w) && turn == 'W' && kingWhite.getIsChecked() == false) {

				if(histIndTwo == 6) {
					if(indexOne == histIndOne && (indexTwo == histIndTwo - 2 || indexTwo == histIndTwo - 1)) {
						boolean checkValid = true;
						
						if(cell[indexOne][5].getToken() != null) {
							checkValid = false;
						} else {
							checkValid = true;
						}
						
						history.setIsValid(checkValid); // allows piece to move if it meets the requirements
						
						if(checkValid) {
							cell[histIndOne][histIndTwo].setToken(null); // set the original space to null
						}
						
					}	
				} else {
					if(indexOne == histIndOne && indexTwo == histIndTwo - 1 && cell[indexOne][indexTwo].getToken() == null) {
						history.setIsValid(true);
						cell[histIndOne][histIndTwo].setToken(null);
					}
				}
				//pawn capture
				if((indexOne == histIndOne + 1 || indexOne == histIndOne - 1) && indexTwo == histIndTwo - 1) {
					if(cell[indexOne][indexTwo].getToken() != null) {
						history.setIsValid(true);
						cell[histIndOne][histIndTwo].setToken(null);
					}
				}
			} else if((history.getImageString().equals(rook_w) && turn == 'W' && kingWhite.getIsChecked() == false) || (history.getImageString().equals(rook_b) && turn == 'B' && kingBlack.getIsChecked() == false)) {
				if(indexOne == histIndOne || indexTwo == histIndTwo) {
					boolean checkValid = true;
					boolean vertical = false;
					
					
					for(int i = histIndTwo + 1; i < indexTwo; i++) {
						
							
						if(cell[histIndOne][i].getToken() != null) {
							checkValid = false;
	
						}
						vertical = true;
					} 
						
					for(int i = histIndTwo - 1; i > indexTwo; i--) {
							
						if(cell[histIndOne][i].getToken() != null) {
							checkValid = false;
	
						}
						vertical = true;
					} 
						
					for(int i = histIndOne + 1; i < indexOne; i++) {
						
						if(cell[i][histIndTwo].getToken() != null) {
							checkValid = false;

						}
						vertical = false;
					} 
					for(int i = histIndOne - 1; i > indexOne; i--) {
						
						if(cell[i][histIndTwo].getToken() != null) {
							checkValid = false;

						}
						vertical = false;
					} 
					
					
					
					history.setIsValid(checkValid);
					if(history.getIsValid()) {
						
						cell[histIndOne][histIndTwo].setToken(null);
					}
					
					handleRookCheck(kingWhite);
					handleRookCheck(kingBlack);
					
				}
			} else if((history.getImageString().equals(knight_w) && turn == 'W') || (history.getImageString().equals(knight_b) && turn == 'B')) {
				if(((knightConds[0] || knightConds[1]) && (knightConds[2] || knightConds[3])) || ((knightConds[4] || knightConds[5]) && (knightConds[6] || knightConds[7]))) {
					if((cell[indexOne][indexTwo].getToken() != null) && (cell[indexOne][indexTwo].getToken().getImageString().equals(kingWhite.getCheckedBy()) || cell[indexOne][indexTwo].getToken().getImageString().equals(kingBlack.getCheckedBy()))) {
						
						handleKnightCheck(kingBlack);
						handleKnightCheck(kingWhite);
						
						history.setIsValid(true);
						cell[histIndOne][histIndTwo].setToken(null);
						
					} else if(!kingWhite.getIsChecked() && !kingBlack.getIsChecked()) {
						handleKnightCheck(kingBlack);
						handleKnightCheck(kingWhite);
						
						history.setIsValid(true);
						cell[histIndOne][histIndTwo].setToken(null);
					}
				}
			} else if((history.getImageString().equals(bishop_w) && turn == 'W' && kingWhite.getIsChecked() == false) || (history.getImageString().equals(bishop_b) && turn == 'B' && kingBlack.getIsChecked() == false)) {
				

				if((indexOne > histIndOne || indexOne < histIndOne) && (indexTwo > histIndTwo || indexTwo < histIndTwo)) {
					if(Math.abs(indexOne - histIndOne) == Math.abs(indexTwo - histIndTwo)) {
						int rOffset, cOffset;
						
						
						boolean checkValid = true;
						
						if(histIndOne < indexOne) {
							cOffset = 1;
						} else {
							cOffset = -1;
						}
						
						if(histIndTwo < indexTwo) {
							rOffset = 1;
						} else {
							rOffset = -1;
						}
						
						int y = histIndOne + cOffset;
						for(int x = histIndTwo + rOffset; x != indexTwo; x += rOffset) {
							System.out.println(y + " " + x);
							
							if(cell[y][x].getToken() != null) {
								checkValid = false;
								break;
							}
							y += cOffset;
						}

						
						if(checkValid) {
							history.setIsValid(true);
							cell[histIndOne][histIndTwo].setToken(null);
						}
						
						
					}
				}
			} else if((history.getImageString().equals(king_w) && turn == 'W') || (history.getImageString().equals(king_b) && turn == 'B')) {
				if(((indexOne == histIndOne + 1 || indexOne == histIndOne - 1) && indexTwo <= histIndTwo + 1) || ((indexTwo == histIndTwo + 1 || indexTwo == histIndTwo - 1) && indexOne <= histIndOne + 1)) {
					
					if(kingBlack.getIsChecked() || kingWhite.getIsChecked()) {
						if(kingBlack.getCheckedBy().equals(rook_w) || kingWhite.getCheckedBy().equals(rook_b)) {
							if((indexOne == histIndOne + 1 || indexOne == histIndOne - 1)) {
								history.setIsValid(true);
								cell[histIndOne][histIndTwo].setToken(null);
								
								if(kingBlack.getIsChecked()) {
									kingBlack.setIsChecked(false);
									kingBlack.setCheckedBy(null);
								} else {
									kingWhite.setIsChecked(false);
									kingWhite.setCheckedBy(null);
								}
							}

						} else if(kingBlack.getCheckedBy().equals(knight_w) || kingWhite.getCheckedBy().equals(knight_b)) {
							if(((indexOne == histIndOne + 1 || indexOne == histIndOne - 1) && indexTwo <= histIndTwo + 1) || ((indexTwo == histIndTwo + 1 || indexTwo == histIndTwo - 1) && indexOne <= histIndOne + 1)) {
								history.setIsValid(true);
								cell[histIndOne][histIndTwo].setToken(null);
								
								if(kingBlack.getIsChecked()) {
									kingBlack.setIsChecked(false);
									kingBlack.setCheckedBy(null);
								} else {
									kingWhite.setIsChecked(false);
									kingWhite.setCheckedBy(null);
								}
							}
						} else if(kingBlack.getCheckedBy().equals(pawn_w)) {
							if(((indexOne == histIndOne + 1 || indexOne == histIndOne - 1) && indexTwo <= histIndTwo + 1) || ((indexTwo == histIndTwo + 1 || indexTwo == histIndTwo - 1) && indexOne <= histIndOne + 1)) {
								kingBlack.setIsChecked(false);
								kingBlack.setCheckedBy(null);
							}
						}

					} else {
						history.setIsValid(true);
						cell[histIndOne][histIndTwo].setToken(null);
					}
				}
			} else if((history.getImageString().equals(queen_w) && turn == 'W' && kingWhite.getIsChecked() == false) || (history.getImageString().equals(queen_b) && turn == 'B' && kingBlack.getIsChecked() == false)) {
				if((indexOne > histIndOne || indexOne < histIndOne) && (indexTwo > histIndTwo || indexTwo < histIndTwo)) {
					if(Math.abs(indexOne - histIndOne) == Math.abs(indexTwo - histIndTwo)) {
						int rOffset, cOffset;
						
						
						boolean checkValid = true;
						
						if(histIndOne < indexOne) {
							cOffset = 1;
						} else {
							cOffset = -1;
						}
						
						if(histIndTwo < indexTwo) {
							rOffset = 1;
						} else {
							rOffset = -1;
						}
						
						int y = histIndOne + cOffset;
						for(int x = histIndTwo + rOffset; x != indexTwo; x += rOffset) {
							System.out.println(y + " " + x);
							
							if(cell[y][x].getToken() != null) {
								checkValid = false;
								break;
							}
							y += cOffset;
						}

						
						if(checkValid) {
							history.setIsValid(true);
							cell[histIndOne][histIndTwo].setToken(null);
						}
					}
				} else if(indexOne == histIndOne || indexTwo == histIndTwo) {
					boolean checkValid = true;
					

					for(int i = histIndTwo + 1; i < indexTwo; i++) {

							
						if(cell[histIndOne][i].getToken() != null) {
							checkValid = false;
	
						}
					} 
						
					for(int i = histIndTwo - 1; i > indexTwo; i--) {
							
						if(cell[histIndOne][i].getToken() != null) {
							checkValid = false;
	
						}
					} 
						
					for(int i = histIndOne + 1; i < indexOne; i++) {
						
						if(cell[i][histIndTwo].getToken() != null) {
							checkValid = false;

						}
					} 
					for(int i = histIndOne - 1; i > indexOne; i--) {
						
						if(cell[i][histIndTwo].getToken() != null) {
							checkValid = false;

						}
					} 
					if(checkValid) {
						history.setIsValid(true);
						cell[histIndOne][histIndTwo].setToken(null);
					}
				}
				
			}
			if(history.getImageString().equals(pawn_b) && turn == 'B' && kingBlack.getIsChecked() == false) {
				if(histIndTwo == 1) {
					if(indexOne == histIndOne && (indexTwo == histIndTwo + 2 || indexTwo == histIndTwo + 1)) {
						boolean checkValid = true;

						
						if(cell[indexOne][2].getToken() != null) {
							checkValid = false;
						} else {
							checkValid = true;
						}
						
						history.setIsValid(checkValid);
						
						if(checkValid) {
							cell[histIndOne][histIndTwo].setToken(null);
						}
						
					}
				} else {
					if(indexOne == histIndOne && indexTwo == histIndTwo + 1 && cell[indexOne][indexTwo].getToken() == null) {
						history.setIsValid(true); 
						cell[histIndOne][histIndTwo].setToken(null); 
					}
				}
				if((indexOne == histIndOne + 1 || indexOne == histIndOne - 1) && indexTwo == histIndTwo + 1) {
					if(cell[indexOne][indexTwo].getToken() != null) {
						history.setIsValid(true); 
						cell[histIndOne][histIndTwo].setToken(null); 
					}
				}
			} 
			
			if(history.getIsValid() == true) {
				// sets the piece to move indexes to the current cell location
				history.setIndexOne(getIndexOne());
				history.setIndexTwo(getIndexTwo());
				
				// sets the space to move tos token with the piece to move
				cell[indexOne][indexTwo].setToken(history);
				
				if(turn == 'W') {
					
					for(int i = 0; i < imagesBlack.length; i++) {
						if(cell[indexOne][indexTwo].getToken().getImageString().equals(imagesBlack[i])) {
							cell[indexOne][indexTwo].setToken(history);
							
							imagesBlack[i] = null;
							
						}
					}

					getChildren().clear();
					handleToken();
					
				} else if(turn == 'B') {
					for(int i = 0; i < imagesWhite.length; i++) {
						if(cell[indexOne][indexTwo].getToken().getImageString().equals(imagesWhite[i])) {
							cell[indexOne][indexTwo].setToken(history);
							
							imagesWhite[i] = null;
						}
					}
					
					getChildren().clear();
					handleToken();
				}
				
				char newTurn = turn == 'W' ? 'B' : turn == 'B' ? 'W' : 'B';
				turn = newTurn;
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
				
				for(int i = 0; i < imagesWhite.length; i++) {
					if(token.getImageString().equals(imagesWhite[i])) {
						t = new Image("File:" + token.getImageString().equals(imagesWhite[i]));
						tV = token.getImageSettings();
						
						getChildren().add(tV);
						
					}
				}
				for(int i = 0; i < imagesBlack.length; i++) {
					if(token.getImageString().equals(imagesBlack[i])) {
						t = new Image("File:" + token.getImageString().equals(imagesBlack[i]));
						tV = token.getImageSettings();
						
						getChildren().add(tV);
					}
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

	
	public static void main(String[] args) {
		launch(args);
	}

}
