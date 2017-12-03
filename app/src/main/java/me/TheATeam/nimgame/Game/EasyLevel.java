package me.TheATeam.nimgame.Game;
import java.util.Random;

public class EasyLevel extends GameDifficultyMain {

    @Override
    public int[] compTurn(int[] a) {

        Random randomNum = new Random();
        int max = a[0];
        //finds max girs
        for (int i = 0; i < a.length; i++) {

            if (a[i] > max) {
                max = a[i];
            }
        }

        //randomly finds row and then randomNum
        int value = 0;
        int row = 0;
        if (max != 0) {
            while (true) {
                row = randomNum.nextInt(7);
                if (a[row] != 0) {
                    value = randomNum.nextInt(a[row]) + 1;
                    break;
                }
            }
        }
        return new int[]{row, value};
    }
}
