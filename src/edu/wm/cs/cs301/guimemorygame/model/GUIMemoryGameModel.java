package edu.wm.cs.cs301.guimemorygame.model;

import java.util.ArrayList;
//import java.awt.Color;
//import java.util.ArrayList;
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
	
	private int pairsFound, currentTurn;
	
	private int compareTiles;
	
	private int firstComparisonRow = -1, firstComparisonColumn = -1;
	
	private MemoryGamePiece firstComparison;
	
	private MemoryGamePiece secondComparison;
	
	private final Random random;
	
	
	public GUIMemoryGameModel() {
		this.columnCount = 4;
		this.maximumRows = 7;
		this.random = new Random();
		this.pairsFound = 0;
		this.currentTurn = 1;
		this.compareTiles = 0;
		this.firstComparison = null;
		this.UniqueSymbolList = new ArrayList<>();
		
		createSymbolList();
		setGameBoard();
		printGameBoard();
	}
	
	public void initialize() {
		this.pairsFound = 0;
		this.currentTurn = 1;
		this.compareTiles = 0;
		this.pairsFound = 0;
		this.firstComparison = null;
		this.UniqueSymbolList = new ArrayList<>();
		createSymbolList();
		setGameBoard();
		printGameBoard();
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
	
	
	public void generateUniqueSymbolList() {
	    int numberOfNeededSymbols = (getMaximumRows() * getColumnCount()) / 2;
	    for (int i = 0; i < numberOfNeededSymbols; i++) {
	        Character symbol = getSingleSymbol();
	        if (!UniqueSymbolList.contains(symbol)) {
	            UniqueSymbolList.add(symbol);
	            UniqueSymbolList.add(symbol); 
	        } else {
	            i--;
	        }
	    }
	   System.out.println(UniqueSymbolList);
	   
	}
	
	public void setTilesVisible(int row, int col) {
		compareTiles++;
		if (firstComparisonRow == -1) {
			this.firstComparisonRow = row;
		}
		
		if (firstComparisonColumn == -1) {
			this.firstComparisonColumn = col;
		}
		
		gameBoard[row][col].setVisible(true);
		if (firstComparison == null) {
			firstComparison = gameBoard[row][col];
		} else {
			secondComparison = gameBoard[row][col];
		}
	}
	
	public boolean checkMatchFound() {
		this.currentTurn++;
	    if (firstComparison.equals(secondComparison)) {
	    	this.pairsFound++;
	    	return true;
	    }else {
	    	firstComparison.setVisible(false);
		    secondComparison.setVisible(false);
	    	return false;
	    }
	    
	}
	
	public void resetComparisonTiles() {
		this.compareTiles = 0;
		this.firstComparisonColumn = -1;
		this.firstComparisonRow= -1;
		this.firstComparison = null;
    	this.secondComparison =  null;
	}

	public boolean isGameWon() {
		System.out.println(pairsFound);
	    System.out.println((getMaximumRows() * getColumnCount()) / 2);
	    return pairsFound == (getMaximumRows() * getColumnCount()) / 2;
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
	
	public void updataMaxRows(int rows) {
		this.maximumRows = rows;
	}
	
	public void updateColCount(int col) {
		this.columnCount = col;
	}
	public int getTotalSymbolsCount() {
		return SymbolList.size();
	}
	
	public int getCurrentTurn() {
		return currentTurn;
	}
	
	
	public int getCompareTiles() {
		return compareTiles;
	}
	
	public int getFirstComparisonRowNumber(){
		return firstComparisonRow;
	}
	
	public int getFirstComparisonColNumber(){
		return firstComparisonColumn;
	}
	
	public String getValue(int row, int col) {
		MemoryGamePiece piece = gameBoard[row][col];
		return piece.toString();
	}

	public void printGameBoard() {
	    for (int row = 0; row < gameBoard.length; row++) {
	        for (int col = 0; col < gameBoard[row].length; col++) {
	            System.out.print(gameBoard[row][col] + " ");
	        }
	        System.out.println(); // Move to the next line after each row
	    }
	}
}