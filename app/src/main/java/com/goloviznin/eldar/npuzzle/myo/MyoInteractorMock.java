package com.goloviznin.eldar.npuzzle.myo;


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MyoInteractorMock implements MyoInteractor {

    private static final int delay = 1000;
    private static final int period = 500;

    private final Random random = new Random();
    private final int directions = Direction.values().length;

    private MovementListener movementListener;

    @Override
    public void setListener(MovementListener movementListener) {
        this.movementListener = movementListener;
    }

    @Override
    public void removeListener() {
        this.movementListener = null;
    }

    @Override
    public boolean searchForMyo() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (movementListener != null) {
                    movementListener.onMoveTo(Direction.values()[random.nextInt(directions)]);
                }
            }
        }, delay, period);
        return true;
    }
}
