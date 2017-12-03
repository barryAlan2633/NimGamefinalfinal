package me.TheATeam.nimgame.Game;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

import info.hoang8f.widget.FButton;
import me.TheATeam.nimgame.Database.GetData;
import me.TheATeam.nimgame.Homescreen.MainActivity;
import me.TheATeam.nimgame.R;
import pl.droidsonroids.gif.GifImageButton;


enum WorkingRow {
    NONE, ROW1, ROW2, ROW3, ROW4, ROW5, ROW6, ROW7
}

public class GameMain extends Activity implements View.OnClickListener, Runnable {


    private static final String LOG_TAG = GameMain.class.getSimpleName();


    private WorkingRow workingRow;
    private TextView tvPlayerTurn;
    private TextView tvComputerTurn;

    private ArrayList<GifImageButton> imageButtons;

    private boolean playerTurn;
    private FButton nextTurn;

    //Getting the data from the database.
    private GetData getData;

    //Buttons that have been selected by the user.
    private ArrayList<GifImageButton> buttonsOnSelect;

    //Total selections LEFT!
    private int TOTAL_SELECTIONS = 15;


    private GameDifficultyMain gameLevelOfDifficulty;

    //time trackerrr
    private Chronometer chronometer;

    //high scores
    private SharedPreferences sharedPreferences;
    private String max = "No High Score";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Making it full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);


        //Font of game page
       // Button select = (Button)findViewById(R.id.btnNextTurn);

       // Typeface typface=Typeface.createFromAsset(getAssets(),"gameFont.ttf");

        //select.setTypeface(typface);



        //Initializing DB
        getData = new GetData(this);
        getData.parseData();


        initialize();
        makeRowsVisible();
        settingOnClickListeners();

        //setting the font type..needs to be done after initialization
       // tvPlayerTurn.setTypeface(typface);
       // tvComputerTurn.setTypeface(typface);

        //Time
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        //stores high score values
        sharedPreferences = getSharedPreferences("highscore", this.MODE_PRIVATE);
        max = sharedPreferences.getString("hs", null);
        if (max == null) {
            max = "No High Score";
        }

        Thread myThread = new Thread(this);
        myThread.start();

    }

    public void revertPreviousSelectionRow() {

        for (GifImageButton imageButton : buttonsOnSelect) {

            imageButton.setBackgroundResource(R.drawable.gir);
        }
        buttonsOnSelect.clear();
    }

   //removes buttons that were selected by user
    public void removeSelected() {

        TOTAL_SELECTIONS -= buttonsOnSelect.size();

        for (final GifImageButton imageButton : buttonsOnSelect) {

            imageButton.setBackgroundResource(R.drawable.burngir);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    imageButton.setVisibility(View.INVISIBLE);
                }
            }, 2000);
        }
        buttonsOnSelect.clear();
    }

    //makes rows visible to user
    public void makeRowsVisible() {

        switch (getData.getGirNumber()) {

            case 0:
                TOTAL_SELECTIONS = 15;
                for (int i = 0; i < 15; i++) {
                    imageButtons.get(i).setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                TOTAL_SELECTIONS = 21;
                for (int i = 0; i < 21; i++) {
                    imageButtons.get(i).setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                TOTAL_SELECTIONS = 28;
                for (int i = 0; i < 28; i++) {
                    imageButtons.get(i).setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }


    public void computerAI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nextTurn.setButtonColor(Color.GRAY);
                nextTurn.setShadowColor(Color.BLACK);
                nextTurn.setClickable(false);
            }
        });

        if (visibleButtons() == 0) {
            return;
        }


        int[] a = new int[]{0, 0, 0, 0, 0, 0, 0};
        int rowIncrement = 0;

        for (int i = 0; i < 7; i++) {
            int j = i + 1;
            while (j != 0) {

                if (imageButtons.get(rowIncrement).getVisibility() == View.VISIBLE) {
                    a[i] += 1;
                }

                rowIncrement += 1;
                j -= 1;
            }
        }

        rowIncrement = 0;

        int[] returnValues;
        returnValues = gameLevelOfDifficulty.compTurn(a);
        for (int i = 0; i < a.length; i++) {

            int j = i + 1;
            rowIncrement += i;
            if (i == returnValues[0]) {
                while(j != 0 && returnValues[1] != 0) {
                    if(imageButtons.get(rowIncrement).getVisibility() == View.VISIBLE) {
                        buttonsOnSelect.add(imageButtons.get(rowIncrement));
                        returnValues[1]--;
                    }
                    rowIncrement++;
                    j--;
                }
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                removeSelected();
            }
        });

        playerTurn = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nextTurn.setShadowColor(Color.BLACK);
                nextTurn.setButtonColor(Color.GRAY);
                nextTurn.setClickable(false);


            }
        });
    }
    //stops game
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    //what buttons are visible on the screen
    public int visibleButtons() {

        int sum = 0;
        for (GifImageButton gifImageButton : imageButtons) {

            if (gifImageButton.getVisibility() == View.VISIBLE) sum++;
        }
        return sum;
    }

    public void disableButtons() {

        for (GifImageButton imageButton : imageButtons) {

            imageButton.setClickable(false);
        }
    }

    public void enableButton() {

        for (GifImageButton imageButton : imageButtons) {

            imageButton.setClickable(true);
        }
    }

    @Override
    public void run() {


        while (true) {


            if (visibleButtons() == 0) {
                chronometer.stop();
                break;
            }
            if (playerTurn) {
                enableButton();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvPlayerTurn.setTextColor(Color.RED);
                        tvComputerTurn.setTextColor(Color.GRAY);
                        enableButton();
                        nextTurn.setButtonColor(getResources().getColor(R.color.fbutton_color_green));
                        nextTurn.setShadowColor(getResources().getColor(R.color.fbutton_color_greenShadow));
                        nextTurn.setClickable(true);
                    }
                });
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nextTurn.setButtonColor(Color.GRAY);
                        nextTurn.setShadowColor(Color.BLACK);
                        nextTurn.setClickable(false);
                    }
                });
                disableButtons();
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                computerAI();
            }
        }

        //dialog button
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nextTurn.setVisibility(View.INVISIBLE);
                if (!(GameMain.this).isFinishing()) {
                    Dialog resultDialog = new Dialog(GameMain.this);
                    resultDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    resultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    resultDialog.setContentView(R.layout.result_dialog);

                    resultDialog.setCanceledOnTouchOutside(false);
                    TextView userScore = (TextView) resultDialog.findViewById(R.id.ustext);
                    TextView highScore = (TextView) resultDialog.findViewById(R.id.hstext);
                    TextView resultTitle = (TextView) resultDialog.findViewById(R.id.resultTitle);
                    ImageView resultGif = (ImageView) resultDialog.findViewById(R.id.resultGif);

                    /*Typeface typface=Typeface.createFromAsset(getAssets(),"gameFont.ttf");
                    userScore.setTypeface(typface);
                    highScore.setTypeface(typface);*/

                    if (playerTurn) {
                        userScore.setVisibility(View.INVISIBLE);
                        resultGif.setVisibility(View.VISIBLE);
                        resultTitle.setText("GAME OVER \n Computer won");
                        highScore.setText("HIGHSCORE: " + max);

                        //resultTitle.setTypeface(typface);
                    } else {
                        resultTitle.setText("GAME OVER \n You won!");
                        resultGif.setVisibility(View.GONE);

                        try {
                            if (checkUserTime((String) chronometer.getText(), max)) {
                                max = (String) chronometer.getText();
                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                edit.putString("hs", max);
                                edit.commit();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        highScore.setText("HIGHSCORE: " + max);
                        userScore.setText("SCORE: " + chronometer.getText());

                    }

                    Button exitGame = (Button) resultDialog.findViewById(R.id.btnExit);
                    Button wouldYouLikeToPlayAgain = (Button) resultDialog.findViewById(R.id.btnPlayAgain);

                    /*wouldYouLikeToPlayAgain.setTypeface(typface);
                    exitGame.setTypeface(typface);
                    resultTitle.setTypeface(typface);*/

                    exitGame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(GameMain.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                    wouldYouLikeToPlayAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(GameMain.this, GameMain.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                    resultDialog.show();
                }
            }
        });

    }


    public boolean checkUserTime(String userTime, String hsTime) throws ParseException {
        if (hsTime == "No High Score") {
            return true;
        }

        String userSeconds = userTime.substring(3);
        String userMinutes = userTime.substring(0, 2);

        String hsSeconds = hsTime.substring(3);
        String hsMinutes = hsTime.substring(0, 2);

        if (Integer.parseInt(hsMinutes) < Integer.parseInt(userMinutes)) {
            return false;
        }
        else if(Integer.parseInt(hsMinutes) > Integer.parseInt(userMinutes)){
            return true;
        }
        else if(Integer.parseInt(hsMinutes) == Integer.parseInt(userMinutes)){

            if (Integer.parseInt(hsSeconds) < Integer.parseInt(userSeconds)) {
                return false;

            }
            else if(Integer.parseInt(hsSeconds) > Integer.parseInt(userSeconds)){
                return true;
            }
            else if(Integer.parseInt(hsSeconds) == Integer.parseInt(userSeconds)){
                return true;
            }
        }
        return true;
    }

    public void settingOnClickListeners() {

        int width, height;
//expands girs on android screens
        if (TOTAL_SELECTIONS == 15) {
            width = 200;
            height = 250;
        } else if (TOTAL_SELECTIONS == 21) {
            width = 170;
            height = 220;
        } else {
            width = 140;
            height = 190;
        }

        nextTurn.setOnClickListener(this);

        for (int i = 0; i < TOTAL_SELECTIONS; i++) {
            imageButtons.get(i).setLayoutParams(new LinearLayout.LayoutParams(width, height));
            imageButtons.get(i).setOnClickListener(this);
        }
    }


    public void initialize() {
        workingRow = WorkingRow.NONE;
        buttonsOnSelect = new ArrayList<>();
        tvPlayerTurn = (TextView) findViewById(R.id.tvPlayer);
        tvComputerTurn = (TextView) findViewById(R.id.tvComputer);
        nextTurn = (FButton) findViewById(R.id.btnNextTurn);


        tvPlayerTurn.setText("USER");
        tvComputerTurn.setText("AI");

        switch (getData.getFirstTurn()) {

            case 0:
                playerTurn = true;
                break;
            case 1:
                playerTurn = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nextTurn.setButtonColor(Color.GRAY);
                        nextTurn.setShadowColor(Color.BLACK);
                        nextTurn.setClickable(false);
                    }
                });
                tvPlayerTurn.setTextColor(Color.GRAY);
                tvComputerTurn.setTextColor(Color.BLUE);
                break;
        }

        //determines lvl of difficulty
        switch (getData.getDifficultyLevel()) {

            case 0:
                gameLevelOfDifficulty = new EasyLevel();
                break;
            case 1:
                gameLevelOfDifficulty = new Intermediate();
                break;
            case 2:
                gameLevelOfDifficulty = new HardLevel();
                break;
        }


        imageButtons = new ArrayList<GifImageButton>();
        imageButtons.add((GifImageButton) findViewById(R.id.girrow1_1));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow2_1));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow2_2));
        imageButtons.add((GifImageButton) findViewById(R.id.gir3_1));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow3_2));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow3_3));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow4_1));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow4_2));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow4_3));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow4_4));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow5_1));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow5_2));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow5_3));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow5_4));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow5_5));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow6_1));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow6_2));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow6_3));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow6_4));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow6_5));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow6_6));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow7_1));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow7_2));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow7_3));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow7_4));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow7_5));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow7_6));
        imageButtons.add((GifImageButton) findViewById(R.id.girrow7_7));

        for (GifImageButton imageButton : imageButtons) {

            imageButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnNextTurn:

                if(buttonsOnSelect.size() != 0) {
                    removeSelected();
                    disableButtons();
                    playerTurn = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nextTurn.setButtonColor(Color.GRAY);
                            nextTurn.setShadowColor(Color.BLACK);
                            nextTurn.setClickable(false);
                        }
                    });
                    tvPlayerTurn.setTextColor(Color.GRAY);
                    tvComputerTurn.setTextColor(Color.RED);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(this,"Please select a piece",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.girrow1_1:
                if (!(workingRow.equals(WorkingRow.ROW1))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW1;
                } else if (buttonsOnSelect.contains(imageButtons.get(0))) {
                    buttonsOnSelect.remove(imageButtons.get(0));
                    imageButtons.get(0).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(0));
                imageButtons.get(0).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow2_1:
                if (!(workingRow.equals(WorkingRow.ROW2))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW2;
                } else if (buttonsOnSelect.contains(imageButtons.get(1))) {
                    buttonsOnSelect.remove(imageButtons.get(1));
                    imageButtons.get(1).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(1));
                imageButtons.get(1).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow2_2:
                if (!(workingRow.equals(WorkingRow.ROW2))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW2;
                } else if (buttonsOnSelect.contains(imageButtons.get(2))) {
                    buttonsOnSelect.remove(imageButtons.get(2));
                    imageButtons.get(2).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(2));
                imageButtons.get(2).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.gir3_1:
                if (!(workingRow.equals(WorkingRow.ROW3))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW3;
                } else if (buttonsOnSelect.contains(imageButtons.get(3))) {
                    buttonsOnSelect.remove(imageButtons.get(3));
                    imageButtons.get(3).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(3));
                imageButtons.get(3).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow3_2:
                if (!(workingRow.equals(WorkingRow.ROW3))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW3;

                } else if (buttonsOnSelect.contains(imageButtons.get(4))) {
                    buttonsOnSelect.remove(imageButtons.get(4));
                    imageButtons.get(4).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(4));
                imageButtons.get(4).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow3_3:
                if (!(workingRow.equals(WorkingRow.ROW3))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW3;
                } else if (buttonsOnSelect.contains(imageButtons.get(5))) {
                    buttonsOnSelect.remove(imageButtons.get(5));
                    imageButtons.get(5).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(5));
                imageButtons.get(5).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow4_1:
                if (!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                } else if (buttonsOnSelect.contains(imageButtons.get(6))) {
                    buttonsOnSelect.remove(imageButtons.get(6));
                    imageButtons.get(6).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(6));
                imageButtons.get(6).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow4_2:
                if (!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                } else if (buttonsOnSelect.contains(imageButtons.get(7))) {
                    buttonsOnSelect.remove(imageButtons.get(7));
                    imageButtons.get(7).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(7));
                imageButtons.get(7).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow4_3:
                if (!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                } else if (buttonsOnSelect.contains(imageButtons.get(8))) {
                    buttonsOnSelect.remove(imageButtons.get(8));
                    imageButtons.get(8).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(8));
                imageButtons.get(8).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow4_4:
                if (!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                } else if (buttonsOnSelect.contains(imageButtons.get(9))) {
                    buttonsOnSelect.remove(imageButtons.get(9));
                    imageButtons.get(9).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(9));
                imageButtons.get(9).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow5_1:
                if (!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                } else if (buttonsOnSelect.contains(imageButtons.get(10))) {
                    buttonsOnSelect.remove(imageButtons.get(10));
                    imageButtons.get(10).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(10));
                imageButtons.get(10).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow5_2:
                if (!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                } else if (buttonsOnSelect.contains(imageButtons.get(11))) {
                    buttonsOnSelect.remove(imageButtons.get(11));
                    imageButtons.get(11).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(11));
                imageButtons.get(11).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow5_3:
                if (!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                } else if (buttonsOnSelect.contains(imageButtons.get(12))) {
                    buttonsOnSelect.remove(imageButtons.get(12));
                    imageButtons.get(12).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(12));
                imageButtons.get(12).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow5_4:
                if (!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                } else if (buttonsOnSelect.contains(imageButtons.get(13))) {
                    buttonsOnSelect.remove(imageButtons.get(13));
                    imageButtons.get(13).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(13));
                imageButtons.get(13).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow5_5:
                if (!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                } else if (buttonsOnSelect.contains(imageButtons.get(14))) {
                    buttonsOnSelect.remove(imageButtons.get(14));
                    imageButtons.get(14).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(14));
                imageButtons.get(14).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow6_1:
                if (!(workingRow.equals(WorkingRow.ROW6))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW6;
                } else if (buttonsOnSelect.contains(imageButtons.get(15))) {
                    buttonsOnSelect.remove(imageButtons.get(15));
                    imageButtons.get(15).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(15));
                imageButtons.get(15).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow6_2:
                if (!(workingRow.equals(WorkingRow.ROW6))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW6;
                } else if (buttonsOnSelect.contains(imageButtons.get(16))) {
                    buttonsOnSelect.remove(imageButtons.get(16));
                    imageButtons.get(16).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(16));
                imageButtons.get(16).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow6_3:
                if (!(workingRow.equals(WorkingRow.ROW6))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW6;
                } else if (buttonsOnSelect.contains(imageButtons.get(17))) {
                    buttonsOnSelect.remove(imageButtons.get(17));
                    imageButtons.get(17).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(17));
                imageButtons.get(17).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow6_4:
                if (!(workingRow.equals(WorkingRow.ROW6))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW6;
                } else if (buttonsOnSelect.contains(imageButtons.get(18))) {
                    buttonsOnSelect.remove(imageButtons.get(18));
                    imageButtons.get(18).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(18));
                imageButtons.get(18).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow6_5:
                if (!(workingRow.equals(WorkingRow.ROW6))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW6;
                } else if (buttonsOnSelect.contains(imageButtons.get(19))) {
                    buttonsOnSelect.remove(imageButtons.get(19));
                    imageButtons.get(19).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(19));
                imageButtons.get(19).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow6_6:
                if (!(workingRow.equals(WorkingRow.ROW6))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW6;
                } else if (buttonsOnSelect.contains(imageButtons.get(20))) {
                    buttonsOnSelect.remove(imageButtons.get(20));
                    imageButtons.get(20).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(20));
                imageButtons.get(20).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow7_1:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (buttonsOnSelect.contains(imageButtons.get(21))) {
                    buttonsOnSelect.remove(imageButtons.get(21));
                    imageButtons.get(21).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(21));
                imageButtons.get(21).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow7_2:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (buttonsOnSelect.contains(imageButtons.get(22))) {
                    buttonsOnSelect.remove(imageButtons.get(22));
                    imageButtons.get(22).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(22));
                imageButtons.get(22).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow7_3:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (buttonsOnSelect.contains(imageButtons.get(23))) {
                    buttonsOnSelect.remove(imageButtons.get(23));
                    imageButtons.get(23).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(23));
                imageButtons.get(23).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow7_4:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (buttonsOnSelect.contains(imageButtons.get(24))) {
                    buttonsOnSelect.remove(imageButtons.get(24));
                    imageButtons.get(24).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(24));
                imageButtons.get(24).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow7_5:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (buttonsOnSelect.contains(imageButtons.get(25))) {
                    buttonsOnSelect.remove(imageButtons.get(25));
                    imageButtons.get(25).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(25));
                imageButtons.get(25).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow7_6:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (buttonsOnSelect.contains(imageButtons.get(26))) {
                    buttonsOnSelect.remove(imageButtons.get(26));
                    imageButtons.get(26).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(26));
                imageButtons.get(26).setBackgroundResource(R.drawable.burngir);
                break;
            case R.id.girrow7_7:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (buttonsOnSelect.contains(imageButtons.get(27))) {
                    buttonsOnSelect.remove(imageButtons.get(27));
                    imageButtons.get(27).setBackgroundResource(R.drawable.gir);
                    break;
                }
                buttonsOnSelect.add(imageButtons.get(27));
                imageButtons.get(27).setBackgroundResource(R.drawable.burngir);
                break;
        }
    }
}