package com.example.minegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class represents the splash screen which appears when the player starts the app.
 */
public class SplashScreen extends AppCompatActivity {
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        setTreasureAnim();
        setTextAnim();
        setSkipButton();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(runnable, 6000);
    }

    private void setTextAnim() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Animation textAnim = AnimationUtils.loadAnimation(this, R.anim.splash_text_anim);
        TextView slogan = findViewById(R.id.slogan);
        TextView author = findViewById(R.id.author_in_splash);
        slogan.setAnimation(textAnim);
        author.setAnimation(textAnim);
    }

    private void setTreasureAnim() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Animation treasureImage = AnimationUtils.loadAnimation(this, R.anim.splash_treasure_anim);
        ImageView image = findViewById(R.id.splash_treasure);
        image.setAnimation(treasureImage);
    }

    private void setSkipButton() {
        Button skip = findViewById(R.id.skipbtn);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

