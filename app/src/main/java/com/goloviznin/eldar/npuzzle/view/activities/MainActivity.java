package com.goloviznin.eldar.npuzzle.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.goloviznin.eldar.npuzzle.App;
import com.goloviznin.eldar.npuzzle.R;
import com.goloviznin.eldar.npuzzle.presenter.Presenter;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_new_game) {
            presenter.onCreateNewPuzzle();
        } else if (id == R.id.action_show_menu) {
            Intent mainMenu = new Intent(this, MenuActivity.class);
            startActivity(mainMenu);
        }
        return true;
    }

}
