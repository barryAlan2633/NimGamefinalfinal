package me.arnavgarg.nimgame.Game;

/**
 * Created by Arnav on 4/24/2016.
 */
public class DifficultyHard extends GameDifficultyMain {

    @Override
    public boolean winningCondidtion(int[] a) {
        return super.winningCondidtion(a);
    }

    @Override
    public void computerTurn(int a[]) {
        if(winningCondidtion(a)) {
            for(int i = 0; i < 4; i++) {

                int storeRowValue = a[i];
                int resultChecker = 0;
                while(a[i] > 0) {
                    if(!winningCondidtion(a)){
                        //a[i] -= 1;
                        break;
                    }
                    a[i] = a[i]-1;
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
            for(int i = 0; i < 4; i++) {
                if(a[i] != 0) {
                    a[i] = a[i] - 1;
                    break;
                }
            }
        }
    }

}
