package com.goloviznin.eldar.npuzzle.activitities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.goloviznin.eldar.npuzzle.R;

public class AboutActivity extends AppCompatActivity {

    private boolean isLandTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        if (isLandTablet) {
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
