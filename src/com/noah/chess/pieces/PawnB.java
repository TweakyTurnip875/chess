package com.noah.chess.pieces;

import com.noah.chess.pieces.ChessPiece;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PawnB implements ChessPiece {
	private String token = "images/black/pawn_b.png";
	private int indexOne, indexTwo;
	private Image t;
	private ImageView tV;
	private boolean isValid = true;
	
	public PawnB(int indexOne, int indexTwo) {
		this.indexOne = indexOne;
		this.indexTwo = indexTwo;
		
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
		if(indexOne == this.indexOne) {
			isValid = true;
			this.indexOne = indexOne;
		} else {
			isValid = false;
			System.out.println("invalid");
		}

	}
	public int getIndexTwo() {
		return indexTwo;
	}
	public void setIndexTwo(int indexTwo) {
		if(indexTwo == indexTwo++) {
			isValid = true;
			this.indexTwo = indexTwo;
		} else {
			isValid = false;
			System.out.println("invalid");
		}
	}
	public boolean getIsValid() {
		return isValid;
	}
	
}