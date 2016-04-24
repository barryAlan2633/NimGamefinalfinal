package me.arnavgarg.nimgame.Game;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
        selectedButtons = new ArrayList<>();

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
                if(!(workingRow.equals(WorkingRow.ROW1))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW1;
                }else if(selectedButtons.contains(btn11)) {
                    selectedButtons.remove(btn11);
                    btn11.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn11);
                btn11.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow2_1:
                if(!(workingRow.equals(WorkingRow.ROW2))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW2;
                    Log.d(LOG_TAG, "" + workingRow.name());
                }else if(selectedButtons.contains(btn21)) {
                    selectedButtons.remove(btn21);
                    btn21.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn21);
                btn21.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow2_2:
                if(!(workingRow.equals(WorkingRow.ROW2))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW2;
                }else if(selectedButtons.contains(btn22)) {
                    selectedButtons.remove(btn22);
                    btn22.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn22);
                btn22.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow3_1:
                if(!(workingRow.equals(WorkingRow.ROW3))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW3;
                }else if(selectedButtons.contains(btn31)) {
                    selectedButtons.remove(btn31);
                    btn31.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn31);
                btn31.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow3_2:
                if(!(workingRow.equals(WorkingRow.ROW3))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW3;
                }else if(selectedButtons.contains(btn32)) {
                    selectedButtons.remove(btn32);
                    btn32.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn32);
                btn32.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow3_3:
                if(!(workingRow.equals(WorkingRow.ROW3))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW3;
                }else if(selectedButtons.contains(btn33)) {
                    selectedButtons.remove(btn33);
                    btn33.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn33);
                btn33.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow4_1:
                if(!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                }else if(selectedButtons.contains(btn41)) {
                    selectedButtons.remove(btn41);
                    btn41.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn41);
                btn41.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow4_2:
                if(!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                }else if(selectedButtons.contains(btn42)) {
                    selectedButtons.remove(btn42);
                    btn42.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn42);
                btn42.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow4_3:
                if(!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                }else if(selectedButtons.contains(btn43)) {
                    selectedButtons.remove(btn43);
                    btn43.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn43);
                btn43.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow4_4:
                if(!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                }else if(selectedButtons.contains(btn44)) {
                    selectedButtons.remove(btn44);
                    btn44.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn44);
                btn44.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow5_1:
                if(!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                }else if(selectedButtons.contains(btn51)) {
                    selectedButtons.remove(btn51);
                    btn51.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn51);
                btn51.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow5_2:
                if(!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                }else if(selectedButtons.contains(btn52)) {
                    selectedButtons.remove(btn52);
                    btn52.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn52);
                btn52.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow5_3:
                if(!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                }else if(selectedButtons.contains(btn53)) {
                    selectedButtons.remove(btn53);
                    btn53.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn53);
                btn53.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow5_4:
                if(!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                }else if(selectedButtons.contains(btn54)) {
                    selectedButtons.remove(btn54);
                    btn54.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(btn54);
                btn54.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow5_5:
                if(!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                } else if(selectedButtons.contains(btn55)) {
                    Log.d(LOG_TAG, "This is clicked again");
                    btn55.setBackgroundColor(Color.parseColor("#b00125"));
                    selectedButtons.remove(btn55);
                    break;
                }
                selectedButtons.add(btn55);
                btn55.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
        }
    }

    public void revertPreviousSelectionRow() {

        for(ImageButton imageButton : selectedButtons) {

            imageButton.setBackgroundColor(Color.parseColor("#b00125"));
        }
        selectedButtons.clear();
    }
}
