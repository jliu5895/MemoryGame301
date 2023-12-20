package edu.wm.cs.cs301.guimemorygame.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import edu.wm.cs.cs301.guimemorygame.controller.SelectionAction;
import edu.wm.cs.cs301.guimemorygame.model.AppColors;
import edu.wm.cs.cs301.guimemorygame.model.GUIMemoryGameModel;

public class ButtonPanel {

    private final JButton[][] buttons;
    private final JPanel panel;
    private final GUIMemoryGameModel model;

    public ButtonPanel(MemoryGameFrame view, GUIMemoryGameModel model) {
        this.model = model;
        int rows = model.getMaximumRows();
        int columns = model.getColumnCount();
        this.buttons = new JButton[rows][columns];
        this.panel = new JPanel(new GridLayout(rows, columns));

        initializeButtons(view);
    }

    private void initializeButtons(MemoryGameFrame view) {
        for (int row = 0; row < model.getMaximumRows(); row++) {
            for (int column = 0; column < model.getColumnCount(); column++) {
                JButton button = new JButton("?");
                button.setPreferredSize(new Dimension(64, 64));
                button.addActionListener(new SelectionAction(row, column, view));
                buttons[row][column] = button;
                panel.add(button);
            }
        }
    }
    
    public void setColor(int row, int column, Color backgroundColor, Color foregroundColor) {
        JButton button = buttons[row][column];
        Color currentBackgroundColor = button.getBackground();

        if (currentBackgroundColor.equals(AppColors.GREEN)) {
            // Do nothing
        } else if (currentBackgroundColor.equals(AppColors.YELLOW) && backgroundColor.equals(AppColors.GREEN)) {
            button.setBackground(backgroundColor);
            button.setForeground(foregroundColor);
        } else {
            button.setBackground(backgroundColor);
            button.setForeground(foregroundColor);
        }
    }
    
    public void resetDefaultColors() {
        for (int row = 0; row < buttons.length; row++) {
            for (int column = 0; column < buttons[row].length; column++) {
                JButton button = buttons[row][column];
                button.setBackground(null);
                button.setForeground(null);
            }
        }
    }
    
    
    public JButton[][] getButtons() {
        return buttons;
    }

    public JPanel getPanel() {
        return panel;
    }
}
