package edu.wm.cs.cs301.guimemorygame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import edu.wm.cs.cs301.guimemorygame.model.GUIMemoryGameModel;
import edu.wm.cs.cs301.guimemorygame.view.LeaderBoardDialog;
import edu.wm.cs.cs301.guimemorygame.view.MemoryGameFrame;
import edu.wm.cs.cs301.guimemorygame.view.WrongSelectionDialog;

public class SelectionAction implements ActionListener {

    private final MemoryGameFrame view;
    private final GUIMemoryGameModel model;
    
    public SelectionAction(MemoryGameFrame view, GUIMemoryGameModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String symbol = clickedButton.getText();
        if ("?".equals(symbol)) {
        	int clickedRow = getButtonRow(clickedButton);
            int clickedColumn = getButtonColumn(clickedButton);

            clickedButton.setText(model.getValue(clickedRow, clickedColumn));
            model.setTilesVisible(clickedRow, clickedColumn);
             
            if (model.getCompareTiles() == 2) {
            	if (model.checkMatchFound()) {
            		 System.out.println("Match found!");
            		 model.resetComparisonTiles();
            		 view.getButtonPanel().updateCurrentTurn();
            	}
            	else {
            		view.getButtonPanel().disableButtons();
            		new java.util.Timer().schedule(new java.util.TimerTask() {
            			@Override
            		    public void run() {
            				
            		        SwingUtilities.invokeLater(() -> {
            		            view.getButtonPanel().setText(
            		                    view.getButtonPanel().getButtons()
            		                    [model.getFirstComparisonRowNumber()][model.getFirstComparisonColNumber()],
            		                    "?"
            		            );
            		            clickedButton.setText("?");
            		            model.resetComparisonTiles();
            		            view.getButtonPanel().enableButtons();
            		            view.getButtonPanel().updateCurrentTurn();
            		        });
            		    }
            		}, 2000);
            	}
            }
        } if (!"?".equals(symbol)){
            new WrongSelectionDialog(view);
        } if (model.isGameWon()) {
        	System.out.println("WON");
        	new LeaderBoardDialog(view, model);
        }
    }

    private int getButtonRow(JButton button) {
        JButton[][] buttons = view.getButtonPanel().getButtons();
        for (int row = 0; row < buttons.length; row++) {
            for (int column = 0; column < buttons[row].length; column++) {
                if (button == buttons[row][column]) {
                    return row;
                }
            }
        }
        return -1;
    }

    private int getButtonColumn(JButton button) {
        JButton[][] buttons = view.getButtonPanel().getButtons();
        for (int row = 0; row < buttons.length; row++) {
            for (int column = 0; column < buttons[row].length; column++) {
                if (button == buttons[row][column]) {
                    return column;
                }
            }
        }
        return -1;
    }
}
