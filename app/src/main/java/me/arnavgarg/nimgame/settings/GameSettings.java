package me.arnavgarg.nimgame.settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import me.arnavgarg.nimgame.Database.GameDatabase;
import me.arnavgarg.nimgame.R;

/**
 * Created by Arnav on 4/6/2016.
 */
public class GameSettings extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{


    private static final String LOG_TAG = GameSettings.class.getSimpleName();
    Button btnDone;
    RadioGroup difficultyGroup, turnGroup, sticksGroup;
    GameDatabase gameDatabase;

    private int difficultyLevel, firstTurn, numberOfSticks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_settings);
        btnDone = (Button) findViewById(R.id.btnDone);
        difficultyGroup = (RadioGroup) findViewById(R.id.rgDifficulty);
        turnGroup = (RadioGroup) findViewById(R.id.rgFirstTurn);
        sticksGroup = (RadioGroup) findViewById(R.id.rgSticks);
        gameDatabase = new GameDatabase(this);

        gameDatabase.open();

        String data = gameDatabase.getData();

        if(data != null) {

            String[] tokens = data.split(" ");

            int diffculty, turn, sticksNumber;

            diffculty = Integer.parseInt(tokens[1]);
            turn = Integer.parseInt(tokens[2]);
            sticksNumber = Integer.parseInt(tokens[3]);

            Log.d(LOG_TAG, "Difficulty Level: " + diffculty
                    + "\nFirst Turn: " + turn
                    + "\nNumber of Sticks: " + sticksGroup.getCheckedRadioButtonId()
                    + "\nData: " + data);

            difficultyGroup.check(diffculty);
            turnGroup.check(turn);
            sticksGroup.check(sticksNumber);
        }

        gameDatabase.close();


        btnDone.setOnClickListener(this);
        difficultyGroup.setOnCheckedChangeListener(this);
        turnGroup.setOnCheckedChangeListener(this);
        sticksGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch(group.getId()) {

            case R.id.rgDifficulty:
                switch(checkedId) {

                    case R.id.rDiffEasy:
                        break;
                    case R.id.rDiffIntermediate:
                        break;
                    case R.id.rDiffDifficult:
                        break;
                }
                break;

            case R.id.rgFirstTurn:
                switch(checkedId) {

                    case R.id.rUser:
                        break;
                    case R.id.rComputer:
                        break;
                }
                break;

            case R.id.rgSticks:
                switch(checkedId) {

                    case R.id.rThree:
                        break;
                    case R.id.rFour:
                        break;
                    case R.id.rFive:
                        break;
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {

        //TODO
    }
}
