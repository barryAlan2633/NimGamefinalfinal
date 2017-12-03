package me.TheATeam.nimgame.Database;

import android.content.Context;

public class GetData {
 //gets data and puts into variables
    //easier to access

    private int difficultyLevel;
    private int firstTurn;
    private int numberOfGirs;
    private NimGameDatabase gameDatabase;

    public GetData(Context context) {
        gameDatabase = new NimGameDatabase(context);
    }


    public void parseData() {

        gameDatabase.open();
        String data = gameDatabase.getData();
        if(data != null) {

            String[] tokens = data.split(" ");

            difficultyLevel = Integer.parseInt(tokens[1]);
            firstTurn = Integer.parseInt(tokens[2]);
            numberOfGirs = Integer.parseInt(tokens[3]);

        }

        gameDatabase.close();
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public int getFirstTurn() {
        return firstTurn;
    }

    public int getGirNumber() {
        return numberOfGirs;
    }
}
