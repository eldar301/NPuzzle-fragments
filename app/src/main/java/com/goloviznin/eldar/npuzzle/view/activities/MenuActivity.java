package com.goloviznin.eldar.npuzzle.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.goloviznin.eldar.npuzzle.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        boolean isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        if (isLandTablet) {
            //MenuActivity не должна открываться на планшетах в альбомной ориентации
            finish();
        }
    }
}
