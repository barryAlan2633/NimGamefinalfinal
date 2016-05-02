package me.arnavgarg.nimgame.Game;

import java.util.Random;

/**
 * Created by Arnav on 4/28/2016.
 */
public class Easy extends GameDifficultyMain {
    @Override
    public int[] computerTurn(int[] a) {

        Random random = new Random();

        int max = a[0];

        for (int i = 0; i < a.length; i++) {

            if (a[i] > max) {
                max = a[i];
            }
        }

        int value = 0;
        int row = 0;

        if(max != 0) {
            while(true) {

                row = random.nextInt(7);
                if(a[row] != 0) {

                    value = random.nextInt(a[row])+1;
                    break;
                }
            }
        }

        return new int[]{row, value};
    }
}
