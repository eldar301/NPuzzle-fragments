package com.goloviznin.eldar.npuzzle.listeners;

import android.view.MotionEvent;
import android.view.View;

public class CellActionListener implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener{

    private CellActionCallback callback;

    public CellActionListener(CellActionCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {
        callback.onCellClick(v);
    }

    private boolean isLongClicked = false;

    @Override
    public boolean onLongClick(View v) {
        isLongClicked = true;
        callback.onCellPressed();
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && isLongClicked) {
            isLongClicked = false;
            callback.onCellReleased();
        }
        return false;
    }
}
