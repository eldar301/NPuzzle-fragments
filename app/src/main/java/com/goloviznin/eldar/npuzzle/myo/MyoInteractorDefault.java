package com.goloviznin.eldar.npuzzle.myo;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.goloviznin.eldar.npuzzle.R;
import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Arm;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.scanner.ScanActivity;

public class MyoInteractorDefault implements MyoInteractor {

    private final Context context;
    private MovementListener movementListener;

    private boolean initialized = false;

    public MyoInteractorDefault(Context context) {
        this.context = context;
    }

    @Override
    public void setListener(MovementListener movementListener) {
        if (initialized && this.movementListener == null) {
            Hub.getInstance()
                    .addListener(myoListener);
            this.movementListener = movementListener;
        }
    }

    @Override
    public void removeListener() {
        if (initialized && this.movementListener != null) {
            this.movementListener = null;
            Hub.getInstance()
                    .removeListener(myoListener);
        }
    }

    @Override
    public boolean searchForMyo() {
        if (init()) {
            Intent myoSearchActivity = new Intent(context, ScanActivity.class);
            myoSearchActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myoSearchActivity);
            return true;
        } else {
            Toast.makeText(context, R.string.error_while_start_scanning, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean init() {
        if (!initialized) {
            Hub hub = Hub.getInstance();
            if (hub.init(context)) {
                hub.setLockingPolicy(Hub.LockingPolicy.NONE);
                initialized = true;
            }
        }
        return initialized;
    }

    private final AbstractDeviceListener myoListener = new AbstractDeviceListener() {
        @Override
        public void onPose(Myo myo, long timestamp, Pose pose) {
            if (movementListener != null) {
                if (myo.getArm() == Arm.LEFT) {
                    if (pose == Pose.WAVE_IN) {
                        pose = Pose.WAVE_OUT;
                    } else if (pose == Pose.WAVE_OUT) {
                        pose = Pose.WAVE_IN;
                    }
                }

                switch (pose) {
                    case FIST:
                        movementListener.onMoveTo(Direction.DOWN);
                        break;
                    case FINGERS_SPREAD:
                        movementListener.onMoveTo(Direction.UP);
                        break;
                    case WAVE_IN:
                        movementListener.onMoveTo(Direction.LEFT);
                        break;
                    case WAVE_OUT:
                        movementListener.onMoveTo(Direction.RIGHT);
                        break;
                }
            }
        }
    };
}