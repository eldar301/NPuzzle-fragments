package com.goloviznin.eldar.npuzzle.myo;

public interface MyoInteractor {
    interface MovementListener {
        void onMoveTo(Direction direction);
    }
    void setListener(MovementListener movementListener);
    void removeListener();
    boolean searchForMyo();
}
