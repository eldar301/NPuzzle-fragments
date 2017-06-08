package com.goloviznin.eldar.npuzzle.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goloviznin.eldar.npuzzle.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyoActivityFragment extends Fragment {

    public MyoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myo, container, false);
    }
}
