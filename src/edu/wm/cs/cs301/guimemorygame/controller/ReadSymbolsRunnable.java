package edu.wm.cs.cs301.guimemorygame.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wm.cs.cs301.guimemorygame.model.GUIMemoryGameModel;


public class ReadSymbolsRunnable implements Runnable{
	
	private final static Logger LOGGER =
			Logger.getLogger(ReadSymbolsRunnable.class.getName());
	
	private final GUIMemoryGameModel model;
	
	public ReadSymbolsRunnable(GUIMemoryGameModel model) {
		LOGGER.setLevel(Level.INFO);

		try {
			FileHandler fileTxt = new FileHandler("./logging.txt");
			LOGGER.addHandler(fileTxt);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.model = model;
	}
	
	@Override
	public void run() {
		List<Character> symbolList;

		try {
			symbolList = createSymbolList();
			LOGGER.info("Created word list of " + symbolList.size() + " symbols.");
		} catch (IOException e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			symbolList = new ArrayList<>();
		}

		model.setSymbolList(symbolList);
		model.generateUniqueSymbolList();
	}
	
	private List<Character> createSymbolList() throws IOException {

		List<Character> symbolList = new ArrayList<>();

		String text = "Emojis.txt";
		ClassLoader loader = this.getClass().getClassLoader();
		InputStream stream = loader.getResourceAsStream(text);

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		String line = reader.readLine();
		while (line != null) {
			line = line.trim();
			symbolList.add(line.charAt(0));
			line = reader.readLine();
		}
		reader.close();

		return symbolList;
	}
}