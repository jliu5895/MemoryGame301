package edu.wm.cs.cs301.guimemorygame.model;

public class MemoryGameCharacterPiece implements MemoryGamePiece {
	private final Character symbol;
	private boolean visibility;
	
	public MemoryGameCharacterPiece(Character s) 
	{
		this.symbol = s;
		this.visibility = false; 
	}

	public Character getSymbol() 
	{	
		Character s = symbol; 
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
		return this.symbol.equals(other.getSymbol()) && this.visibility == other.isVisible();
	}
	
}