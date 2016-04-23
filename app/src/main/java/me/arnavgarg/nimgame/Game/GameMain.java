package me.arnavgarg.nimgame.Game;

import android.app.Activity;
import android.os.Bundle;

import me.arnavgarg.nimgame.R;

/**
 * Created by Arnav on 4/7/2016.
 */

enum WorkingRow {
    NONE, ROW1, ROW2, ROW3, ROW4, ROW5, ROW6, ROW7
}

public class GameMain extends Activity {



    private WorkingRow workingRow;
    private static final String LOG_TAG = GameMain.class.getSimpleName();
    private static int MAX_ROWS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);


    }


}
