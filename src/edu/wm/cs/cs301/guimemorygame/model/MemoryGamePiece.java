package edu.wm.cs.cs301.guimemorygame.model;

public interface MemoryGamePiece {
	public boolean equals(MemoryGamePiece other);
	public void setVisible(boolean v);
	public boolean isVisible();
	public Character getSymbol();
}