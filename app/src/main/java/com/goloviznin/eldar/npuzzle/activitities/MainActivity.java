package com.goloviznin.eldar.npuzzle.activitities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.goloviznin.eldar.npuzzle.R;
import com.goloviznin.eldar.npuzzle.fragments.MainFragment;
import com.goloviznin.eldar.npuzzle.fragments.MyoScanFragment;
import com.goloviznin.eldar.npuzzle.fragments.SettingsFragment;
import com.goloviznin.eldar.npuzzle.tools.Direction;
import com.goloviznin.eldar.npuzzle.tools.MyoAction;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements
        SettingsFragment.SettingsChangeListener, MyoScanFragment.MyoScanListener, MyoAction.MyoActionListener {

    private MainFragment mainFragment;
    private View menuFragmentContainer;
    private boolean isLandTablet;

    private boolean needToListenMyo;
    private MyoAction myoAction = null;

    private final String MENU_CONTAINER_VISIBILITY = "MENU_CONTAINER_VISIBILITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        menuFragmentContainer = findViewById(R.id.menuFragmentContainer);
        isLandTablet = getResources().getBoolean(R.bool.isLandTablet);

        if (isLandTablet) {
            menuFragmentContainer.setVisibility(View.VISIBLE);
        } else {
            if (savedInstanceState != null) {
                menuFragmentContainer.setVisibility(
                        (savedInstanceState.getBoolean(MENU_CONTAINER_VISIBILITY, false))
                                ? View.VISIBLE
                                : View.INVISIBLE
                );
            } else {
                menuFragmentContainer.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (needToListenMyo) {
            MyoAction myoAction = MyoAction.getInstance(this, this);
            myoAction.startListen();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (needToListenMyo) {
            MyoAction myoAction = MyoAction.getInstance(this, this);
            myoAction.stopListen();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!isLandTablet) {
            boolean visibility = menuFragmentContainer.getVisibility() == View.VISIBLE;
            outState.putBoolean(MENU_CONTAINER_VISIBILITY, visibility);
        }
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
            mainFragment.startNewGame();
        } else if (id == R.id.action_show_menu) {
            Animation animation;
            AnimatorListenerAdapter animatorListenerAdapter;
            if (menuFragmentContainer.getVisibility() == View.INVISIBLE) {
                animation = AnimationUtils.loadAnimation(this, R.anim.menu_appearing);
                animatorListenerAdapter = new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        menuFragmentContainer.setVisibility(View.VISIBLE);
                        animation.removeListener(this);
                    }
                };
            } else {
                animation = AnimationUtils.loadAnimation(this, R.anim.menu_disappearing);
                animatorListenerAdapter = new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        menuFragmentContainer.setVisibility(View.INVISIBLE);
                        animation.removeListener(this);
                    }
                };
            }
            menuFragmentContainer.setAnimation(animation);
            menuFragmentContainer.animate().
                    setListener(animatorListenerAdapter).
                    start();
        }

        return true;
    }

    /* SettingsChangeListener */

    @Override
    public void onFieldSizeChange() {
        mainFragment.loadPreferences();
        mainFragment.startNewGame();
    }

    @Override
    public void onFieldTypeChange() {
        mainFragment.loadPreferences();
        mainFragment.startNewGame();
    }

    @Override
    public void onCellBackChange() {
        mainFragment.loadPreferences();
        mainFragment.applyCellBackColor();
    }

    /* MyoScanListener */

    @Override
    public void myoHubInitialized() {
        needToListenMyo = true;
    }

    /* MyoActionListener */

    @Override
    public void turnInDirection(Direction direction) {
        mainFragment.turnInDirection(direction);
    }

}
