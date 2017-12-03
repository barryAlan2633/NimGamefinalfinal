package me.TheATeam.nimgame.Game;

import android.util.Log;

import java.util.Random;

public class HardLevel extends GameDifficultyMain {

    private final static String LOG_TAG = HardLevel.class.getSimpleName();

    @Override
    public int[] compTurn(int a[]) {

        //stores values of each row
        String[] bits = new String[a.length];
        int max = 0;

        //gets highest number
        for (int i = 0; i < a.length; i++) {

            if (a[i] > a[max]) {
                max = i;
            }
        }
        //Storing the binary in a string
        for (int i = 0; i < a.length; i++) {
            bits[i] = String.format("%3s", Integer.toBinaryString(a[i])).replace(' ', '0');
        }

        int[] answerBit = new int[3];
        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j < bits.length; j++) {

                answerBit[i] += Integer.parseInt(String.valueOf(bits[j].charAt(i)));
            }

            if (answerBit[i] % 2 == 0) {
                answerBit[i] = 0;
            } else {
                answerBit[i] = 1;
            }
        }

        int[] changedBit = new int[3];

        for (int i = 2; i >= 0; i--) {


            if (answerBit[i] == 1) {


                if (Integer.parseInt(String.valueOf(bits[max].charAt(i))) == 1) {
                    changedBit[i] = 0;
                } else {
                    changedBit[i] = 1;
                }
            } else {
                changedBit[i] = Integer.parseInt(String.valueOf(bits[max].charAt(i)));
            }
        }

        //how you calculate the sum
        int differenceOfSum = (Integer.parseInt(String.valueOf(bits[max].charAt(0))) * 4 + Integer.parseInt(String.valueOf(bits[max].charAt(1))) * 2
                + Integer.parseInt(String.valueOf(bits[max].charAt(2)))) - (changedBit[0] * 4 + changedBit[1] * 2 + changedBit[2]);
        if (differenceOfSum == 0) {
            Random random = new Random();
            differenceOfSum = random.nextInt(a[max]) + 1;
        }

        //returns row and sum
        return new int[]{max, differenceOfSum};
    }

}
