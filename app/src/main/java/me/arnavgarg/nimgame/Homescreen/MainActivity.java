package me.arnavgarg.nimgame.Homescreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import me.arnavgarg.nimgame.Database.GameDatabase;
import me.arnavgarg.nimgame.Game.GameMain;
import me.arnavgarg.nimgame.R;
import me.arnavgarg.nimgame.settings.GameSettings;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    BoomMenuButton boomMenuButton;
    Button buttonPlay, buttonGameOptions;
    Context mContext;
    GameDatabase gameDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FOR THE FONT
        TextView txtvw=(TextView)findViewById(R.id.Nims);
        Typeface typface=Typeface.createFromAsset(getAssets(),"minecraftPE.ttf");
        txtvw.setTypeface(typface);

        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonGameOptions = (Button) findViewById(R.id.buttonGameOptions);
        mContext = this;
        boomMenuButton = (BoomMenuButton) findViewById(R.id.boom);
        buttonPlay.setOnClickListener(this);
        buttonGameOptions.setOnClickListener(this);
        gameDatabase = new GameDatabase(this);

        //Default settings for the game.
        gameDatabase.open();

//        gameDatabase.deleteAll();
        if(gameDatabase.checkEmpty()) {
            gameDatabase.createEntry(2131493009,2131493012,2131493016);
        }

        gameDatabase.close();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        BoomMenuThead boomMenuThead = new BoomMenuThead();
        boomMenuThead.run();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;
        switch(v.getId()) {

            case R.id.buttonPlay:
                intent = new Intent(MainActivity.this, GameMain.class);
                startActivity(intent);
                break;
            case R.id.buttonGameOptions:
                intent = new Intent(MainActivity.this, GameSettings.class);
                startActivity(intent);
                break;
        }
    }

    private class BoomMenuThead implements Runnable {

        @Override
        public void run() {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("MainActivityLog", "Working");

            Drawable[] drawables = new Drawable[3];
            int[] drawablesResource = new int[]{
                    R.drawable.ic_launcher,
                    R.drawable.ic_launcher,
                    R.drawable.ic_launcher
            };
            for (int i = 0; i < 3; i++)
                drawables[i] = ContextCompat.getDrawable(mContext, drawablesResource[i]);

            int[][] colors = new int[3][2];
            for (int i = 0; i < 3; i++) {
                colors[i][1] = ContextCompat.getColor(mContext, R.color.colorPrimary);
                colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
            }

            String[] text = {
                    "Settings",
                    "How To Play",
                    "About The Developer"
            };

            boomMenuButton.init(
                    drawables,          // The drawables of images of sub buttons. Can not be null.
                    text,               // The texts of sub buttons, ok to be null.
                    colors,             // The colors of sub buttons, including pressed-state and normal-state.
                    ButtonType.HAM,     // The button type.
                    BoomType.PARABOLA,  // The boom type.
                    PlaceType.HAM_3_1,  // The place type.
                    null,               // Ease type to move the sub buttons when showing.
                    null,               // Ease type to scale the sub buttons when showing.
                    null,               // Ease type to rotate the sub buttons when showing.
                    null,               // Ease type to move the sub buttons when dismissing.
                    null,               // Ease type to scale the sub buttons when dismissing.
                    null,               // Ease type to rotate the sub buttons when dismissing.
                    null                // Rotation degree.
            );
        }

        public void pause() {

            //TODO
        }
    }
}
