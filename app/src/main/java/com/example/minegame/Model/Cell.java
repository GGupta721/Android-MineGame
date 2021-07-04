package com.example.minegame.Model;


/** This class represents a cell in the 2-d array. The cell contains features that defines it into either a treasure chest or empty cell.
 */
public class Cell {
    private int xPos;
    private int yPos;
    private boolean isChest;
    private boolean isVisited;



    private boolean isScanned;

    private int ScannerScore;

    public Cell(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        isChest = false;
        isVisited = false;
    }



    public boolean isChest() {
        return isChest;
    }

    public void setChest(boolean chest) {
        isChest = chest;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }

    public int getScannerScore() {
        return ScannerScore;
    }

    public void setScannerScore(int scannerScore) {
        ScannerScore = scannerScore;
    }


}
