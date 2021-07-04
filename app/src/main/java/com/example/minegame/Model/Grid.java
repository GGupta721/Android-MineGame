package com.example.minegame.Model;

import java.util.Random;

/** This class represents a Grid that contains cells using a 2-d array. the user specifies the number of rows and column.
 * it contains 2 integer arrays rowScores and colScores that keeps track of the scan score for cells.
 */
public class Grid {
    private int ROW;
    private int COL;
    private Cell[][] Grid;
    private int[] rowScores;
    private int[] colScores;

    public Grid(int ROW, int COL, int mines) {

        this.ROW = ROW;
        this.COL = COL;
        Grid = new Cell[ROW][COL];
        rowScores = new int[ROW];
        colScores = new int[COL];
        populateGrid(mines);

    }

    //private static Grid instance;
    /*public static Grid getInstance(int ROW, int COL, int mines){
        if(instance == null){
            instance = new Grid(ROW,COL,mines);
        }
        return instance;
    }*/

    private void populateGrid(int mines) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                Grid[i][j] = new Cell(i, j);
            }
        }
        populateMines(mines);
        populateScannerScore();
    }

    private void populateMines(int mines) {
        Random rand = new Random();
        for (int i = 0; i < mines; ) {
            int x = rand.nextInt(ROW);
            int y = rand.nextInt(COL);
            if (!Grid[x][y].isChest()) {
                Grid[x][y].setChest(true);
                rowScores[x] = rowScores[x] + 1;
                colScores[y] = colScores[y] + 1;
                i++;
            }
        }
    }

    public Cell getCell(int Row, int Col) {
        return Grid[Row][Col];
    }

    public void updateScannerScore(int row, int col){
        rowScores[row] = rowScores[row] - 1;
        colScores[col] = colScores[col] - 1;
        populateScannerScore();

    }

    private void populateScannerScore() {
        for (int x = 0; x < ROW; x++) {
            for(int y = 0; y< COL; y++){
                int score = rowScores[x] + colScores[y];
                Grid[x][y].setScannerScore(score);

            }
        }


    }
}
