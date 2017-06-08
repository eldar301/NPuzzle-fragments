package com.goloviznin.eldar.npuzzle.listeners;

import android.view.View;

public interface CellActionCallback {
    void onCellClick(View view);
    void onCellPressed();
    void onCellReleased();
}
