package edu.wm.cs.cs301.guimemorygame.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import edu.wm.cs.cs301.guimemorygame.model.GUIMemoryGameModel;



public class MemoryGameFrame {
    private final JFrame frame;
    private final ButtonPanel buttonPanel;
    private final GUIMemoryGameModel model;
 
    public MemoryGameFrame(GUIMemoryGameModel model) {
    	this.model = model;
        this.buttonPanel = new ButtonPanel(this, model);
        this.frame = createAndShowGUI();
    }

    private JFrame createAndShowGUI() {
        JFrame frame = new JFrame("Memory Game");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setJMenuBar(createMenuBar());
        frame.setResizable(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                shutdown();
            }
        });
        
        frame.add(createTitlePanel(), BorderLayout.NORTH);
        frame.add(buttonPanel.getPanel(), BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        System.out.println("Frame size: " + frame.getSize());
        return frame;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu helpMenu = new JMenu("Help");
        JMenu difficultyMenu = new JMenu("Difficulty");
        
        menuBar.add(helpMenu);
        menuBar.add(difficultyMenu);
        
        JMenuItem EasyDifficulty = new JMenuItem("Easy");
		EasyDifficulty.addActionListener(new EasyDifficultyAction());
		difficultyMenu.add(EasyDifficulty);
		
		JMenuItem MediumDifficulty = new JMenuItem("Medium");
		MediumDifficulty.addActionListener(new MediumDifficultyAction());
		difficultyMenu.add(MediumDifficulty);
		
		JMenuItem HardDifficulty = new JMenuItem("Hard");
		HardDifficulty.addActionListener(new HardDifficultyAction());
		difficultyMenu.add(HardDifficulty);
		
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(event -> shutdown());
        menuBar.add(quitItem);
        
        JMenuItem instructionsItem = new JMenuItem("Instructions...");
		instructionsItem.addActionListener(event -> new InstructionsDialog(this));
		helpMenu.add(instructionsItem);

        return menuBar;
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelAction");
        ActionMap actionMap = panel.getActionMap();
        actionMap.put("cancelAction", new CancelAction());

        JLabel label = new JLabel("Memory Game");
        label.setFont(AppsFont.getTitleFont());
        panel.add(label);

        return panel;
    }
    
    public void shutdown() {
        frame.dispose();
        System.exit(0);
    }

    public JFrame getFrame() {
        return frame;
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public void repaintButtonPanel() {
        buttonPanel.getPanel().revalidate();
        buttonPanel.getPanel().repaint();
    }

    private class CancelAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            shutdown();
        }
    }
    private class EasyDifficultyAction extends AbstractAction {

    		private static final long serialVersionUID = 1L;

    		@Override
    		public void actionPerformed(ActionEvent event) {
    			frame.dispose();
    			model.updateColCount(4);
    			model.updataMaxRows(3);
    			model.initialize();
    			new MemoryGameFrame(model);
    		}
    	}
    private class MediumDifficultyAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent event) {
			frame.dispose();
			model.updateColCount(7);
			model.updataMaxRows(4);
			model.initialize();
			new MemoryGameFrame(model);
		}
	}
    private class HardDifficultyAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent event) {
			frame.dispose();
			model.updateColCount(8);
			model.updataMaxRows(7);
			model.initialize();
			new MemoryGameFrame(model);
		}
	}
}