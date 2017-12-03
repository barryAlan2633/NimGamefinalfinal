package me.TheATeam.nimgame.Game;

import java.util.Random;

public class Intermediate extends GameDifficultyMain{
    @Override
    public int[] compTurn(int[] a) {

        HardLevel hardLevel = new HardLevel();
        EasyLevel easyLevel = new EasyLevel();

        Random random = new Random();
        int[] returnVal;



        if(random.nextBoolean()) {

            returnVal = hardLevel.compTurn(a);
        } else {

            returnVal = easyLevel.compTurn(a);
        }

        return returnVal;
    }
}
