package com.goloviznin.eldar.npuzzle.presenter;

public interface Presenter {
    void setGameView(GameView gameView);
    void removeGameView();
    void onGameViewReady();
    void onResumeGameView();
    void onPauseGameView();
    void onCreateNewPuzzle();
    void onMoveTo(int to);
    void onCellLongPressed();
    void onCellLongReleased();
    void onFieldSizeChanged();
    void onFieldTypeChanged();
    void onCellBackColorChanged();
    void searchForMyo();
}
