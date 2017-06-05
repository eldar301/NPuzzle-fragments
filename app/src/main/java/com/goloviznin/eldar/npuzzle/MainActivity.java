package com.goloviznin.eldar.npuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SettingsActivityFragment settingsActivityFragment = new SettingsActivityFragment();
    AboutActivityFragment aboutActivityFragment = new AboutActivityFragment();
    private boolean isLandTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, true);

        isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        if (isLandTablet) {
            switchFragmentContainerTo(settingsActivityFragment);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Hello world", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void switchFragmentContainerTo(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (isLandTablet) {
            if (id == R.id.action_settings) {
                switchFragmentContainerTo(settingsActivityFragment);
            } else if (id == R.id.action_about) {
                switchFragmentContainerTo(aboutActivityFragment);
            }
        } else {
            if (id == R.id.action_settings) {
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
            } else if (id == R.id.action_about) {
                Intent about = new Intent(this, AboutActivity.class);
                startActivity(about);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
