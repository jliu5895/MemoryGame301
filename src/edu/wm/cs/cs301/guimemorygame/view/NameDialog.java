package edu.wm.cs.cs301.guimemorygame.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import edu.wm.cs.cs301.guimemorygame.model.GUIMemoryGameModel;

public class NameDialog extends JDialog{
	
	private static final long serialVersionUID = 1L;
	
	private final MemoryGameFrame view;
	
	private static String finalName;
	
	JLabel label1 = new JLabel("Name");
	
	JTextField nameField = new JTextField();
	
	private final CancelAction cancelAction;
	
	private final SubmitAction submitAction;
	
	public NameDialog(MemoryGameFrame view) {
		super(view.getFrame(), "Input Name", true);
		this.view = view;
		
		this.cancelAction = new CancelAction();
		this.submitAction = new SubmitAction();
		
		add(createMainPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(view.getFrame());
		setVisible(true);
		
		if (finalName == null || finalName.equals("")){
        	view.shutdown();
			dispose();
        }
    }
	private JPanel createMainPanel() {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

	    nameField = new JTextField(16);
	    panel.add(nameField, BorderLayout.CENTER);

	    JPanel messagePanel = new JPanel(new GridLayout(2, 1)); // 2 rows, 1 column
	    JLabel firstMessageLabel = new JLabel("Entering no name or only spaces is not valid.");
	    JLabel secondMessageLabel = new JLabel("Exiting out of this name input exits the whole game.");
	    messagePanel.add(firstMessageLabel);
	    messagePanel.add(secondMessageLabel);
	    
	    panel.add(messagePanel, BorderLayout.SOUTH);

	    return panel;
	}

	
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelAction");
		ActionMap actionMap = panel.getActionMap();
		actionMap.put("cancelAction", cancelAction);
		actionMap.put("submitAction", submitAction);
		JButton cancelbutton = new JButton("Cancel");
		JButton submitbutton = new JButton("Submit");
		
		cancelbutton.addActionListener(cancelAction);
		submitbutton.addActionListener(submitAction);
		panel.add(cancelbutton);
		panel.add(submitbutton);
		return panel;
	}
	
	private class SubmitAction extends AbstractAction {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void actionPerformed(ActionEvent event) {
	        // Check if the event source is the submit button
	        if (event.getSource() instanceof JButton) {
	            JButton sourceButton = (JButton) event.getSource();
	            String actionCommand = sourceButton.getActionCommand();

	            // Check if the action command is "Submit"
	            if ("Submit".equals(actionCommand)) {
	                String name = nameField.getText();
	                System.out.println(name);
	                boolean newname = name.trim().isEmpty();
	                if (!newname) {
	                    finalName = name;
	                    GUIMemoryGameModel.setName(name);
	                    new InstructionsDialog(view);
	                    dispose();
	                }
	            }
	        }
	    }
	}
	private class CancelAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent event) {
			view.shutdown();
			dispose();
		}
		
	}
	
	public String getName() {
		return finalName;
	}
}