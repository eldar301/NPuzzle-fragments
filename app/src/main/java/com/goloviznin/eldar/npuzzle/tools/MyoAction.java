package com.goloviznin.eldar.npuzzle.tools;


import android.content.Context;
import android.widget.Toast;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;

import java.io.Serializable;

public class MyoAction {

    private static MyoAction instance = new MyoAction();

    private Context context;
    private MyoActionListener callback;

    public interface MyoActionListener {
        void turnInDirection(Direction direction);
    }

    public static MyoAction getInstance(Context context, MyoActionListener callback) {
        instance.context = context;
        instance.callback = callback;
        return instance;
    }

    public void startListen() {
        Hub.getInstance().addListener(myoListener);
    }

    public void stopListen() {
        Hub.getInstance().removeListener(myoListener);
    }

    private final AbstractDeviceListener myoListener = new AbstractDeviceListener() {
        @Override
        public void onPose(Myo myo, long timestamp, Pose pose) {
            Toast.makeText(context, pose.toString(), Toast.LENGTH_SHORT).show();
            switch (pose) {
                case FINGERS_SPREAD:
                    callback.turnInDirection(Direction.UP);
                    break;
                case WAVE_OUT:
                    callback.turnInDirection(Direction.RIGHT);
                    break;
                case WAVE_IN:
                    callback.turnInDirection(Direction.DOWN);
                    break;
                case FIST:
                    callback.turnInDirection(Direction.LEFT);
                    break;
            }
        }

        @Override
        public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
            super.onOrientationData(myo, timestamp, rotation);
        }
    };

}
