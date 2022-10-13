package com.noah.chess.pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
