package com.goloviznin.eldar.npuzzle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    private boolean isLandTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        if (isLandTablet) {
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(android.R.id.content, new SettingsActivityFragment())
//                .commit();

    }

}
