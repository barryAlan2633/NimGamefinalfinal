package me.TheATeam.nimgame.Homescreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import me.TheATeam.nimgame.Database.NimGameDatabase;
import me.TheATeam.nimgame.Game.GameMain;
import me.TheATeam.nimgame.R;
import me.TheATeam.nimgame.settings.NimGameSettings;

public class MainActivity extends AppCompatActivity{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    Context mContext;
    NimGameDatabase nimGameDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        TextView nims=(TextView)findViewById(R.id.NimTitle);
        Button play = (Button)findViewById(R.id.playButton);
        Button settings = (Button)findViewById(R.id.settings);

        Typeface typface= Typeface.createFromAsset(getAssets(),"gameFont.ttf");
        nims.setTypeface(typface);
        play.setTypeface(typface);
        settings.setTypeface(typface);

        mContext = this;
        nimGameDB = new NimGameDatabase(this);

        //Default settings for the game.
        nimGameDB.open();

        if(nimGameDB.checkEmpty()) {
            nimGameDB.fEntry(2, 0, 0);
        }

        nimGameDB.close();
    }

    public void gotoSettingsActivity(View v){
        Intent intent = new Intent(MainActivity.this, NimGameSettings.class);
        startActivity(intent);
    }

    public void gotoGameActivity(View v){
        Intent intent = new Intent(MainActivity.this, GameMain.class);
        startActivity(intent);
    }
}
