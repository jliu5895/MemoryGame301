package edu.wm.cs.cs301.guimemorygame.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {

	private List<Integer> topScores;
    private List<String> topScorers;

    private String path, log;

    public LeaderBoard(int rowNumber) {
        String fileSeparator = System.getProperty("file.separator");
        this.path = System.getProperty("user.home") + fileSeparator + "GUIMemoryGame";
        if (rowNumber == 3) {
            this.log = fileSeparator + "leaderboardEasy.log";
        } else if (rowNumber == 4) {
            this.log = fileSeparator + "leaderboardMedium.log";
        } else {
            this.log = fileSeparator + "leaderboardHard.log";
        }
        readLeaderboard();
    }

    private void readLeaderboard() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + log));
            
            topScores = new ArrayList<>();
            topScorers = new ArrayList<>();
            
            for (int i = 0; i < 3; i++) {
                int score = Integer.valueOf(br.readLine().trim());
                String scorer = br.readLine().trim();
                
                topScores.add(score);
                topScorers.add(scorer);
            }
            System.out.println("asdfasdf");
            br.close();
        } catch (FileNotFoundException e) {
            initializeLeaderboard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeLeaderboard() {
        topScores = new ArrayList<>();
        topScorers = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            topScores.add(0);
            topScorers.add("No one");
        }
    }

    public void writeLeaderboard(int score, String scorer) {
        try {
            File file = new File(path);
            file.mkdir();
            file = new File(path + log);
            file.createNewFile();

            
            updateLeaderboard(score, scorer);
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < 3; i++) {
                bw.write(Integer.toString(topScores.get(i)));
                bw.write(System.lineSeparator());
                bw.write(topScorers.get(i));
                bw.write(System.lineSeparator());
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateLeaderboard(int playerScore, String playerName) {
        for (int i = 0; i < 3; i++) {
            if (playerScore < topScores.get(i) | topScores.get(i) == 0) {
            	System.out.print(topScores.get(i));
                topScores.add(i, playerScore);
                topScorers.add(i, playerName);
                break;
            }
        }
    }

    public List<Integer> getTopScores() {
        return topScores;
    }

    public List<String> getTopScorers() {
        return topScorers;
    }
}