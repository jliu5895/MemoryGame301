package edu.wm.cs.cs301.guimemorygame.view;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Timer;
import java.util.TimerTask;

public class WrongSelectionDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final Timer timer;

    public WrongSelectionDialog(MemoryGameFrame view) {
        super(view.getFrame(), "Wrong Selection", true);
        this.timer = new Timer();

        add(createWordPanel(), BorderLayout.NORTH);
        
        pack();
        
        setLocationRelativeTo(view.getFrame());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        view.getButtonPanel().disableButtons(); 

        
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    dispose();
                    view.getButtonPanel().enableButtons(); 
                });
            }
        }, 2000);
        setVisible(true);
    }

    private JPanel createWordPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        JLabel label = new JLabel("Already Visible! Choose Again!");

        panel.add(label, BorderLayout.CENTER);

        return panel;
    }
}