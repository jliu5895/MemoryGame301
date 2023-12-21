package edu.wm.cs.cs301.guimemorygame.view;

import javax.sound.sampled.*;
import javax.swing.*;

import edu.wm.cs.cs301.guimemorygame.model.GUIMemoryGameModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;

public class MemoryGameFrame {
    private JFrame frame;
    private ButtonPanel buttonPanel;
    private GUIMemoryGameModel model;
    private Clip backgroundMusic;
    private boolean isMuted = false;

    public MemoryGameFrame(GUIMemoryGameModel model) {
        this.model = model;
        this.buttonPanel = new ButtonPanel(this, model);
        this.frame = createAndShowGUI();
        initializeMusic();
        playBackgroundMusic();
    }

    private JFrame createAndShowGUI() {
        frame = new JFrame("Memory Game");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setJMenuBar(createMenuBar());
        frame.setResizable(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                shutdown();
            }
        });

        JPanel titlePanel = createTitlePanel();
        JButton muteButton = new JButton(new MuteAction());
        muteButton.setText(isMuted ? MuteAction.UNMUTE_TEXT : MuteAction.MUTE_TEXT);
        titlePanel.add(muteButton);

        frame.add(titlePanel, BorderLayout.NORTH);
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
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label);

        return panel;
    }

    private void initializeMusic() {
        try {
           
            InputStream in = getClass().getResourceAsStream("/edu/wm/cs/cs301/guimemorygame/wav/Funny-background-music.wav");

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(in);

            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioStream);
 
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playBackgroundMusic() {
        try {
            stopBackgroundMusic(); // Stop and close the current Clip

            InputStream in = getClass().getResourceAsStream("/edu/wm/cs/cs301/guimemorygame/wav/Funny-background-music.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(in);

            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioStream);

            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);

            backgroundMusic.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && !isMuted) {
                    backgroundMusic.setMicrosecondPosition(0);
                    backgroundMusic.start();
                }
            });

            if (!isMuted) {
                backgroundMusic.start();
            }
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }

    public void shutdown() {
        stopBackgroundMusic();
        frame.dispose();
        System.exit(0);
    }

    private class MuteAction extends AbstractAction {
        private static final String MUTE_TEXT = "Mute Music";
        private static final String UNMUTE_TEXT = "Unmute Music";

        @Override
        public void actionPerformed(ActionEvent event) {
            isMuted = !isMuted;

            if (isMuted) {
                stopBackgroundMusic();
            } else {
                playBackgroundMusic();
            }

            ((JButton) event.getSource()).setText(isMuted ? UNMUTE_TEXT : MUTE_TEXT);
        }
    }

    private class CancelAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent event) {
            shutdown();
        }
    }

    private class EasyDifficultyAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent event) {
        	stopBackgroundMusic();
            frame.dispose();
            model.updateColCount(4);
            model.updataMaxRows(3);
            model.initialize();
            MemoryGameFrame newFrame = new MemoryGameFrame(model);
        }
    }

    private class MediumDifficultyAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent event) {
        	stopBackgroundMusic();
            frame.dispose();
            model.updateColCount(7);
            model.updataMaxRows(4);
            model.initialize();
            new MemoryGameFrame(model);
            
        }
    }

    private class HardDifficultyAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent event) {
        	stopBackgroundMusic();
            frame.dispose();
            model.updateColCount(8);
            model.updataMaxRows(7);
            model.initialize();
            new MemoryGameFrame(model);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIMemoryGameModel model = new GUIMemoryGameModel();
            new MemoryGameFrame(model);
        });
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

}

