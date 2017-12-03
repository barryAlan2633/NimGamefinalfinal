package me.arnavgarg.nimgame.Homescreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import me.arnavgarg.nimgame.Database.GameDatabase;
import me.arnavgarg.nimgame.Game.GameMain;
import me.arnavgarg.nimgame.R;
import me.arnavgarg.nimgame.settings.GameSettings;
import me.arnavgarg.nimgame.widgets.FButton;

public class MainActivity extends AppCompatActivity{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    Context mContext;
    GameDatabase gameDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


        //Font of front page

        TextView nims=(TextView)findViewById(R.id.Nims);
        Button play = (Button)findViewById(R.id.buttonPlay);
        Button settings = (Button)findViewById(R.id.buttonSettings);

        Typeface typface=Typeface.createFromAsset(getAssets(),"gameFont.ttf");
        nims.setTypeface(typface);
        play.setTypeface(typface);
        settings.setTypeface(typface);


        mContext = this;
        gameDatabase = new GameDatabase(this);

        //Default settings for the game.
        gameDatabase.open();

        if(gameDatabase.checkEmpty()) {
            gameDatabase.createEntry(2, 0, 0);
        }

        gameDatabase.close();
    }

    public void gotoGameActivity(View v){
        Intent intent = new Intent(MainActivity.this, GameMain.class);
        startActivity(intent);
    }

    public void gotoSettingsActivity(View v){
        Intent intent = new Intent(MainActivity.this, GameSettings.class);
        startActivity(intent);
    }
}
