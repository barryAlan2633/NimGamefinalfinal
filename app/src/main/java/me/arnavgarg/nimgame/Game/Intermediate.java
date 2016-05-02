package me.arnavgarg.nimgame.Game;

import java.util.Random;

/**
 * Created by Arnav on 4/28/2016.
 */
public class Intermediate extends GameDifficultyMain{

//Basically we are randomly choosing a move from hard and random
    @Override
    public int[] computerTurn(int[] a) {

        Hard hardLevel = new Hard();
        Easy easyLevel = new Easy();

        Random random = new Random();
        int[] returnValue;

        if(random.nextBoolean()) {

            returnValue = hardLevel.computerTurn(a);
        } else {

            returnValue = easyLevel.computerTurn(a);
        }

        return returnValue;
    }
}
