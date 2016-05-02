package me.arnavgarg.nimgame.Homescreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import me.arnavgarg.nimgame.Database.GameDatabase;
import me.arnavgarg.nimgame.Game.GameMain;
import me.arnavgarg.nimgame.Game.GamePVP;
import me.arnavgarg.nimgame.R;
import me.arnavgarg.nimgame.settings.GameSettings;
import me.arnavgarg.nimgame.settings.HowToPlay;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    BoomMenuButton boomMenuButton;
    Button buttonPlay, buttonPVP;
    Context mContext;
    GameDatabase gameDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPVP = (Button) findViewById(R.id.buttonPVP);
        mContext = this;
        boomMenuButton = (BoomMenuButton) findViewById(R.id.boom);
        buttonPlay.setOnClickListener(this);
        buttonPVP.setOnClickListener(this);
        gameDatabase = new GameDatabase(this);

        //Default settings for the game.
        gameDatabase.open();

        if(gameDatabase.checkEmpty()) {
            gameDatabase.createEntry(2, 0, 0);
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
            case R.id.buttonPVP:
                intent = new Intent(MainActivity.this, GamePVP.class);
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

            Drawable[] drawables = new Drawable[2];
            int[] drawablesResource = new int[]{
                    R.drawable.settings,
                    R.drawable.howtoplay,
            };
            for (int i = 0; i < 2; i++)
                drawables[i] = ContextCompat.getDrawable(mContext, drawablesResource[i]);

            int[][] colors = new int[2][2];
            for (int i = 0; i < 2; i++) {
                colors[i][1] = ContextCompat.getColor(mContext, R.color.colorPrimary);
                colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
            }

            String[] text = {
                    "Settings",
                    "How To Play",
                    ""
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

            boomMenuButton.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
                @Override
                public void onClick(int buttonIndex) {

                    Intent intent = null;
                    switch (buttonIndex) {

                        case 0:
                            intent = new Intent(MainActivity.this, GameSettings.class);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(MainActivity.this, HowToPlay.class);
                            startActivity(intent);
                            break;
                    }
                }
            });
        }

    }
}
