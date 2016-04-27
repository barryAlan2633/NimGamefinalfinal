package me.arnavgarg.nimgame.Game;

/**
 * Created by Arnav on 4/24/2016.
 */
public class DifficultyHard extends GameDifficultyMain {


    @Override
    public void computerTurn(int a[]) {

        for(int i = 0; i < a.length; i++) {

            a[i]  = -5;
         }
    }

}
