package me.arnavgarg.nimgame.Homescreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import me.arnavgarg.nimgame.Homescreen.MainActivity;

/**
 * Created by Arnav on 3/26/2016.
 */
public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
