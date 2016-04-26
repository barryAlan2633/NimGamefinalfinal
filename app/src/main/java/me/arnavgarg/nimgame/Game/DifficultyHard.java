package me.arnavgarg.nimgame.Game;

import android.util.Log;

/**
 * Created by Arnav on 4/24/2016.
 */
public class DifficultyHard extends GameDifficultyMain {

    @Override
    public boolean winningCondidtion(int[] a) {
        return super.winningCondidtion(a);
    }

    @Override
    public int[] computerTurn(int a[]) {

        int[] storeValue = new int[2];
        int counter = 0;
        int i;

        if(winningCondidtion(a)) {
            for(i = 0; i < 4; i++) {

                int storeRowValue = a[i];
                int resultChecker = 0;
                while(a[i] > 0) {
                    if(!winningCondidtion(a)){
                        //a[i] -= 1;
                        break;
                    }
                    a[i] = a[i]-1;
                    counter++;
                    resultChecker = 1;
                }

                if(resultChecker == 0) {
                    a[i] = storeRowValue;
                }
                else if(resultChecker == 1) {
                    break;
                }
            }
        }
        else {
            for(i = 0; i < 4; i++) {
                if(a[i] != 0) {
                    a[i] = a[i] - 1;
                    counter++;
                    break;
                }
            }
        }

        Log.d("DifficultyHard", "" + counter);
        storeValue[0] = i;
        storeValue[1] = counter;
        return storeValue;
    }

}
