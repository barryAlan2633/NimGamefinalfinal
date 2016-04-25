package me.arnavgarg.nimgame.Game;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;

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

    //For knowing which row we are working on
    private WorkingRow workingRow;
    //Link to all the buttons in the xml file
    private ImageButton btn11, btn21, btn22, btn31, btn32, btn33, btn41, btn42, btn43, btn44;
    private ImageButton btn51, btn52, btn53, btn54, btn55;
    //TODO: Assign then tasks!
    private ImageButton btn61, btn62, btn63, btn64, btn65, btn66;
    private ImageButton btn71, btn72, btn73, btn74, btn75, btn76, btn77;

    private ArrayList<ImageButton> row1;
    private ArrayList<ImageButton> row2;
    private ArrayList<ImageButton> row3;
    private ArrayList<ImageButton> row4;
    private ArrayList<ImageButton> row5;
    //TODO: Assign them tasks!
    private ArrayList<ImageButton> row6;
    private ArrayList<ImageButton> row7;

    private HashMap<Integer, ArrayList<ImageButton>> rowMap;


    private Button nextTurn;
    private GetData getData;
    private ArrayList<ImageButton> selectedButtons;
    private int TOTAL_SELECTIONS = 0;
    private GameDifficultyMain gameDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        workingRow = WorkingRow.NONE;
        getData = new GetData(this);
        getData.parseData();
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


        //Assigning the rows!
        row1 = new ArrayList<>();
        row2 = new ArrayList<>();
        row3 = new ArrayList<>();
        row4 = new ArrayList<>();
        row5 = new ArrayList<>();

        rowMap = new HashMap<>();

        //ArrayList of the rows!
        row1.add(btn11);
        row2.add(btn21);
        row2.add(btn22);
        row3.add(btn31);
        row3.add(btn32);
        row3.add(btn33);
        row4.add(btn41);
        row4.add(btn42);
        row4.add(btn43);
        row4.add(btn44);
        row5.add(btn51);
        row5.add(btn52);
        row5.add(btn53);
        row5.add(btn54);
        row5.add(btn55);

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

        nextTurn = (Button) findViewById(R.id.btnNextTurn);

        nextTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendingDataAI();
            }
        });

        Log.d(LOG_TAG, "[MESSAGE] " + getData.getNumberOfSticks());

        switch(getData.getNumberOfSticks()) {

            case 7:

            case 6:
            case 2131493015:
                rowMap.put(1, row1);
                rowMap.put(2, row2);
                rowMap.put(3, row3);
                rowMap.put(4, row4);
                rowMap.put(5, row5);
                TOTAL_SELECTIONS = 15;
                break;
        }

        if(getData.getDifficultyLevel() == 2131493008) gameDifficulty = new DifficultyHard();


        //backgroundTask.run();
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.ibRow1_1:
                if(!(workingRow.equals(WorkingRow.ROW1))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW1;
                }else if(selectedButtons.contains(row1.get(0))) {
                    selectedButtons.remove(row1.get(0));
                    row1.get(0).setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row1.get(0));
                row1.get(0).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow2_1:
                if(!(workingRow.equals(WorkingRow.ROW2))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW2;
                    Log.d(LOG_TAG, "" + workingRow.name());
                }else if(selectedButtons.contains(row2.get(0))){
                    selectedButtons.remove(row2.get(0));
                    row2.get(0).setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row2.get(0));
                btn21.setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow2_2:
                if(!(workingRow.equals(WorkingRow.ROW2))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW2;
                }else if(selectedButtons.contains(row2.get(1))) {
                    selectedButtons.remove(row2.get(1));
                    row2.get(1).setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row2.get(1));
                row2.get(1).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow3_1:
                if(!(workingRow.equals(WorkingRow.ROW3))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW3;
                }else if(selectedButtons.contains(row3.get(0))) {
                    selectedButtons.remove(row3.get(0));
                    row3.get(0).setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row3.get(0));
                row3.get(0).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow3_2:
                if(!(workingRow.equals(WorkingRow.ROW3))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW3;
                }else if(selectedButtons.contains(row3.get(1))) {
                    selectedButtons.remove(row3.get(1));
                    row3.get(1).setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row3.get(1));
                row3.get(1).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow3_3:
                if(!(workingRow.equals(WorkingRow.ROW3))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW3;
                }else if(selectedButtons.contains(row3.get(2))) {
                    selectedButtons.remove(row3.get(2));
                    row3.get(2).setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row3.get(2));
                row3.get(2).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow4_1:
                if(!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                }else if(selectedButtons.contains(row4.get(0))) {
                    selectedButtons.remove(row4.get(0));
                    row4.get(0).setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row4.get(0));
                row4.get(0).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow4_2:
                if(!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                }else if(selectedButtons.contains(row4.get(1))) {
                    selectedButtons.remove(row4.get(1));
                    row4.get(1).setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row4.get(1));
                row4.get(1).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow4_3:
                if(!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                }else if(selectedButtons.contains(row4.get(2))) {
                    selectedButtons.remove(row4.get(2));
                    row4.get(2).setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row4.get(2));
                row4.get(2).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow4_4:
                if(!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                }else if(selectedButtons.contains(row4.get(3))) {
                    selectedButtons.remove(row4.get(3));
                    btn44.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row4.get(3));
                row4.get(3).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow5_1:
                if(!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                }else if(selectedButtons.contains(row5.get(0))) {
                    selectedButtons.remove(row5.get(0));
                    btn51.setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row5.get(0));
                row5.get(0).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow5_2:
                if(!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                }else if(selectedButtons.contains(row5.get(1))) {
                    selectedButtons.remove(row5.get(1));
                    row5.get(1).setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row5.get(1));
                row5.get(1).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow5_3:
                if(!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                }else if(selectedButtons.contains(row5.get(2))) {
                    selectedButtons.remove(row5.get(2));
                    row5.get(2).setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row5.get(2));
                row5.get(2).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow5_4:
                if(!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                }else if(selectedButtons.contains(row5.get(3))) {
                    selectedButtons.remove(row5.get(3));
                    row5.get(3).setBackgroundColor(Color.parseColor("#b00125"));
                    break;
                }
                selectedButtons.add(row5.get(3));
                row5.get(3).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
            case R.id.ibRow5_5:
                if(!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                } else if(selectedButtons.contains(row5.get(4))) {
                    Log.d(LOG_TAG, "This is clicked again");
                    row5.get(4).setBackgroundColor(Color.parseColor("#b00125"));
                    selectedButtons.remove(row5.get(4));
                    break;
                }
                selectedButtons.add(row5.get(4));
                row5.get(4).setBackgroundColor(Color.parseColor("#5ab1ff"));
                break;
        }
    }

    public void revertPreviousSelectionRow() {

        for(ImageButton imageButton : selectedButtons) {

            imageButton.setBackgroundColor(Color.parseColor("#b00125"));
        }
        selectedButtons.clear();
    }

    public void removeSelected() {

        for(ImageButton imageButton : selectedButtons) {

            imageButton.setVisibility(View.GONE);
        }
        selectedButtons.clear();
    }

    public void sendingDataAI() {

        btn51.setVisibility(View.INVISIBLE);
        Log.d(LOG_TAG, "[MESSAGE] " + rowMap.get(5).get(0).getVisibility());

    }

    public class backgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {


            return null;
        }
    }
}
