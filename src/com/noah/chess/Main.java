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
		for(int i = 0; i < 8; i++) {
			cell[i][1].setToken(new PawnB(i, 1));
			cell[i][6].setToken(new PawnW(i, 6));
		}
		
		cell[0][0].setToken(new RookB(0, 0));
		cell[7][0].setToken(new RookB(7, 0));
		
		cell[1][0].setToken(new KnightB(1, 0));
		cell[6][0].setToken(new KnightB(6, 0));
		
		cell[2][0].setToken(new BishopB(2, 0));
		cell[5][0].setToken(new BishopB(5, 0));
		
		cell[3][0].setToken(new KingB(3, 0));
		
		cell[4][0].setToken(new QueenB(4, 0));

		
		cell[0][7].setToken(new RookW(0, 7));
		cell[7][7].setToken(new RookW(7, 7));
		
		cell[1][7].setToken(new KnightW(1, 7));
		cell[6][7].setToken(new KnightW(6, 7));
		
		cell[2][7].setToken(new BishopW(2, 7));
		cell[5][7].setToken(new BishopW(5, 7));
		
		cell[3][7].setToken(new KingW(3, 7));
		
		cell[4][7].setToken(new QueenW(4, 7));
		
		
		
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
			boolean[] knightConds = {(indexOne == histIndOne - 1), 
									  (indexOne == histIndOne + 1), 
									  (indexTwo == histIndTwo - 2),
									  (indexTwo == histIndTwo + 2),
									  (indexOne == histIndOne - 2),
									  (indexOne == histIndOne + 2),
									  (indexTwo == histIndTwo - 1),
									  (indexTwo == histIndTwo + 1)};
			

			
			//if the piece to move is in the same column and is equal to its current space - 1
			if(history.getImageString().equals(pawn_w)) {
				if(histIndTwo == 6) {
					if(indexOne == histIndOne && indexTwo == histIndTwo - 2 || indexTwo == histIndTwo - 1) {
						history.setIsValid(true); // allows piece to move if it meets the requirements
						cell[histIndOne][histIndTwo].setToken(null); // set the original space to null
					}
				} else {
					if(indexOne == histIndOne && indexTwo == histIndTwo - 1) {
						history.setIsValid(true); // allows piece to move if it meets the requirements
						cell[histIndOne][histIndTwo].setToken(null); // set the original space to null
					}
				}
			} else if(history.getImageString().equals(rook_w) || history.getImageString().equals(rook_b)) {
				if(indexOne == histIndOne || indexTwo == histIndTwo) {
					boolean valid = true;
					
				
					for(int i = histIndTwo + 1; i < indexTwo; i++) {
						System.out.println(cell[0][i].getToken() == null);
						
						if(cell[0][i].getToken() != null) {
							valid = false;

						}
					} 
					history.setIsValid(valid);
					if(history.getIsValid()) {
						cell[histIndOne][histIndTwo].setToken(null);
					}
				}
			} else if(history.getImageString().equals(knight_w) || history.getImageString().equals(knight_b)) {
				if(((knightConds[0] ||knightConds[1]) && (knightConds[2] || knightConds[3])) || ((knightConds[4] || knightConds[5]) && (knightConds[6] || knightConds[7]))) {
					history.setIsValid(true);
					cell[histIndOne][histIndTwo].setToken(null);
				}
			} else if(history.getImageString().equals(bishop_w) || history.getImageString().equals(bishop_b)) {
				if((indexOne > histIndOne || indexOne < histIndOne) && (indexTwo > histIndTwo || indexTwo < histIndTwo)) {
					if(Math.abs(indexOne - histIndOne) == Math.abs(indexTwo - histIndTwo)) {
						cell[histIndOne][histIndTwo].setToken(null);
						history.setIsValid(true);

					}
				}
			} else if(history.getImageString().equals(king_w) || history.getImageString().equals(king_b)) {
				if(((indexOne == histIndOne + 1 || indexOne == histIndOne - 1) && indexTwo <= histIndTwo + 1) || ((indexTwo == histIndTwo + 1 || indexTwo == histIndTwo - 1) && indexOne <= histIndOne + 1)) {
					history.setIsValid(true);
					cell[histIndOne][histIndTwo].setToken(null);
				}
			} else if(history.getImageString().equals(queen_w) || history.getImageString().equals(queen_b)) {
				if((indexOne > histIndOne || indexOne < histIndOne) && (indexTwo > histIndTwo || indexTwo < histIndTwo)) {
					if(Math.abs(indexOne - histIndOne) == Math.abs(indexTwo - histIndTwo)) {
						history.setIsValid(true);
						cell[histIndOne][histIndTwo].setToken(null);
					}
				} else if(indexOne == histIndOne || indexTwo == histIndTwo) {
					history.setIsValid(true);
					cell[histIndOne][histIndTwo].setToken(null);
				}
				
			}
			if(history.getImageString().equals(pawn_b)) {
				if(histIndTwo == 1) {
					if(indexOne == histIndOne && indexTwo == histIndTwo + 2 || indexTwo == histIndTwo + 1) {
						history.setIsValid(true); 
						cell[histIndOne][histIndTwo].setToken(null);
					}
				} else {
					if(indexOne == histIndOne && indexTwo == histIndTwo + 1) {
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
			String[] images = {pawn_w, pawn_b, rook_w, rook_b, knight_w, knight_b, bishop_w, bishop_b, king_w, king_b, queen_w, queen_b};
			
			if(token != null) {
				for(int i = 0; i < images.length; i++) {
					if(token.getImageString().equals(images[i])) {
						t = new Image("File:" + token.getImageString().equals(images[i]));
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
	public class RookW implements ChessPiece {
		private String token;
		private boolean isValid = false;
		private int indexOne, indexTwo;
		private Image t;
		private ImageView tV;
		
		public RookW(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			token = rook_w;
			t = new Image("File:" + token); 
			
			tV = new ImageView();
			
			tV.setFitHeight(45);
			tV.setFitWidth(35);
			tV.setTranslateX(13);
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
	public class RookB implements ChessPiece {
		private String token;
		private boolean isValid = false;
		private int indexOne, indexTwo;
		private Image t;
		private ImageView tV;
		
		public RookB(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			token = rook_b;
			t = new Image("File:" + token); 
			
			tV = new ImageView();
			
			tV.setFitHeight(45);
			tV.setFitWidth(35);
			tV.setTranslateX(13);
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
	public class KnightW implements ChessPiece {
		private String token;
		private boolean isValid = false;
		private int indexOne, indexTwo;
		private Image t;
		private ImageView tV;
		
		public KnightW(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			token = knight_w;
			t = new Image("File:" + token); 
			
			tV = new ImageView();
			
			tV.setFitHeight(45);
			tV.setFitWidth(35);
			tV.setTranslateX(13);
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
	public class KnightB implements ChessPiece {
		private String token;
		private boolean isValid = false;
		private int indexOne, indexTwo;
		private Image t;
		private ImageView tV;
		
		public KnightB(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			token = knight_b;
			t = new Image("File:" + token); 
			
			tV = new ImageView();
			
			tV.setFitHeight(45);
			tV.setFitWidth(35);
			tV.setTranslateX(13);
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
	public class BishopW implements ChessPiece {
		private String token;
		private boolean isValid = false;
		private int indexOne, indexTwo;
		private Image t;
		private ImageView tV;
		
		public BishopW(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			token = bishop_w;
			t = new Image("File:" + token); 
			
			tV = new ImageView();
			
			tV.setFitHeight(45);
			tV.setFitWidth(35);
			tV.setTranslateX(13);
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
	public class BishopB implements ChessPiece {
		private String token;
		private boolean isValid = false;
		private int indexOne, indexTwo;
		private Image t;
		private ImageView tV;
		
		public BishopB(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			token = bishop_b;
			t = new Image("File:" + token); 
			
			tV = new ImageView();
			
			tV.setFitHeight(45);
			tV.setFitWidth(35);
			tV.setTranslateX(13);
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
	public class KingW implements ChessPiece {
		private String token;
		private boolean isValid = false;
		private int indexOne, indexTwo;
		private Image t;
		private ImageView tV;
		
		public KingW(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			token = king_w;
			t = new Image("File:" + token); 
			
			tV = new ImageView();
			
			tV.setFitHeight(50);
			tV.setFitWidth(45);
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
	public class KingB implements ChessPiece {
		private String token;
		private boolean isValid = false;
		private int indexOne, indexTwo;
		private Image t;
		private ImageView tV;
		
		public KingB(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			token = king_b;
			t = new Image("File:" + token); 
			
			tV = new ImageView();
			
			tV.setFitHeight(50);
			tV.setFitWidth(45);
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
	public class QueenB implements ChessPiece {
		private String token;
		private boolean isValid = false;
		private int indexOne, indexTwo;
		private Image t;
		private ImageView tV;
		
		public QueenB(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			token = queen_b;
			t = new Image("File:" + token); 
			
			tV = new ImageView();
			
			tV.setFitHeight(50);
			tV.setFitWidth(45);
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
	public class QueenW implements ChessPiece {
		private String token;
		private boolean isValid = false;
		private int indexOne, indexTwo;
		private Image t;
		private ImageView tV;
		
		public QueenW(int indexOne, int indexTwo) {
			this.indexOne = indexOne;
			this.indexTwo = indexTwo;
			
			token = queen_w;
			t = new Image("File:" + token); 
			
			tV = new ImageView();
			
			tV.setFitHeight(50);
			tV.setFitWidth(45);
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
