package edu.wm.cs.cs301.guimemorygame.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.wm.cs.cs301.guimemorygame.controller.ReadSymbolsRunnable;

public class GUIMemoryGameModel{
	
	private MemoryGamePiece[][] gameBoard;
	
	private List<Character> SymbolList, UniqueSymbolList;
	
	MemoryGameResponse[][] memoryGameGrid;
	
	private int columnCount;

	private int maximumRows;
	
	private final Random random;
	
	
	public GUIMemoryGameModel() {
		this.columnCount = 4;
		this.maximumRows = 7;
		this.random = new Random();
	
		createSymbolList();
		setGameBoard();
		
		this.memoryGameGrid = initializeMemoryGameGrid();
		
	}
	
	private void createSymbolList() {		
	     ReadSymbolsRunnable runnable = new ReadSymbolsRunnable(this);		
	     Thread symbolListThread = new Thread(runnable);		
	     symbolListThread.start();		
	     try {			
	          symbolListThread.join();		
	     } catch (InterruptedException e) {			
	          e.printStackTrace();		
	     }	
	}
	
	public void setSymbolList(List<Character> SymbolList) {
		this.SymbolList = SymbolList;
	}
	
	private void setGameBoard() {
		shuffleSymbolList();
		gameBoard = new MemoryGamePiece[getMaximumRows()][getColumnCount()];
		int count = 0;
		for (int row = 0; row < gameBoard.length; row++) {
			for (int col = 0; col < gameBoard[0].length; col++) {
				gameBoard[row][col] = new MemoryGameCharacterPiece(UniqueSymbolList.get(count));
				count++;
			}
		}
	}
	public MemoryGameResponse[][] initializeMemoryGameGrid(){
		this.memoryGameGrid = new MemoryGameResponse[maximumRows][columnCount];
		for (int row = 0; row < memoryGameGrid.length; row++) {
			for (int col = 0;col < memoryGameGrid[0].length; col++) {
				memoryGameGrid[row][col] = null;
			}
		}
		return memoryGameGrid;
	}
	
	public void generateUniqueSymbolList() {
		int numberOfNeededSymbols = (getMaximumRows()*getColumnCount())/2;
        for (int i = 0; i < numberOfNeededSymbols; i++) {
        	Character symbol = getSingleSymbol();
        	if (!UniqueSymbolList.contains(symbol)){
        		UniqueSymbolList.add(symbol);
        		UniqueSymbolList.add(symbol);
        	}
        	else {
        		i--;
        	}
        }
	}
	
	private void shuffleSymbolList() {
		Collections.shuffle(UniqueSymbolList);
	}
	
	private Character getSingleSymbol() {
		return SymbolList.get(getRandomIndex());
	}
	
	private int getRandomIndex() {
		int size = SymbolList.size();
		return random.nextInt(size);
	}
	
	public int getMaximumRows() {
		return maximumRows;
	}
	
	public int getColumnCount() {
		return columnCount;
	}
	
	public int getTotalSymbolsCount() {
		return SymbolList.size();
	}
}