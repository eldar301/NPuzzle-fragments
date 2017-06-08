package com.goloviznin.eldar.npuzzle.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goloviznin.eldar.npuzzle.R;
import com.goloviznin.eldar.npuzzle.model.game.Npuzzle;
import com.goloviznin.eldar.npuzzle.listeners.CellActionCallback;
import com.goloviznin.eldar.npuzzle.listeners.CellActionListener;
import com.goloviznin.eldar.npuzzle.views.Cell;

import java.io.Serializable;

public class MainActivityFragment extends Fragment implements CellActionCallback {

    private Npuzzle game;
    private GridLayout fieldGrid;
    private AlertDialog gameSolvedDialog;

    private int fieldSize;
    private String fieldType;
    private int cellBackColor;

    private final String GAME_INSTANCE_KEY = "GAME_INSTANCE_KEY";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View gameView = inflater.inflate(R.layout.fragment_main, container, false);
        fieldGrid = (GridLayout) gameView.findViewById(R.id.fieldGrid);

        loadPreferences();
        if (savedInstanceState == null) {
            createGameInstance();
        } else {
            Serializable serializedGame = savedInstanceState.getSerializable(GAME_INSTANCE_KEY);
            game = (Npuzzle) serializedGame;
        }
        instantiateFieldGrid();
        applyCellBackColor();
        return gameView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(GAME_INSTANCE_KEY, game);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (gameSolvedDialog != null) {
            gameSolvedDialog.dismiss();
            gameSolvedDialog = null;
        }
    }


    public void startNewGame() {
        createGameInstance();
        instantiateFieldGrid();
        applyCellBackColor();
    }

    public void loadPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        fieldSize = Integer.parseInt(preferences.getString(getString(R.string.preferences_field_size),
                getString(R.string.default_preferences_field_size)));
        fieldType = preferences.getString(getString(R.string.preferences_field_type),
                getString(R.string.default_preferences_field_type));
        cellBackColor = Color.parseColor(preferences.getString(getString(R.string.preferences_cell_back),
                getString(R.string.default_preferences_cell_back)));
    }

    private final String CONVENTIONAL = "conventional";
    private final String SPIRAL = "spiral";

    private void createGameInstance() {
        switch (fieldType) {
            case CONVENTIONAL:
                game = Npuzzle.createConventional(fieldSize);
                return;
            case SPIRAL:
                game = Npuzzle.createSpiral(fieldSize);
                break;
        }
    }

    private void instantiateFieldGrid() {
        fieldGrid.removeAllViews();
        fieldGrid.setColumnCount(fieldSize);
        fieldGrid.setRowCount(fieldSize);
        CellActionListener cellActionListener = new CellActionListener(this);
        GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f));
        cellParams.width = 0;
        cellParams.height = 0;
        int margin = getResources().getDimensionPixelOffset(R.dimen.cellMargin);
        cellParams.setMargins(margin, margin, margin, margin);
        int[] initialField = game.getCurrentField().getField();
        for (int num : initialField) {
            Cell cell = new Cell(getContext());
            cell.setLayoutParams(new GridLayout.LayoutParams(cellParams));
            cell.setNumber(num);
            cell.setOnTouchListener(cellActionListener);
            cell.setOnClickListener(cellActionListener);
            cell.setOnLongClickListener(cellActionListener);
            fieldGrid.addView(cell);
        }
    }

    public void applyCellBackColor() {
        for (int idx = 0; idx < fieldGrid.getChildCount(); ++idx) {
            View view = fieldGrid.getChildAt(idx);
            view.setBackgroundColor(cellBackColor);
            view.getBackground().setAlpha(getResources().getInteger(R.integer.cell_transparency_level));
        }
    }

    private void transformCells(int[] nextArrangement) {
        for (int idx = 0; idx < nextArrangement.length; ++idx) {
            int num = nextArrangement[idx];
            Cell cell = (Cell)fieldGrid.getChildAt(idx);
            cell.setNumber(num);
        }
    }

    @Override
    public void onCellClick(View view) {
        if (view instanceof Cell && !game.isSolved()) {
            Cell cell = (Cell) view;
            int switchTo = cell.getNumber();
            if (game.turnTo(switchTo)) {
                transformCells(game.getCurrentField().getField());
                if (game.isSolved()) {
                    showGameSolvedDialog();
                }
            }
        }
    }

    @Override
    public void onCellPressed() {
        showFiniteField();
    }

    @Override
    public void onCellReleased() {
        showCurrentField();
    }

    private void showFiniteField() {
        transformCells(game.getFiniteField().getField());
    }

    private void showCurrentField() {
        transformCells(game.getCurrentField().getField());
    }

    private void showGameSolvedDialog() {
        gameSolvedDialog = new AlertDialog.Builder(getActivity())
                            .setCancelable(false)
                            .setTitle(R.string.solved_title)
                            .setMessage(R.string.solved_question)
                            .setPositiveButton(R.string.solved_start, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startNewGame();
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
}