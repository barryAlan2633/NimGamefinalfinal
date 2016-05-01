package me.arnavgarg.nimgame.Game;

/**
 * Created by Arnav on 4/28/2016.
 */
public class Intermediate extends GameDifficultyMain {


    @Override
    public int computerTurn(int[] a) {

        String[] bits = new String[a.length];
        int max = 0;
        for(int i = 0; i < a.length; i++) {

            if(a[max] < a[i]) max = i;
            bits[i] = String.format("%3s", Integer.toBinaryString(a[i])).replace(' ', '0');
        }

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

        return 0;
    }
}
