package me.arnavgarg.nimgame.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import me.arnavgarg.nimgame.Database.GameDatabase;
import me.arnavgarg.nimgame.Homescreen.MainActivity;
import me.arnavgarg.nimgame.R;

/**
 * Created by Arnav on 4/6/2016.
 */
public class GameSettings extends Activity implements RadioGroup.OnCheckedChangeListener{


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
                    + "\nNumber of Sticks: " + sticksNumber
                    + "\nData: " + data);

            difficultyGroup.check(diffculty);
            turnGroup.check(turn);
            sticksGroup.check(sticksNumber);
        }

        gameDatabase.close();

        difficultyGroup.setOnCheckedChangeListener(this);
        turnGroup.setOnCheckedChangeListener(this);
        sticksGroup.setOnCheckedChangeListener(this);



        btnDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                gameDatabase.open();
                gameDatabase.deleteAll();
                gameDatabase.createEntry(difficultyLevel, firstTurn, numberOfSticks);
                gameDatabase.close();

                Intent intent = new Intent(GameSettings.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch(group.getId()) {

            case R.id.rgDifficulty:

                Log.d(LOG_TAG, String.valueOf(group.getId()));
                Log.d(LOG_TAG, String.valueOf(group.getCheckedRadioButtonId()));

                difficultyLevel = group.getCheckedRadioButtonId();

                break;

            case R.id.rgFirstTurn:

                firstTurn = group.getCheckedRadioButtonId();
                break;

            case R.id.rgSticks:

                numberOfSticks = group.getCheckedRadioButtonId();
                break;
        }
    }

}
