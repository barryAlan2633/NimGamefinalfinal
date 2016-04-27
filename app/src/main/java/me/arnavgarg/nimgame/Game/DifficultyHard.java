package me.arnavgarg.nimgame.Game;

import android.util.Log;

/**
 * Created by Arnav on 4/24/2016.
 */
public class DifficultyHard extends GameDifficultyMain {

    @Override
    public boolean winningCondidtion(int[] a) {

        int q;
        q=(a[1] ^ a[2] ^ a[3] ^ a[4]);

        if(q == 0) {
            return false;
        }
        return true;
    }

    @Override
    public int[] computerTurn(int a[]) {

        int[] storeValue = new int[2];
        int counter = 0;
        int i;

//        for(i = 0; i < a.length; i++) {
//
//            Log.d("asdfg", " " + a[i]);
//        }

        if(winningCondidtion(a)) {
            Log.d("asdfg", "WORK!");
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

        Log.d("asdfg", "i: " + i + "counter: " + counter);
        storeValue[0] = i;
        storeValue[1] = counter;
        return storeValue;
    }

}
