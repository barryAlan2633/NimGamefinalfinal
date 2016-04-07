package me.arnavgarg.nimgame.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import me.arnavgarg.nimgame.R;

/**
 * Created by Arnav on 4/6/2016.
 */
public class GameSettings extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    Button btnDone;
    RadioGroup difficultyGroup, turnGroup, sticksGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_options_activity);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch(group.getId()) {

            case 1:
                switch(checkedId) {

                }
        }
    }

    @Override
    public void onClick(View v) {

    }
}
