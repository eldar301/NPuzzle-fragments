package com.goloviznin.eldar.npuzzle.presenter;

import com.goloviznin.eldar.npuzzle.data.SettingsHandler;
import com.goloviznin.eldar.npuzzle.model.game.NPuzzle;
import com.goloviznin.eldar.npuzzle.myo.Direction;
import com.goloviznin.eldar.npuzzle.myo.MyoInteractor;

public class PresenterDefault implements Presenter {

    private final SettingsHandler settingsHandler;
    private final MyoInteractor myoInteractor;
    private boolean listenMyo = false;
    private NPuzzle puzzle;
    private GameView gameView;

    public PresenterDefault(SettingsHandler settingsHandler, MyoInteractor myoInteractor) {
        this.settingsHandler = settingsHandler;
        this.myoInteractor = myoInteractor;
    }

    @Override
    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void removeGameView() {
        this.gameView = null;
    }

    @Override
    public void onGameViewReady() {
        if (gameView != null) {
            if (puzzle == null) {
                settingsHandler.load();
                puzzle = NPuzzle.newInstance(settingsHandler.getFieldType(), settingsHandler.getFieldSize());
            }
            gameView.refresh(settingsHandler.getFieldSize(), settingsHandler.getCellBackColor());
            gameView.updateCells(puzzle.getCurrentField().getField());
        }
    }

    @Override
    public void onResumeGameView() {
        if (listenMyo) {
            myoInteractor.setListener(movementListener);
        }
    }

    @Override
    public void onPauseGameView() {
        if (listenMyo) {
            myoInteractor.removeListener();
        }
    }

    @Override
    public void onCreateNewPuzzle() {
        settingsHandler.load();
        puzzle = NPuzzle.newInstance(settingsHandler.getFieldType(), settingsHandler.getFieldSize());
        if (gameView != null) {
            gameView.refresh(settingsHandler.getFieldSize(), settingsHandler.getCellBackColor());
            gameView.updateCells(puzzle.getCurrentField().getField());
        }
    }

    @Override
    public void onMoveTo(int to) {
        if (gameView != null && puzzle != null && !puzzle.isSolved() && puzzle.turnTo(to)) {
            gameView.updateCells(puzzle.getCurrentField().getField());
            if (puzzle.isSolved()) {
                gameView.showPuzzleSolved();
            }
        }
    }

    @Override
    public void onCellLongPressed() {
        if (gameView != null && puzzle != null) {
            gameView.updateCells(puzzle.getFiniteField().getField());
        }
    }

    @Override
    public void onCellLongReleased() {
        if (gameView != null && puzzle != null) {
            gameView.updateCells(puzzle.getCurrentField().getField());
        }
    }

    @Override
    public void onFieldSizeChanged() {
        handleSettingsChanges(true);
    }

    @Override
    public void onFieldTypeChanged() {
        handleSettingsChanges(true);
    }

    @Override
    public void onCellBackColorChanged() {
        handleSettingsChanges(false);
    }

    private void handleSettingsChanges(boolean recreatePuzzle) {
        settingsHandler.load();
        if (recreatePuzzle) {
            puzzle = NPuzzle.newInstance(settingsHandler.getFieldType(), settingsHandler.getFieldSize());
        }
        if (gameView != null && puzzle != null) {
            gameView.refresh(settingsHandler.getFieldSize(), settingsHandler.getCellBackColor());
            gameView.updateCells(puzzle.getCurrentField().getField());
        }
    }

    @Override
    public void searchForMyo() {
        listenMyo = myoInteractor.searchForMyo();
        if (listenMyo) {
            myoInteractor.setListener(movementListener);
        }
    }

    private final MyoInteractor.MovementListener movementListener = new MyoInteractor.MovementListener() {
        @Override
        public void onMoveTo(Direction direction) {
            if (gameView != null && puzzle != null && !puzzle.isSolved()) {
                boolean movementResult = false;
                switch (direction) {
                    case UP:
                        movementResult = puzzle.turnUp();
                        break;
                    case RIGHT:
                        movementResult = puzzle.turnRight();
                        break;
                    case DOWN:
                        movementResult = puzzle.turnDown();
                        break;
                    case LEFT:
                        movementResult = puzzle.turnLeft();
                        break;
                }
                if (movementResult) {
                    gameView.updateCells(puzzle.getCurrentField().getField());
                    if (puzzle.isSolved()) {
                        gameView.showPuzzleSolved();
                    }
                }
            }
        }
    };
}
