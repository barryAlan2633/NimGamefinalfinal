package me.arnavgarg.nimgame.Game;

import java.util.Random;

/**
 * Created by Arnav on 4/28/2016.
 */
public class Easy extends GameDifficultyMain {

    /**
     * Nothing super-complicated. The computer just randomly chooses values. No smart algorithm :)
     */
    @Override
    public int[] computerTurn(int[] a) {

        Random random = new Random();
        int max = a[0];

        //Finding max to be over-cautious that we have some sticks left in the game.
        //i.e max != 0
        for (int i = 0; i < a.length; i++) {

            if (a[i] > max) {
                max = a[i];
            }
        }

        //Now we randomly find the row and then random value from the row.
        int value = 0;
        int row = 0;

        if (max != 0) {
            while (true) {

                row = random.nextInt(7);
                if (a[row] != 0) {

                    value = random.nextInt(a[row]) + 1;
                    break;
                }
            }
        }

        return new int[]{row, value};
    }
}
