package me.arnavgarg.nimgame.settings;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import me.arnavgarg.nimgame.R;

/**
 * Created by Arnav on 5/1/2016.
 */
public class HowToPlay extends Activity {

    TextView title, paragrapgh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Making the thing full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.howtoplay);

        title = (TextView) findViewById(R.id.tvtitle);
        paragrapgh = (TextView) findViewById(R.id.tvText);

        //Now I'll just add the font to the text!
        Typeface typface=Typeface.createFromAsset(getAssets(),"minecraftPE.ttf");
        title.setTypeface(typface);
        //paragrapgh.setTypeface(typface);

    }
}
