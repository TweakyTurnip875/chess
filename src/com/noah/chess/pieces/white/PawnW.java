package com.noah.chess.pieces.white;

import com.noah.chess.pieces.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PawnW implements ChessPiece {
	private String token;
	private boolean isValid = false;
	private int indexOne, indexTwo;
	private Image t;
	private ImageView tV;
	
	public PawnW(int indexOne, int indexTwo, String token) {
		this.indexOne = indexOne;
		this.indexTwo = indexTwo;
		
		this.token = token;
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
