package me.arnavgarg.nimgame.Game;

import android.util.Log;

/**
 * Created by Arnav on 4/24/2016.
 */
public class Hard extends GameDifficultyMain {

    private final static String LOG_TAG = Hard.class.getSimpleName();

    @Override
    public int[] computerTurn(int a[]) {

        String[] bits = new String[a.length];

        int max = 0;

        for (int i = 0; i < a.length; i++) {

            if (a[i] > a[max]) {
                max = i;
            }
        }

        //Storing the binary in string form.
        for (int i = 0; i < a.length; i++) {
            bits[i] = String.format("%3s", Integer.toBinaryString(a[i])).replace(' ', '0');
        }
        Log.d(LOG_TAG, "" + bits[0] + " " + bits[1] + " " + bits[2] + " " + bits[3] + " " + bits[4]);


        //for addition of the bits.
        int[] answerBit = new int[3];
        for (int i = 2; i >= 0; i--) {
            Log.d(LOG_TAG, "____________________");
            for (int j = 0; j < bits.length; j++) {
                Log.d(LOG_TAG, "[BIT VALUE]: " + Integer.parseInt(String.valueOf(bits[j].charAt(i))));
                answerBit[i] += Integer.parseInt(String.valueOf(bits[j].charAt(i)));
            }

            if (answerBit[i] % 2 == 0) {
                answerBit[i] = 0;
            } else {
                answerBit[i] = 1;
            }
        }

        //This is the result of the nim sum!
        //Now we have to flip one of the arrays bits accordinly!
        Log.d(LOG_TAG, "" + answerBit[0] + " " + answerBit[1] + " " + answerBit[2]);

        int[] changedBit = new int[3];

        for (int i = 2; i >= 0; i--) {

            //This is trouble.. se we fix the max bit valued number!
            if (answerBit[i] == 1) {

                Log.d(LOG_TAG, "[FUCK THIS] " + Integer.parseInt(String.valueOf(bits[max].charAt(i))));
                if (Integer.parseInt(String.valueOf(bits[max].charAt(i))) == 1) {
                    changedBit[i] = 0;
                } else {
                    changedBit[i] = 1;
                }
            } else {
                changedBit[i] = Integer.parseInt(String.valueOf(bits[max].charAt(i)));
            }
        }

        Log.d(LOG_TAG, "CHECKING: " + changedBit[0] + " " + changedBit[1] + " "+ changedBit[2]);
        //Manually calculating the sum difference like an idiot.
        //Should have used a for loop.
        int sumDiff = (Integer.parseInt(String.valueOf(bits[max].charAt(0)))*4
                + Integer.parseInt(String.valueOf(bits[max].charAt(1)))*2
                + Integer.parseInt(String.valueOf(bits[max].charAt(2))))
                     - (changedBit[0]*4 + changedBit[1]*2 + changedBit[2]);



        Log.d(LOG_TAG, "Row: " + max + " Number: " + sumDiff);

        //returning the row and the sum that needs to be removed.
        return new int[]{max, sumDiff};
    }

}
