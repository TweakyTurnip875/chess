package com.noah.chess.pieces.pieceClasses;

import com.noah.chess.pieces.ChessPiece;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class King implements ChessPiece {
	private String token;
	private boolean isValid = false;
	private int indexOne, indexTwo;
	private Image t;
	private ImageView tV;
	private String checkedBy = "";
	
	private boolean isChecked = false;
	private int checkIndOne, checkIndTwo;
	
	public King(int indexOne, int indexTwo, String token) {
		this.indexOne = indexOne;
		this.indexTwo = indexTwo;
		
		this.token = token;
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
	public boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getCheckedBy() {
		return checkedBy;
	}
	public void setCheckedBy(String checkedBy) {
		if(isChecked) {
			this.checkedBy = checkedBy;
		}
	}
	public int getCheckIndOne() {
		return checkIndOne;
	}
	public void setCheckIndOne(int checkIndOne) {
		if(isChecked) {
			this.checkIndOne = checkIndOne;
		}
	}
	public int getCheckIndTwo() {
		return checkIndTwo;
	}
	public void setCheckIndTwo(int checkIndTwo) {
		if(isChecked) {
			this.checkIndTwo = checkIndTwo;
		} 
	}
	public void setCheckIndex(int checkIndOne, int checkIndTwo) {
		if(isChecked) {
			this.checkIndTwo = checkIndTwo;
			this.checkIndOne = checkIndOne;
		} 
	}
}