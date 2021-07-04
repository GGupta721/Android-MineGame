package com.example.minegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


/**
 * This class represents the help screen, it contains a link to the of the assignment description, and a alert dialog that provides
 * the instruction on how to play the game.
 */
public class HelpScreen extends AppCompatActivity {


    public static Intent makeIntent(Context context) {
        return new Intent(context, HelpScreen.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
        setAuthorLink();
        setGameInfoLink();
    }

    private void setGameInfoLink() {
        TextView tv = findViewById(R.id.gameInfoLink);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                GameInfoFragment dialog = new GameInfoFragment();
                dialog.show(manager, "Game Info Dialog");
                Log.i("TAG", "just show dialog");
            }
        });
    }

    private void setAuthorLink() {
        TextView author = findViewById(R.id.authorLink);
        author.setMovementMethod(LinkMovementMethod.getInstance());
    }


}
