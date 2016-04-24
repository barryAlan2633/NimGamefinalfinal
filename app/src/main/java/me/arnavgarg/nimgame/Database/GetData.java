package me.arnavgarg.nimgame.Database;

import android.content.Context;

/**
 * Created by Arnav on 4/23/2016.
 */
public class GetData {

    private int difficultyLevel;
    private int firstTurn;
    private int numberOfSticks;
    private GameDatabase gameDatabase;

    public GetData(Context context) {
        gameDatabase = new GameDatabase(context);
    }

    public void parseData() {

        gameDatabase.open();

        String data = gameDatabase.getData();

        if(data != null) {

            String[] tokens = data.split(" ");

            difficultyLevel = Integer.parseInt(tokens[1]);
            firstTurn = Integer.parseInt(tokens[2]);
            numberOfSticks = Integer.parseInt(tokens[3]);

        }

        gameDatabase.close();
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public int getFirstTurn() {
        return firstTurn;
    }

    public int getNumberOfSticks() {
        return numberOfSticks;
    }
}
