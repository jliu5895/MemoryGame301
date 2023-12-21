package edu.wm.cs.cs301.guimemorygame.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import edu.wm.cs.cs301.guimemorygame.controller.SelectionAction;

import edu.wm.cs.cs301.guimemorygame.model.GUIMemoryGameModel;

public class ButtonPanel {

    private final JButton[][] buttons;
    private final JPanel panel;
    private final GUIMemoryGameModel model;
    private final SelectionAction selectionAction;

    public ButtonPanel(MemoryGameFrame view, GUIMemoryGameModel model) {
        this.model = model;
        this.selectionAction = new SelectionAction(view, model);
        int rows = model.getMaximumRows();
        int columns = model.getColumnCount();
        this.buttons = new JButton[rows][columns];
        this.panel = new JPanel(new BorderLayout());
        initializeButtons(view);
    }

    private void initializeButtons(MemoryGameFrame view) {
        JPanel buttonsPanel = new JPanel(new GridLayout(model.getMaximumRows(), model.getColumnCount()));

        for (int row = 0; row < model.getMaximumRows(); row++) {
            for (int column = 0; column < model.getColumnCount(); column++) {
                JButton button = new JButton("?");
                button.setPreferredSize(new Dimension(64, 64));
                button.addActionListener(new SelectionAction(view, model));
                buttons[row][column] = button;
                buttonsPanel.add(button);
            }
        }

        panel.add(buttonsPanel, BorderLayout.CENTER);
        panel.add(createTurnsPanel(), BorderLayout.SOUTH);  // Add the turns panel at the bottom
    }

    private JPanel createTurnsPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        Font footerFont = AppsFont.getFooterFont();

        String text = String.format("Current Turn: %,d", model.getCurrentTurn());
        JLabel label = new JLabel(text);
        label.setFont(footerFont);
        panel.add(label);

        return panel;
    }
    
    public void updateCurrentTurn() {
        String text = String.format("Current Turn: %,d", model.getCurrentTurn());
        JLabel label = new JLabel(text);
        label.setFont(AppsFont.getFooterFont());

        JPanel turnsPanel = new JPanel(new FlowLayout());
        turnsPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        turnsPanel.add(label);

       
        panel.remove(1); // Assumes the turns panel is at index 1, adjust if needed
        panel.add(turnsPanel, BorderLayout.SOUTH);
        // Repaint the panel to reflect the changes
        panel.revalidate();
        panel.repaint();
    }

    public void setButtonSymbol(int row, int column, char symbol) {
        JButton button = buttons[row][column];
        button.setText(String.valueOf(symbol));
    }
    
    public void resetDefaultPanel() {
        for (int row = 0; row < buttons.length; row++) {
            for (int column = 0; column < buttons[row].length; column++) {
                JButton button = buttons[row][column];
                button.setText("?");
                button.setEnabled(true);
                // Reset any other properties you may have changed during the game
            }
        }
    }
    
    public void disableButtons() {
        for (int row = 0; row < buttons.length; row++) {
            for (int column = 0; column < buttons[row].length; column++) {
                JButton button = buttons[row][column];
                button.setEnabled(false);
            }
        }
    }

    public void enableButtons() {
        for (int row = 0; row < buttons.length; row++) {
            for (int column = 0; column < buttons[row].length; column++) {
                JButton button = buttons[row][column];
                button.setEnabled(true);
            }
        }
    }

    public JButton[][] getButtons() {
        return buttons;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setText(JButton jButton, String string) {
        jButton.setText(string);
    }
}