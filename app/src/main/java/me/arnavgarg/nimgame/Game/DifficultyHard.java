package me.arnavgarg.nimgame.Game;

import android.util.Log;

import java.util.Random;

/**
 * Created by Arnav on 4/24/2016.
 */
public class DifficultyHard extends GameDifficultyMain {

    private final static String LOG_TAG = DifficultyHard.class.getSimpleName();

    @Override
    public int computerTurn(int a[]) {

        String[] bits = new String[a.length];

        int max = a[0];

        for(int i = 1; i < a.length; i++) {

            if(a[i] > max) {
                max = a[i];
            }
        }

        //Storing the binary in string form.
        for(int i = 0; i < a.length; i++) {
            bits[i] = String.format("%3s", Integer.toBinaryString(a[i])).replace(' ', '0');
        }
        Log.d(LOG_TAG, "" + bits[0] + " " + bits[1] + " " + bits[2]);


        //for addition of the bits.
        int[] answerBit = new int[3];
        int sum = 0;
        for(int i = 2; i >= 0; i--) {
            for(int j = 0; j < bits.length; j++) {
                answerBit[i] += bits[j].charAt(i);
            }

            if(answerBit[i]%2 == 0) {
                answerBit[i] = 0;
            } else {
                answerBit[i] = 1;
            }
        }

        Log.d(LOG_TAG, "" + answerBit[0] + " " + answerBit[1] + " " + answerBit[2]);

        //calculation the "NIM SUM"
        for(int i = 0; i <= answerBit.length-1; i++) {

            Log.d(LOG_TAG, " " + answerBit[i]*Math.pow(2, answerBit.length-1-i));
            sum += (int) (answerBit[i]*Math.pow(2, answerBit.length-1-i));
        }


        Log.d(LOG_TAG, "MAX: " + max);

        if((sum > max || sum == 0) && max != 0) {

            Random random = new Random();
            sum = random.nextInt(max) + 1;
        }

        return sum;

    }

}
