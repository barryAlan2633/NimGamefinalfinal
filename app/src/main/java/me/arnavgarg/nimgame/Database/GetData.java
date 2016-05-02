package me.arnavgarg.nimgame.Database;

import android.content.Context;

/**
 * Created by Arnav on 4/23/2016.
 */
public class GetData {

    /**
     * Created this to basically parse the data from the database and store it in variables which can
     * be called using getters later. Makes my life easier.
     */

    private int difficultyLevel;
    private int firstTurn;
    private int numberOfSticks;
    private GameDatabase gameDatabase;

    public GetData(Context context) {
        gameDatabase = new GameDatabase(context);
    }

    //Getting the data from the database and parsing it and storing it in the variables.
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

    /*
    Getters...
     */
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
