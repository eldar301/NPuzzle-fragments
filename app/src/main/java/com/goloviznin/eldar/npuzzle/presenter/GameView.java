package com.goloviznin.eldar.npuzzle.presenter;

public interface GameView {
    void refresh(int fieldSize, int cellBackColor);
    void updateCells(int[] arrangement);
    void showPuzzleSolved();
}
