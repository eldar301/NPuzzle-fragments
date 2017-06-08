package com.goloviznin.eldar.npuzzle.activitities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.goloviznin.eldar.npuzzle.fragments.AboutActivityFragment;
import com.goloviznin.eldar.npuzzle.R;
import com.goloviznin.eldar.npuzzle.fragments.MainActivityFragment;
import com.goloviznin.eldar.npuzzle.fragments.MyoActivityFragment;
import com.goloviznin.eldar.npuzzle.fragments.SettingsActivityFragment;

public class MainActivity extends AppCompatActivity {

    private boolean isLandTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        if (isLandTablet) {
            switchFragmentContainerTo(new SettingsActivityFragment());
        }

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(settingsListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(settingsListener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener settingsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            MainActivityFragment mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
            mainActivityFragment.loadPreferences();
            if (key.equals(getString(R.string.preferences_field_size)) || key.equals(getString(R.string.preferences_field_type))) {
                mainActivityFragment.startNewGame();
            } else if (key.equals(getString(R.string.preferences_cell_back))) {
                mainActivityFragment.applyCellBackColor();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void switchFragmentContainerTo(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fragment_appearing, R.anim.fragment_disappearing)
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_newgame) {
            MainActivityFragment mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
            mainActivityFragment.startNewGame();
            return super.onOptionsItemSelected(item);
        }

        if (isLandTablet) {
            if (id == R.id.action_settings) {
                switchFragmentContainerTo(new SettingsActivityFragment());
            } else if (id == R.id.action_myo) {
                switchFragmentContainerTo(new MyoActivityFragment());
            } else if (id == R.id.action_about) {
                switchFragmentContainerTo(new AboutActivityFragment());
            }
        } else {
            if (id == R.id.action_settings) {
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
            } else if (id == R.id.action_myo) {
                Intent myo = new Intent(this, MyoActivity.class);
                startActivity(myo);
            } else if (id == R.id.action_about) {
                Intent about = new Intent(this, AboutActivity.class);
                startActivity(about);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
