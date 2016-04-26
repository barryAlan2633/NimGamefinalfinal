package me.arnavgarg.nimgame.Game;

/**
 * Created by Arnav on 4/8/2016.
 */
public abstract class GameDifficultyMain {

    public boolean winningCondidtion(int[] a) {

        int q;
        q=(a[1] ^ a[2] ^ a[3] ^ a[4]);

        if(q == 1) {
            return true;
        }
        return false;
    }

    public abstract void computerTurn(int[] a);
}
