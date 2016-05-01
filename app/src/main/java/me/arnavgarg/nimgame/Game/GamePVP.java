package me.arnavgarg.nimgame.Game;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;
import me.arnavgarg.nimgame.Database.GetData;
import me.arnavgarg.nimgame.Homescreen.MainActivity;
import me.arnavgarg.nimgame.R;
import pl.droidsonroids.gif.GifImageButton;

/**
 * Created by Arnav on 5/1/2016.
 */
public class GamePVP extends Activity implements View.OnClickListener, Runnable{


    /*
    START HERE..............
     */

    private static final String LOG_TAG = GameMain.class.getSimpleName();

    //For knowing which row we are working on
    private WorkingRow workingRow;
    //For displaying whose turn it is
    private TextView tvPlayerTurn;
    private TextView tvComputerTurn;

    private ArrayList<GifImageButton> imageButtons;

    private boolean playerTurn;
    private FButton nextTurn;

    //Getting the data from the database.
    private GetData getData;

    //Buttons that have been selected by the user.
    private ArrayList<GifImageButton> selectedButtons;

    //Total selections LEFT!
    private int TOTAL_SELECTIONS = 15;

    //Selecting the game difficulty.
    private GameDifficultyMain gameDifficulty;

    //For keeping track of the time
    private Chronometer chronometer;

    /*
    ...........END HERE
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Making it full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);//Initializing the database and calling the parser.
        getData = new GetData(this);
        getData.parseData();

        //Does all the dirty work..keeps my onCreate clean.
        initialize();
        makeVisible();
        settingOnClickListeners();

        tvPlayerTurn.setText("USER 1");
        tvComputerTurn.setText("USER 2");

        //setting the font type..needs to be done after initialization
        Typeface typface = Typeface.createFromAsset(getAssets(), "minecraftPE.ttf");
        tvPlayerTurn.setTypeface(typface);
        tvComputerTurn.setTypeface(typface);

        //Time for the base.
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setVisibility(View.GONE);

        Thread myThread = new Thread(this);
        myThread.start();

    }

    /**
     * A function to make only the user selected ROWS visible to the user.
     */
    public void makeVisible() {

        switch (getData.getNumberOfSticks()) {

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


    public void settingOnClickListeners() {

        int width, height;

        if(TOTAL_SELECTIONS == 15) {
            width = 200; height = 250;
        }
        else if(TOTAL_SELECTIONS == 21) {
            width =170; height = 220;
        }
        else {
            width = 140; height = 190;
        }

        nextTurn.setOnClickListener(this);

        for (int i = 0; i < TOTAL_SELECTIONS; i++) {
            imageButtons.get(i).setLayoutParams(new LinearLayout.LayoutParams(width, height));
            imageButtons.get(i).setOnClickListener(this);
        }
    }

    //FOR ALL THE CRAZY STUFF THAT'S HAPPENING. THIS IS WHERE I DUMP ALL THE CRAZY SHIT.
    public void initialize() {

        //First things first, gotta initialize the *later* used variables
        workingRow = WorkingRow.NONE;
        selectedButtons = new ArrayList<>();
        tvPlayerTurn = (TextView) findViewById(R.id.tvPlayer);
        tvComputerTurn = (TextView) findViewById(R.id.tvComputer);
        nextTurn = (FButton) findViewById(R.id.btnNextTurn);

        //setting the turn text so that we can use the same xml in PVP
        tvPlayerTurn.setText("USER");
        tvComputerTurn.setText("AI");

        playerTurn = true;
        tvPlayerTurn.setTextColor(Color.GREEN);
        tvComputerTurn.setTextColor(Color.RED);

        //Determining the game difficulty.
        switch (getData.getDifficultyLevel()) {

            case 0:
                break;
            case 1:
                break;
            case 2:
                gameDifficulty = new Hard();
                break;
        }


        imageButtons = new ArrayList<GifImageButton>();


        //It is worse than it looks :(
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow1_1));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow2_1));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow2_2));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow3_1));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow3_2));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow3_3));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow4_1));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow4_2));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow4_3));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow4_4));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow5_1));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow5_2));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow5_3));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow5_4));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow5_5));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow6_1));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow6_2));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow6_3));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow6_4));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow6_5));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow6_6));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow7_1));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow7_2));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow7_3));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow7_4));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow7_5));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow7_6));
        imageButtons.add((GifImageButton) findViewById(R.id.ibRow7_7));

        //Initially setting all of them as invisible.
        for (GifImageButton imageButton : imageButtons) {

            imageButton.setVisibility(View.GONE);
        }
    }

    /**
     * FOR REVERTING THE SELECTIONS IN THE PREVIOUSLY SELECTED ROW.
     */
    public void revertPreviousSelectionRow() {

        for (GifImageButton imageButton : selectedButtons) {

            imageButton.setBackgroundResource(R.drawable.stick);
        }
        selectedButtons.clear();
    }


    /**
     * REMOVE THE SELECTED BUTTONS FROM THE SCREEN!
     */
    public void removeSelected() {

        TOTAL_SELECTIONS -= selectedButtons.size();

        for (final GifImageButton imageButton : selectedButtons) {

            imageButton.setBackgroundResource(R.drawable.lburndown);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    imageButton.setVisibility(View.INVISIBLE);
                }
            }, 2500);
        }

        selectedButtons.clear();
    }
    /**
     * For calculating the number of visible buttons on the screen.
     */
    public int numberOfVisibleButton() {

        int sum = 0;
        for (GifImageButton gifImageButton : imageButtons) {

            if (gifImageButton.getVisibility() == View.VISIBLE) sum++;
        }
        return sum;
    }

    @Override
    public void run() {

        while(true) {
            if(numberOfVisibleButton() == 0) {
                break;
            }
            if(playerTurn) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvPlayerTurn.setTextColor(Color.GREEN);
                        tvComputerTurn.setTextColor(Color.RED);
                    }
                });

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvPlayerTurn.setTextColor(Color.RED);
                        tvComputerTurn.setTextColor(Color.GREEN);
                    }
                });
            }
        }

         /*
        For the Dialog box.
         */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nextTurn.setVisibility(View.INVISIBLE);
                if (!(GamePVP.this).isFinishing()) {
                    final Dialog resultDialog = new Dialog(GamePVP.this);
                    resultDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    resultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    resultDialog.setContentView(R.layout.result_dialog);

                    resultDialog.setCanceledOnTouchOutside(false);
                    TextView userScore = (TextView) resultDialog.findViewById(R.id.ustext);
                    TextView highScore = (TextView) resultDialog.findViewById(R.id.hstext);
                    ImageView resultImage = (ImageView) resultDialog.findViewById(R.id.resultImage);

                    userScore.setVisibility(View.INVISIBLE);
                    highScore.setVisibility(View.INVISIBLE);

                    if (playerTurn) {
                        resultImage.setImageResource(R.drawable.user2won);
                    } else {

                        resultImage.setImageResource(R.drawable.user1won);
                    }

                    Button exit = (Button) resultDialog.findViewById(R.id.btnExit);
                    Button playAgain = (Button) resultDialog.findViewById(R.id.btnPlayAgain);

                    exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(GamePVP.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                    playAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(GamePVP.this, GamePVP.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                    resultDialog.show();
                }
            }
        });


    }


    @Override
    public void onClick(View v) {
        /**
         * 1) We check which button is pressed.
         * 2) We check if the working row is the right one.. if not then revert the perviously selected
         *    row and making the current row active.
         * 3) if the selected button is selected again. then remove it from the selectedButton arraylist.
         *    and then break out of switch!
         * 4) Selected buttons will be added to the selectedButtons arraylist!
         */

        switch (v.getId()) {

            //BEFORE WE START WITH ALL THE CRAZY-NESS
            case R.id.btnNextTurn:
                removeSelected();
                //alternating player turns
                if(playerTurn) playerTurn = false;
                else playerTurn = true;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

            //CRAZY-NESS
            case R.id.ibRow1_1:
                if (!(workingRow.equals(WorkingRow.ROW1))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW1;
                } else if (selectedButtons.contains(imageButtons.get(0))) {
                    selectedButtons.remove(imageButtons.get(0));
                    imageButtons.get(0).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(0));
                imageButtons.get(0).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow2_1:
                if (!(workingRow.equals(WorkingRow.ROW2))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW2;
                } else if (selectedButtons.contains(imageButtons.get(1))) {
                    selectedButtons.remove(imageButtons.get(1));
                    imageButtons.get(1).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(1));
                imageButtons.get(1).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow2_2:
                if (!(workingRow.equals(WorkingRow.ROW2))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW2;
                } else if (selectedButtons.contains(imageButtons.get(2))) {
                    selectedButtons.remove(imageButtons.get(2));
                    imageButtons.get(2).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(2));
                imageButtons.get(2).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow3_1:
                if (!(workingRow.equals(WorkingRow.ROW3))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW3;
                } else if (selectedButtons.contains(imageButtons.get(3))) {
                    selectedButtons.remove(imageButtons.get(3));
                    imageButtons.get(3).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(3));
                imageButtons.get(3).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow3_2:
                if (!(workingRow.equals(WorkingRow.ROW3))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW3;

                } else if (selectedButtons.contains(imageButtons.get(4))) {
                    selectedButtons.remove(imageButtons.get(4));
                    imageButtons.get(4).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(4));
                imageButtons.get(4).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow3_3:
                if (!(workingRow.equals(WorkingRow.ROW3))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW3;
                } else if (selectedButtons.contains(imageButtons.get(5))) {
                    selectedButtons.remove(imageButtons.get(5));
                    imageButtons.get(5).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(5));
                imageButtons.get(5).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow4_1:
                if (!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                } else if (selectedButtons.contains(imageButtons.get(6))) {
                    selectedButtons.remove(imageButtons.get(6));
                    imageButtons.get(6).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(6));
                imageButtons.get(6).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow4_2:
                if (!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                } else if (selectedButtons.contains(imageButtons.get(7))) {
                    selectedButtons.remove(imageButtons.get(7));
                    imageButtons.get(7).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(7));
                imageButtons.get(7).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow4_3:
                if (!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                } else if (selectedButtons.contains(imageButtons.get(8))) {
                    selectedButtons.remove(imageButtons.get(8));
                    imageButtons.get(8).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(8));
                imageButtons.get(8).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow4_4:
                if (!(workingRow.equals(WorkingRow.ROW4))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW4;
                } else if (selectedButtons.contains(imageButtons.get(9))) {
                    selectedButtons.remove(imageButtons.get(9));
                    imageButtons.get(9).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(9));
                imageButtons.get(9).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow5_1:
                if (!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                } else if (selectedButtons.contains(imageButtons.get(10))) {
                    selectedButtons.remove(imageButtons.get(10));
                    imageButtons.get(10).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(10));
                imageButtons.get(10).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow5_2:
                if (!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                } else if (selectedButtons.contains(imageButtons.get(11))) {
                    selectedButtons.remove(imageButtons.get(11));
                    imageButtons.get(11).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(11));
                imageButtons.get(11).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow5_3:
                if (!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                } else if (selectedButtons.contains(imageButtons.get(12))) {
                    selectedButtons.remove(imageButtons.get(12));
                    imageButtons.get(12).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(12));
                imageButtons.get(12).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow5_4:
                if (!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                } else if (selectedButtons.contains(imageButtons.get(13))) {
                    selectedButtons.remove(imageButtons.get(13));
                    imageButtons.get(13).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(13));
                imageButtons.get(13).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow5_5:
                if (!(workingRow.equals(WorkingRow.ROW5))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW5;
                } else if (selectedButtons.contains(imageButtons.get(14))) {
                    selectedButtons.remove(imageButtons.get(14));
                    imageButtons.get(14).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(14));
                imageButtons.get(14).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow6_1:
                if (!(workingRow.equals(WorkingRow.ROW6))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW6;
                } else if (selectedButtons.contains(imageButtons.get(15))) {
                    selectedButtons.remove(imageButtons.get(15));
                    imageButtons.get(15).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(15));
                imageButtons.get(15).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow6_2:
                if (!(workingRow.equals(WorkingRow.ROW6))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW6;
                } else if (selectedButtons.contains(imageButtons.get(16))) {
                    selectedButtons.remove(imageButtons.get(16));
                    imageButtons.get(16).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(16));
                imageButtons.get(16).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow6_3:
                if (!(workingRow.equals(WorkingRow.ROW6))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW6;
                } else if (selectedButtons.contains(imageButtons.get(17))) {
                    selectedButtons.remove(imageButtons.get(17));
                    imageButtons.get(17).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(17));
                imageButtons.get(17).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow6_4:
                if (!(workingRow.equals(WorkingRow.ROW6))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW6;
                } else if (selectedButtons.contains(imageButtons.get(18))) {
                    selectedButtons.remove(imageButtons.get(18));
                    imageButtons.get(18).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(18));
                imageButtons.get(18).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow6_5:
                if (!(workingRow.equals(WorkingRow.ROW6))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW6;
                } else if (selectedButtons.contains(imageButtons.get(19))) {
                    selectedButtons.remove(imageButtons.get(19));
                    imageButtons.get(19).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(19));
                imageButtons.get(19).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow6_6:
                if (!(workingRow.equals(WorkingRow.ROW6))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW6;
                } else if (selectedButtons.contains(imageButtons.get(20))) {
                    selectedButtons.remove(imageButtons.get(20));
                    imageButtons.get(20).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(20));
                imageButtons.get(20).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow7_1:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (selectedButtons.contains(imageButtons.get(21))) {
                    selectedButtons.remove(imageButtons.get(21));
                    imageButtons.get(21).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(21));
                imageButtons.get(21).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow7_2:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (selectedButtons.contains(imageButtons.get(22))) {
                    selectedButtons.remove(imageButtons.get(22));
                    imageButtons.get(22).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(22));
                imageButtons.get(22).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow7_3:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (selectedButtons.contains(imageButtons.get(23))) {
                    selectedButtons.remove(imageButtons.get(23));
                    imageButtons.get(23).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(23));
                imageButtons.get(23).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow7_4:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (selectedButtons.contains(imageButtons.get(24))) {
                    selectedButtons.remove(imageButtons.get(24));
                    imageButtons.get(24).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(24));
                imageButtons.get(24).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow7_5:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (selectedButtons.contains(imageButtons.get(25))) {
                    selectedButtons.remove(imageButtons.get(25));
                    imageButtons.get(25).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(25));
                imageButtons.get(25).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow7_6:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (selectedButtons.contains(imageButtons.get(26))) {
                    selectedButtons.remove(imageButtons.get(26));
                    imageButtons.get(26).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(26));
                imageButtons.get(26).setBackgroundResource(R.drawable.lburning);
                break;
            case R.id.ibRow7_7:
                if (!(workingRow.equals(WorkingRow.ROW7))) {
                    revertPreviousSelectionRow();
                    workingRow = WorkingRow.ROW7;
                } else if (selectedButtons.contains(imageButtons.get(27))) {
                    selectedButtons.remove(imageButtons.get(27));
                    imageButtons.get(27).setBackgroundResource(R.drawable.stick);
                    break;
                }
                selectedButtons.add(imageButtons.get(27));
                imageButtons.get(27).setBackgroundResource(R.drawable.lburning);
                break;
        }

    }
}
