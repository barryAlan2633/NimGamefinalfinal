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
        Log.d(LOG_TAG, "" + data);

        if(data != null) {

            String[] tokens = data.split(" ");

            difficultyLevel = Integer.parseInt(tokens[1]);
            firstTurn = Integer.parseInt(tokens[2]);
            numberOfSticks = Integer.parseInt(tokens[3]);
            Log.d(LOG_TAG, "Difficulty Level: " + difficultyLevel
                    + "\nFirst Turn: " + firstTurn
                    + "\nNumber of Sticks: " + numberOfSticks);
        }

        difficultyGroup.check(difficultyLevel);
        turnGroup.check(firstTurn);
        sticksGroup.check(numberOfSticks);

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

                Log.d(LOG_TAG, "Difficulty Level: " + difficultyLevel);
                difficultyLevel = group.getCheckedRadioButtonId();

                break;

            case R.id.rgFirstTurn:
                Log.d(LOG_TAG,"\nFirst Turn: " + firstTurn);
                firstTurn = group.getCheckedRadioButtonId();
                break;

            case R.id.rgSticks:
                Log.d(LOG_TAG, "\nNumber of Sticks: " + numberOfSticks);
                numberOfSticks = group.getCheckedRadioButtonId();
                break;
        }
    }

}
