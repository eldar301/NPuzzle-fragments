package com.goloviznin.eldar.npuzzle.view.listeners;

import android.view.MotionEvent;
import android.view.View;

public class CellActionListener implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

    private final CellActionCallback callback;

    public interface CellActionCallback {
        void onCellClicked(View view);
        void onCellLongPressed();
        void onCellLongReleased();
    }

    public CellActionListener(CellActionCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {
        callback.onCellClicked(v);
    }

    private boolean isLongClicked = false;

    @Override
    public boolean onLongClick(View v) {
        isLongClicked = true;
        callback.onCellLongPressed();
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && isLongClicked) {
            isLongClicked = false;
            callback.onCellLongReleased();
            return true;
        }
        return false;
    }
}
