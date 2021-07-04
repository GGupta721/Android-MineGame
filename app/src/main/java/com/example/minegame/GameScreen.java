package com.example.minegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.minegame.Model.Cell;
import com.example.minegame.Model.Grid;


/**
 * This class represents the game screen. It keeps track of the number of treasure chests found and hidden, no of scans used, and total number
 * of times the user has played the game.
 */
public class GameScreen extends AppCompatActivity {

    public static final String TOTAL_TIMES_PLAYED = "Total Times Played";
    public static final String TIMES_PLAYED = "Times Played";

    public int Row;
    public int Col;
    public int Chests;

    public int chestsFound = 0;
    public int scansUsed = 0;
    TextView count;
    TextView scanCount;
    TextView timePlayed;

    Grid mainGrid;
    Button gridButtons[][];

    public static Intent makeIntent(Context C) {
        Intent intent = new Intent(C, GameScreen.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        saveTimesPlayed(getTimesPlayed(this) + 1);

        Chests = OptionScreen.getNumMinesChosen(this);
        setBoardSize(OptionScreen.getBoardSizeChosen(this));

        mainGrid = new Grid(Row, Col, Chests);
        gridButtons = new Button[Row][Col];

        count = (TextView) findViewById(R.id.count);
        scanCount = (TextView) findViewById(R.id.scanCount);
        timePlayed = (TextView) findViewById(R.id.timesPlayed);
        count.setText("Found 0 of " + Chests + " mines");
        scanCount.setText("# Scans used: 0");
        timePlayed.setText("Times Played: " + getTimesPlayed(this));
        populateGrid();

    }

    public void saveTimesPlayed(int sum) {
        SharedPreferences prefs = this.getSharedPreferences(TIMES_PLAYED, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(TOTAL_TIMES_PLAYED, sum);
        editor.apply();
    }

    static public int getTimesPlayed(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(TIMES_PLAYED, MODE_PRIVATE);
        return prefs.getInt(TOTAL_TIMES_PLAYED, 0);

    }

    public void setBoardSize(String boardSizeChosen) {
        String str = boardSizeChosen;

        if (str.equals("4 x 6")) {
            Row = 4;
            Col = 6;
        } else if (str.equals("5 x 10")) {
            Row = 5;
            Col = 10;
        } else if (str.equals("6 x 15")) {
            Row = 6;
            Col = 15;
        }

        return;
    }


    private void populateGrid() {
        TableLayout mainTable = (TableLayout) findViewById(R.id.gameGrid);
        for (int currentRow = 0; currentRow < Row; currentRow++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            mainTable.addView(tableRow);

            for (int currentCol = 0; currentCol < Col; currentCol++) {
                final int FinalRow = currentRow;
                final int FinalCol = currentCol;
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                button.setPadding(0, 0, 0, 0);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButton(FinalRow, FinalCol);
                    }
                });
                tableRow.addView(button);
                gridButtons[currentRow][currentCol] = button;
            }
        }
    }

    private void gridButton(int finalRow, int finalCol) {
        lockGridSize();

        Cell clickedCell = mainGrid.getCell(finalRow, finalCol);
        Button clickedButton = gridButtons[finalRow][finalCol];

        if (!clickedCell.isVisited()) {
            if (clickedCell.isChest()) {
                setButtonImage(clickedButton);
                MediaPlayer foundTreasure = MediaPlayer.create(this, R.raw.found);
                foundTreasure.start();

                chestsFound = chestsFound + 1;
                count.setText("Found " + chestsFound + " of " + Chests + " chests");

                mainGrid.updateScannerScore(finalRow, finalCol);
                updateButtonGrid();

                if (chestsFound >= Chests) {
                    FragmentManager manager = getSupportFragmentManager();
                    WinMessageFragment dialog = new WinMessageFragment();
                    dialog.show(manager, "Message Dialog");

                }
            }
            if (!clickedCell.isChest()) {

                scansUsed = scansUsed + 1;
                scanCount.setText("# Scans used: " + scansUsed);

                scannerEffect(clickedButton);

                clickedButton.setText("" + clickedCell.getScannerScore());
                clickedCell.setScanned(true);
            }
        }
        if (clickedCell.isVisited()) {
            if (clickedCell.isChest()) {
                scannerEffect(clickedButton);
                scansUsed = scansUsed + 1;
                scanCount.setText("# Scans used: " + scansUsed);
                clickedButton.setText("" + clickedCell.getScannerScore());
                clickedCell.setScanned(true);
            }

        }
        clickedCell.setVisited(true);
    }

    private void scannerEffect(Button clickedButton) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scanning);
        clickedButton.startAnimation(animation);

        MediaPlayer scannerSound = MediaPlayer.create(this, R.raw.sonareffect);
        scannerSound.start();
    }

    private void updateButtonGrid() {
        for (int row = 0; row < Row; row++) {
            for (int col = 0; col < Col; col++) {
                Cell currentCell = mainGrid.getCell(row, col);
                Button currentBtn = gridButtons[row][col];
                if (currentCell.isVisited()) {
                    if (currentCell.isScanned()) {
                        currentBtn.setText("" + currentCell.getScannerScore());
                    }
                }
            }
        }
    }

    private void setButtonImage(Button clickedButton) {
        int newWidth = clickedButton.getWidth();
        int newHeight = clickedButton.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tresure1);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        clickedButton.setBackground(new BitmapDrawable(resource, scaledBitmap));
    }

    private void lockGridSize() {
        for (int row = 0; row < Row; row++) {
            for (int col = 0; col < Col; col++) {
                Button btn = gridButtons[row][col];
                int width = btn.getWidth();
                btn.setMinimumWidth(width);
                btn.setMaxWidth(width);
                int height = btn.getHeight();
                btn.setMinimumHeight(height);
                btn.setMaxHeight(height);
            }
        }
    }
}
