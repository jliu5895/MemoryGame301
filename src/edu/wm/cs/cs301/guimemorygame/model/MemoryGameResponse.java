package edu.wm.cs.cs301.guimemorygame.model;

import java.awt.Color;

public class MemoryGameResponse{
	
	private final Character c;
	private boolean visibility;
	
	public MemoryGameResponse(Character c, Color backgroundColor, Color foregroundcolor) {
		this.c = c;
		this.visibility = false;
	}
	
	public Character getSymbol() 
	{	
		Character s = c; 
		return s;
	}
	
	public void setVisible(boolean flipped) 
	{
		this.visibility = flipped;
	}
	
	public boolean isVisible() 
	{
		return visibility;
	}
	
	public boolean equals(MemoryGamePiece other) 
	{
		return this.c.equals(other.getSymbol()) && this.visibility == other.isVisible();
	}
	
	
	
}