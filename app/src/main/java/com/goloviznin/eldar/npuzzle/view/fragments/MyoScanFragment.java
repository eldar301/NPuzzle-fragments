package com.goloviznin.eldar.npuzzle.view.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goloviznin.eldar.npuzzle.App;
import com.goloviznin.eldar.npuzzle.R;
import com.goloviznin.eldar.npuzzle.presenter.Presenter;

import javax.inject.Inject;

public class MyoScanFragment extends Fragment implements View.OnClickListener {

    @Inject
    Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.getAppComponent().inject(this);
        View view = inflater.inflate(R.layout.fragment_myo_scan, container, false);
        view.findViewById(R.id.searchButton)
                .setOnClickListener(this);
        return view;
    }

    private final int PERMISSION_REQUEST_ACCESS_LOCATION = 1;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.searchButton) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_ACCESS_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            presenter.searchForMyo();
        }
    }

}
