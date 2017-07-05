package com.goloviznin.eldar.npuzzle.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goloviznin.eldar.npuzzle.R;
import com.thalmic.myo.Hub;
import com.thalmic.myo.scanner.ScanActivity;

public class MyoScanFragment extends Fragment implements View.OnClickListener {

    private MyoScanListener callback;

    public interface MyoScanListener {
        void myoHubInitialized();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (MyoScanListener) getActivity();
        }
        catch (ClassCastException ignored) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        showScanActivity();
    }

    private void showScanActivity() {
        Hub hub = Hub.getInstance();
        if (hub.init(getContext())) {
            Intent myoSearch = new Intent(getContext(), ScanActivity.class);
            startActivity(myoSearch);
            callback.myoHubInitialized();
        } else {
            Toast.makeText(getContext(), R.string.error_while_start_scanning, Toast.LENGTH_SHORT).show();
        }
    }

}
