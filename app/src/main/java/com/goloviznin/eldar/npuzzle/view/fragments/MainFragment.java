package com.goloviznin.eldar.npuzzle.view.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goloviznin.eldar.npuzzle.App;
import com.goloviznin.eldar.npuzzle.R;
import com.goloviznin.eldar.npuzzle.presenter.GameView;
import com.goloviznin.eldar.npuzzle.presenter.Presenter;
import com.goloviznin.eldar.npuzzle.view.Cell;
import com.goloviznin.eldar.npuzzle.view.listeners.CellActionListener;

import javax.inject.Inject;

public class MainFragment extends Fragment implements GameView, CellActionListener.CellActionCallback {

    private GridLayout fieldGrid;
    private AlertDialog gameSolvedDialog;

    @Inject
    Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.getAppComponent().inject(this);
        View gameView = inflater.inflate(R.layout.fragment_main, container, false);
        fieldGrid = (GridLayout) gameView.findViewById(R.id.fieldGrid);

        presenter.setGameView(this);
        presenter.onGameViewReady();

        return gameView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResumeGameView();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPauseGameView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (gameSolvedDialog != null) {
            gameSolvedDialog.dismiss();
        }
        presenter.removeGameView();
    }

    private void instantiateFieldGrid(int fieldSize) {
        fieldGrid.removeAllViews();
        fieldGrid.setColumnCount(fieldSize);
        fieldGrid.setRowCount(fieldSize);
        CellActionListener cellActionListener = new CellActionListener(this);
        int margin = getResources().getDimensionPixelOffset(R.dimen.cellMargin);
        for (int id = 0; id < fieldSize * fieldSize; ++id) {
            GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f));
            cellParams.width = 0;
            cellParams.height = 0;
            cellParams.setMargins(margin, margin, margin, margin);

            Cell cell = new Cell(getContext());
            cell.setLayoutParams(cellParams);
            cell.setOnTouchListener(cellActionListener);
            cell.setOnClickListener(cellActionListener);
            cell.setOnLongClickListener(cellActionListener);
            fieldGrid.addView(cell);
        }
    }

    private void applyCellBackColor(int color) {
        for (int idx = 0; idx < fieldGrid.getChildCount(); ++idx) {
            View view = fieldGrid.getChildAt(idx);
            view.setBackgroundColor(color);
            view.getBackground().setAlpha(getResources().getInteger(R.integer.cell_transparency_level));
        }
    }

    @Override
    public void refresh(int fieldSize, int cellBackColor) {
        instantiateFieldGrid(fieldSize);
        applyCellBackColor(cellBackColor);
    }

    @Override
    public void updateCells(int[] arrangement) {
        for (int idx = 0; idx < arrangement.length; ++idx) {
            int num = arrangement[idx];
            Cell cell = (Cell)fieldGrid.getChildAt(idx);
            cell.setNumber(num);
        }
    }

    @Override
    public void showPuzzleSolved() {
        gameSolvedDialog = new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setTitle(R.string.solved_title)
                .setMessage(R.string.solved_question)
                .setPositiveButton(R.string.solved_start, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onCreateNewPuzzle();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.solved_decline, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onCellClicked(View view) {
        if (view instanceof Cell) {
            Cell cell = (Cell) view;
            int moveTo = cell.getNumber();
            presenter.onMoveTo(moveTo);
        }
    }

    @Override
    public void onCellLongPressed() {
        presenter.onCellLongPressed();
    }

    @Override
    public void onCellLongReleased() {
        presenter.onCellLongReleased();
    }

}