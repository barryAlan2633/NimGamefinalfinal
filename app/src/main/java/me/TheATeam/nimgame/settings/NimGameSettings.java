package me.TheATeam.nimgame.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import me.TheATeam.nimgame.Database.NimGameDatabase;
import me.TheATeam.nimgame.Homescreen.MainActivity;
import me.TheATeam.nimgame.R;

public class NimGameSettings extends Activity implements RadioGroup.OnCheckedChangeListener{

    private static final String LOG_TAG = NimGameSettings.class.getSimpleName();
    Button bDone;
    RadioGroup levelGroup, turns, groupOfGirs;
    NimGameDatabase gameDatabase;

    private int difficultyLevel, firstTurn, numberOfGirs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_settings);

        //FOR THE FONT
        TextView Settings=(TextView)findViewById(R.id.gameSettings);
        TextView Difficulty=(TextView)findViewById(R.id.textViewDiff);
        TextView FirstTurn=(TextView)findViewById(R.id.textViewFT);
        TextView numberOfGir =(TextView)findViewById(R.id.textViewNum);
        RadioButton BtnEasy = (RadioButton) findViewById(R.id.easyLevel);
        RadioButton BtnIntermediate = (RadioButton) findViewById(R.id.IntermLevel);
        RadioButton BtnDifficult = (RadioButton) findViewById(R.id.DiffcultLevel);
        RadioButton BtnUser = (RadioButton) findViewById(R.id.User1);
        RadioButton BtnComputer = (RadioButton) findViewById(R.id.Computer1);
        RadioButton BtnRadio5 = (RadioButton) findViewById(R.id.rbFive);
        RadioButton BtnRadio6 = (RadioButton) findViewById(R.id.rbSix);
        RadioButton BtnRadio7 = (RadioButton) findViewById(R.id.rbSeven);
        Button done = (Button)findViewById(R.id.btnDone);

/*        Typeface typface=Typeface.createFromAsset(getAssets(),"gameFont.ttf");
        txtvwSettings.setTypeface(typface);
        txtvwDifficulty.setTypeface(typface);
        txtvwFirsTurn.setTypeface(typface);
        txtvwnumberOfSticks.setTypeface(typface);
        rdBtnEasy.setTypeface(typface);
        rdBtnIntermediate.setTypeface(typface);
        rdBtnDifficult.setTypeface(typface);
        rdBtnUser.setTypeface(typface);
        rdBtnComputer.setTypeface(typface);
        rdBtn5.setTypeface(typface);
        rdBtn6.setTypeface(typface);
        rdBtn7.setTypeface(typface);
        done.setTypeface(typface);*/



        bDone = (Button) findViewById(R.id.btnDone);
        levelGroup = (RadioGroup) findViewById(R.id.rgDifficulty);
        turns = (RadioGroup) findViewById(R.id.rgFirstTurn);
        groupOfGirs = (RadioGroup) findViewById(R.id.rbGir);
        gameDatabase = new NimGameDatabase(this);

        gameDatabase.open();

        String data = gameDatabase.getData();
        Log.d(LOG_TAG, "" + data);

        if(data != null) {

            String[] tokens = data.split(" ");

            difficultyLevel = Integer.parseInt(tokens[1]);
            firstTurn = Integer.parseInt(tokens[2]);
            numberOfGirs = Integer.parseInt(tokens[3]);
            /*Log.d(LOG_TAG, "Difficulty Level: " + difficultyLevel
                    + "\nFirst Turn: " + firstTurn
                    + "\nNumber of Girs: " + numberOfGirs);*/
        }

        switch (difficultyLevel) {

            case 0:
                levelGroup.check(R.id.easyLevel);
                break;
            case 1:
                levelGroup.check(R.id.IntermLevel);
                break;
            case 2:
                levelGroup.check(R.id.DiffcultLevel);
                break;
        }

        switch (firstTurn) {

            case 0:
                turns.check(R.id.User1);
                break;
            case 1:
                turns.check(R.id.Computer1);
                break;
        }

        switch (numberOfGirs) {

            case 0:
                groupOfGirs.check(R.id.rbFive);
                break;
            case 1:
                groupOfGirs.check(R.id.rbSix);
                break;
            case 2:
                groupOfGirs.check(R.id.rbSeven);
                break;
        }

        gameDatabase.close();


        levelGroup.setOnCheckedChangeListener(this);
        turns.setOnCheckedChangeListener(this);
        groupOfGirs.setOnCheckedChangeListener(this);

        bDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                gameDatabase.open();
                gameDatabase.deleteUser();
                gameDatabase.fEntry(difficultyLevel, firstTurn, numberOfGirs);
                gameDatabase.close();

                Intent intent = new Intent(NimGameSettings.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch(group.getId()) {

            case R.id.rgDifficulty:

                if(checkedId == R.id.easyLevel) difficultyLevel = 0;
                else if(checkedId == R.id.IntermLevel) difficultyLevel = 1;
                else if(checkedId == R.id.DiffcultLevel) difficultyLevel = 2;
                break;

            case R.id.rgFirstTurn:

                if(checkedId == R.id.User1) firstTurn = 0;
                else if(checkedId == R.id.Computer1) firstTurn = 1;
                break;

            case R.id.rbGir:
                if(checkedId == R.id.rbFive) numberOfGirs = 0;
                else if(checkedId == R.id.rbSix) numberOfGirs = 1;
                else if(checkedId == R.id.rbSeven) numberOfGirs = 2;
                break;
        }
    }

}
