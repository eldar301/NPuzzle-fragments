package com.goloviznin.eldar.npuzzle.di;

import com.goloviznin.eldar.npuzzle.view.activities.MainActivity;
import com.goloviznin.eldar.npuzzle.view.fragments.MainFragment;
import com.goloviznin.eldar.npuzzle.view.fragments.MyoScanFragment;
import com.goloviznin.eldar.npuzzle.view.fragments.SettingsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(MainFragment mainFragment);
    void inject(SettingsFragment settingsFragment);
    void inject(MyoScanFragment myoScanFragment);
}
