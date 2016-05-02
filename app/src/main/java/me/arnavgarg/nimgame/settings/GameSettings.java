package me.arnavgarg.nimgame.settings;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import me.arnavgarg.nimgame.Database.GameDatabase;
import me.arnavgarg.nimgame.Homescreen.MainActivity;
import me.arnavgarg.nimgame.R;

/**
 * Created by Arnav on 4/6/2016.
 */
public class GameSettings extends Activity implements RadioGroup.OnCheckedChangeListener{

    /**
     * Summary: Taking values from the radio boxes and storing in the SQLite database!
     */


    private static final String LOG_TAG = GameSettings.class.getSimpleName();
    Button btnDone;
    RadioGroup difficultyGroup, turnGroup, sticksGroup;
    GameDatabase gameDatabase;

    private int difficultyLevel, firstTurn, numberOfSticks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_settings);

        //FOR THE FONT
        TextView txtvw=(TextView)findViewById(R.id.gameSettings);
        Typeface typface=Typeface.createFromAsset(getAssets(),"minecraftPE.ttf");
        txtvw.setTypeface(typface);


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

        switch (difficultyLevel) {

            case 0:
                difficultyGroup.check(R.id.rDiffEasy);
                break;
            case 1:
                difficultyGroup.check(R.id.rDiffIntermediate);
                break;
            case 2:
                difficultyGroup.check(R.id.rDiffDifficult);
                break;
        }

        switch (firstTurn) {

            case 0:
                turnGroup.check(R.id.rUser);
                break;
            case 1:
                turnGroup.check(R.id.rComputer);
                break;
        }

        switch (numberOfSticks) {

            case 0:
                sticksGroup.check(R.id.rFive);
                break;
            case 1:
                sticksGroup.check(R.id.rSix);
                break;
            case 2:
                sticksGroup.check(R.id.rSeven);
                break;
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

                if(checkedId == R.id.rDiffEasy) difficultyLevel = 0;
                else if(checkedId == R.id.rDiffIntermediate) difficultyLevel = 1;
                else if(checkedId == R.id.rDiffDifficult) difficultyLevel = 2;
                break;

            case R.id.rgFirstTurn:

                if(checkedId == R.id.rUser) firstTurn = 0;
                else if(checkedId == R.id.rComputer) firstTurn = 1;
                break;

            case R.id.rgSticks:
                if(checkedId == R.id.rFive) numberOfSticks = 0;
                else if(checkedId == R.id.rSix) numberOfSticks = 1;
                else if(checkedId == R.id.rSeven) numberOfSticks = 2;
                break;
        }
    }

}
