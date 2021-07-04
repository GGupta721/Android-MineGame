package com.example.minegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This class represents the option screen. It gives the player an option to choose the game configuration.
 * It also allows the user to reset the number of time the user has played the game.
 */
public class OptionScreen extends AppCompatActivity {
    private static final String NUM_CHEST_PREF_NAME = "Num of chests";
    private static final String MINE_PREFS_NAME = "AppMinePref";
    private static final String Size_BOARD_PREF_NAME = "Board size choice";
    private static final String Board_PREFS_NAME = "AppBoardSizePref";

    public static Intent makeIntent(Context context) {
        return new Intent(context, OptionScreen.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_screen);
        createMinesRadioButton();
        TextView timesPlayed = (TextView) findViewById(R.id.playCount);
        timesPlayed.setText("Times played: " + GameScreen.getTimesPlayed(this));
        createBoardSizeRadioButton();
        setEraseButton();
    }

    private void createMinesRadioButton() {
        RadioGroup group = findViewById(R.id.radio_group_mines);
        int[] numChest = getResources().getIntArray(R.array.num_chests);
        //create the buttons
        for (int i = 0; i < numChest.length; i++) {
            final int numMine = numChest[i];
            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.chests, numMine));
            button.setTextColor(Color.BLACK);
            // Set onClick
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNumChestSetting(numMine);
                }
            });
            // Add to group
            group.addView(button);
            //Select default button
            if (numMine == getNumMinesChosen(this)) {
                button.setChecked(true);
            }
        }
    }

    private void saveNumChestSetting(int numChest) {
        SharedPreferences prefs = this.getSharedPreferences(MINE_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUM_CHEST_PREF_NAME, numChest);
        editor.apply();
    }

    static public int getNumMinesChosen(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MINE_PREFS_NAME, MODE_PRIVATE);
        int defaultNumChest = context.getResources().getInteger(R.integer.default_num_chest);
        return prefs.getInt(NUM_CHEST_PREF_NAME, defaultNumChest);
    }

    private void createBoardSizeRadioButton() {
        RadioGroup group = findViewById(R.id.radio_group_board_size);
        String[] boardSize = getResources().getStringArray(R.array.board_size);
        //create the buttons
        for (int i = 0; i < boardSize.length; i++) {
            final String boardSizeChoice = boardSize[i];
            RadioButton button = new RadioButton(this);
            button.setText(boardSizeChoice);
            button.setTextColor(Color.BLACK);
            // Set onClick
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveBoardSizeSetting(boardSizeChoice);
                }
            });
            group.addView(button);
            if (boardSizeChoice.equals(getBoardSizeChosen(this))) {
                button.setChecked(true);
            }
        }
    }

    private void saveBoardSizeSetting(String boardSizeChoice) {
        SharedPreferences prefs = this.getSharedPreferences(Board_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Size_BOARD_PREF_NAME, boardSizeChoice);
        editor.apply();
    }


    static public String getBoardSizeChosen(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Board_PREFS_NAME, MODE_PRIVATE);
        String defaultBoardSize = context.getResources().getString(R.string.default_board_size);
        return prefs.getString(Size_BOARD_PREF_NAME, defaultBoardSize);
    }

    private void setEraseButton() {
        Button button = findViewById(R.id.erasebtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                EraseMessageFragment dialog = new EraseMessageFragment();
                dialog.show(manager, "EraseMessageDialog");
                Log.i("TAG", "just show dialog");
            }
        });
    }
}
