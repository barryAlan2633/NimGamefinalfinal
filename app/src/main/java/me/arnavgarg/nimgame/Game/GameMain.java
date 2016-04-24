package me.arnavgarg.nimgame.Game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import me.arnavgarg.nimgame.Database.GetData;
import me.arnavgarg.nimgame.R;

/**
 * Created by Arnav on 4/7/2016.
 */

enum WorkingRow {
    NONE, ROW1, ROW2, ROW3, ROW4, ROW5, ROW6, ROW7
}

public class GameMain extends Activity implements View.OnClickListener{

    private static final String LOG_TAG = GameMain.class.getSimpleName();

    private WorkingRow workingRow;
    private ImageButton btn11, btn21, btn22, btn31, btn32, btn33, btn41, btn42, btn43, btn44;
    private ImageButton btn51, btn52, btn53, btn54, btn55;
    private ImageButton btn61, btn62, btn63, btn64, btn65, btn66;
    private ImageButton btn71, btn72, btn73, btn74, btn75, btn76, btn77;

    private GetData getData;
    private ArrayList<ImageButton> selectedButtons;
    private int TOTAL_SELECTIONS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        workingRow = WorkingRow.NONE;

        getData = new GetData(this);

        //Row 1
        btn11 = (ImageButton) findViewById(R.id.ibRow1_1);
        //Row 2
        btn21 = (ImageButton) findViewById(R.id.ibRow2_1);
        btn22 = (ImageButton) findViewById(R.id.ibRow2_2);
        //Row 3
        btn31 = (ImageButton) findViewById(R.id.ibRow3_1);
        btn32 = (ImageButton) findViewById(R.id.ibRow3_2);
        btn33 = (ImageButton) findViewById(R.id.ibRow3_3);
        //Row 4
        btn41 = (ImageButton) findViewById(R.id.ibRow4_1);
        btn42 = (ImageButton) findViewById(R.id.ibRow4_2);
        btn43 = (ImageButton) findViewById(R.id.ibRow4_3);
        btn44 = (ImageButton) findViewById(R.id.ibRow4_4);
        //Row 5
        btn51 = (ImageButton) findViewById(R.id.ibRow5_1);
        btn52 = (ImageButton) findViewById(R.id.ibRow5_2);
        btn53 = (ImageButton) findViewById(R.id.ibRow5_3);
        btn54 = (ImageButton) findViewById(R.id.ibRow5_4);
        btn55 = (ImageButton) findViewById(R.id.ibRow5_5);

        //setting up onclicklisteners for all the buttons.
        btn11.setOnClickListener(this);
        btn21.setOnClickListener(this);
        btn22.setOnClickListener(this);
        btn31.setOnClickListener(this);
        btn32.setOnClickListener(this);
        btn33.setOnClickListener(this);
        btn41.setOnClickListener(this);
        btn42.setOnClickListener(this);
        btn43.setOnClickListener(this);
        btn44.setOnClickListener(this);
        btn51.setOnClickListener(this);
        btn52.setOnClickListener(this);
        btn53.setOnClickListener(this);
        btn54.setOnClickListener(this);
        btn55.setOnClickListener(this);


        switch(getData.getNumberOfSticks()) {

            case 7:
                break;
            case 6:
                break;
            case 5:
                TOTAL_SELECTIONS = 15;
                break;
        }
//        //Row 6
//        btn61 = (ImageButton) findViewById(R.id.ibRow1_1);
//        btn62 = (ImageButton) findViewById(R.id.ibRow1_1);
//        btn63 = (ImageButton) findViewById(R.id.ibRow1_1);
//        btn64 = (ImageButton) findViewById(R.id.ibRow1_1);
//        btn65 = (ImageButton) findViewById(R.id.ibRow1_1);
//        btn66 = (ImageButton) findViewById(R.id.ibRow1_1);
//        //Row 7
//        btn71 = (ImageButton) findViewById(R.id.ibRow1_1);
//        btn72 = (ImageButton) findViewById(R.id.ibRow1_1);
//        btn73 = (ImageButton) findViewById(R.id.ibRow1_1);
//        btn74 = (ImageButton) findViewById(R.id.ibRow1_1);
//        btn75 = (ImageButton) findViewById(R.id.ibRow1_1);
//        btn76 = (ImageButton) findViewById(R.id.ibRow1_1);
//        btn77 = (ImageButton) findViewById(R.id.ibRow1_1);
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.ibRow1_1:
                if(workingRow.equals(WorkingRow.ROW1)) {
                    //TODO
                }
                break;
            case R.id.ibRow2_1:
                if(workingRow.equals(WorkingRow.ROW2)) {
                    //TODO
                }
                break;
            case R.id.ibRow2_2:
                break;
            case R.id.ibRow3_1:
                break;
            case R.id.ibRow3_2:
                break;
            case R.id.ibRow3_3:
                break;
            case R.id.ibRow4_1:
                break;
            case R.id.ibRow4_2:
                break;
            case R.id.ibRow4_3:
                break;
            case R.id.ibRow4_4:
                break;
            case R.id.ibRow5_1:
                break;
            case R.id.ibRow5_2:
                break;
            case R.id.ibRow5_3:
                break;
            case R.id.ibRow5_4:
                break;
            case R.id.ibRow5_5:
                break;
        }

    }

    public void makeVisible(ArrayList<ImageButton> imageButtons) {
        //TODO

    }
}
