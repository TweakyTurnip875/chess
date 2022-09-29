package com.noah.chess.pieces;

import com.noah.chess.pieces.ChessPiece;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PawnW implements ChessPiece {
	private boolean isValid = true;
	private String token = "images/white/pawn_w.png";

	private int indexOne, indexTwo;
	private Image t;
	private ImageView tV;
	
	public PawnW(int indexOne, int indexTwo) {
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
			isValid = false;
			this.indexOne = indexOne;
		} else {
			isValid = false;
			System.out.println(isValid);
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
			System.out.println(isValid);
		}
	}
	public boolean getIsValid() {
		return isValid;
	}
	
	
}
