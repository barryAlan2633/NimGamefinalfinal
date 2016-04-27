package me.arnavgarg.nimgame.Game;

import android.util.Log;

/**
 * Created by Arnav on 4/24/2016.
 */
public class DifficultyHard extends GameDifficultyMain {


    @Override
    public void computerTurn(int a[]) {

        String[] bits = new String[a.length];

        //Storing the binary in string form.
        for(int i = 0; i < a.length; i++) {
            bits[i] = String.format("%3s", Integer.toBinaryString(a[i])).replace(' ', '0');
        }

        for(int i = 0; i < a.length; i++) {
            Log.d("difficulty", " " + bits[i]);
        }

        int[] answerBit = new int[bits.length];
        int sum = 0;
        for(int i = 2; i >= 0; i--) {
            for(int j = 0; j < bits.length; j++) {
                Log.d("difficulty", "" + j);
                answerBit[i] += bits[j].charAt(i);
            }

            if(answerBit[i]%2 == 0) {
                answerBit[i] = 0;
            } else {
                answerBit[i] = 1;
            }
        }

        for(int i = 0; i < answerBit.length; i++) {

            Log.d("difficulty", "Row " + i + " : " + answerBit[i]);
        }

        for(int i = 0; i < answerBit.length-1; i++) {

            sum += (int) (answerBit[i]*Math.pow(2, i));
        }

        Log.d("difficulty", "" + sum);

    }

}
