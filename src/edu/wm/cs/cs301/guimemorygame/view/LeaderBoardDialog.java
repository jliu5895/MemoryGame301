package edu.wm.cs.cs301.guimemorygame.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import edu.wm.cs.cs301.guimemorygame.model.GUIMemoryGameModel;


public class LeaderBoardDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private final ExitAction exitAction;
	
	private final NextAction nextAction;
	
	private final MemoryGameFrame view;
	
	private final GUIMemoryGameModel model;
	
	public LeaderBoardDialog(MemoryGameFrame view, GUIMemoryGameModel model) {
		super(view.getFrame(), "LeaderBoard", true);
		this.view = view;
		this.model = model;
		this.exitAction = new ExitAction();
		this.nextAction = new NextAction();
		
		add(createMainPanel(), BorderLayout.NORTH);
		
		
		pack();
		setLocationRelativeTo(view.getFrame());
		setVisible(true);
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		panel.add(createTopPanel(), BorderLayout.NORTH);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createTopPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		panel.add(createTopTitlePanel(), BorderLayout.NORTH);
		panel.add(createSummaryPanel(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createTopTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		panel.add(createTitlePanel(), BorderLayout.NORTH);
		panel.add(createWordPanel(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		JLabel label = null;
		if (model.getMaximumRows() == 3) {
			label = new JLabel("Easy Mode LeaderBoard");
			}
		else if (model.getMaximumRows() == 4) {
			label = new JLabel("Medium Mode LeaderBoard");
			}
		else {
			label = new JLabel("Hard Mode Leaderboard");
			}
		label.setFont(AppsFont.getTitleFont());
		panel.add(label);
		
		return panel;
	}
	
	private JPanel createWordPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		JLabel label;
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		label = new JLabel("Congrats! You found all the pairs!");
		
		panel.add(label, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createSummaryPanel() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

	    panel.add(createLeaderBoardPanel(
	            model.getLeaderBoard().getTopScorers().get(0) + " = " + model.getLeaderBoard().getTopScores().get(0),
	            "First Place Scorer"), BorderLayout.NORTH);

	    panel.add(createLeaderBoardPanel(
	            model.getLeaderBoard().getTopScorers().get(1) + " = " + model.getLeaderBoard().getTopScores().get(1),
	            "Second Place Scorer"), BorderLayout.AFTER_LAST_LINE);

	    panel.add(createLeaderBoardPanel(
	            model.getLeaderBoard().getTopScorers().get(2) + " = " + model.getLeaderBoard().getTopScores().get(2),
	            "Third Place Scorer"), BorderLayout.AFTER_LAST_LINE);

	    return panel;
	}

	
	private JPanel createLeaderBoardPanel(String value, String line) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		Font textFont = AppsFont.getTextFont();
		
		JLabel valueLabel = new JLabel(line);
		valueLabel.setFont(AppsFont.getTitleFont());
		panel.add(valueLabel);
		
		JLabel label = new JLabel(value);
		label.setFont(textFont);
		panel.add(label);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exitAction");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "nextAction");
		ActionMap actionMap = panel.getActionMap();
		actionMap.put("nextAction", nextAction);
		actionMap.put("exitAction", exitAction);
		
		JButton nextButton = new JButton("Play Again!");
		nextButton.addActionListener(nextAction);
		panel.add(nextButton);
		
		JButton exitButton = new JButton("Exit Memory Game");
		exitButton.addActionListener(exitAction);
		panel.add(exitButton);
		
		Dimension nextDimension = nextButton.getPreferredSize();
		Dimension exitDimension = exitButton.getPreferredSize();
		int maxWidth = Math.max(nextDimension.width, exitDimension.width) + 10;
		nextButton.setPreferredSize(new Dimension(maxWidth, nextDimension.height));
		exitButton.setPreferredSize(new Dimension(maxWidth, exitDimension.height));
		
		return panel;
	}
	
	private class ExitAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
			view.shutdown();
		}
	}
	private class NextAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
			model.initialize();
			view.repaintButtonPanel();
			view.getButtonPanel().updateCurrentTurn();
			view.getButtonPanel().resetDefaultPanel();
	}

}
}