package edu.wm.cs.cs301.guimemorygame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import edu.wm.cs.cs301.guimemorygame.model.GUIMemoryGameModel;
import edu.wm.cs.cs301.guimemorygame.view.MemoryGameFrame;
import edu.wm.cs.cs301.guimemorygame.view.NameDialog;


public class GuiMemoryGame implements Runnable{
	
	public static void main(String[] args) {
			SwingUtilities.invokeLater(new GuiMemoryGame());
			
			//use cross-platform look and feel so button backgrounds work on Mac
			try {
			    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
			 } catch (Exception e) {
				 e.printStackTrace();
			 }

	}

	@Override
	public void run() {
		new NameDialog((new MemoryGameFrame(new GUIMemoryGameModel())));
	}
}
