package com.example.minegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This is the main activity class of the game. It contains three buttons , to game, options, and help screen.
 */
public class MainActivity extends AppCompatActivity {
    public static final String TOTAL_TIMES_PLAYED = "Total Times Played";
    public static final String TIMES_PLAYED = "Times Played";
    int timesPlayed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPlayButton();
        setOptionsButton();
        setHelpButton();
    }

    private void setHelpButton() {
        Button Help = findViewById(R.id.helpbtn);
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = HelpScreen.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setOptionsButton() {
        Button Option = findViewById(R.id.optionbtn);
        Option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OptionScreen.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setPlayButton() {
        Button play = findViewById(R.id.playbtn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GameScreen.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }
}
