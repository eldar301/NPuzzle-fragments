package com.goloviznin.eldar.npuzzle.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.goloviznin.eldar.npuzzle.data.SettingsHandler;
import com.goloviznin.eldar.npuzzle.data.SettingsHandlerDefault;
import com.goloviznin.eldar.npuzzle.myo.MyoInteractor;
import com.goloviznin.eldar.npuzzle.myo.MyoInteractorDefault;
import com.goloviznin.eldar.npuzzle.presenter.Presenter;
import com.goloviznin.eldar.npuzzle.presenter.PresenterDefault;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    SettingsHandler provideSettingsKeeper(Context context, SharedPreferences sharedPreferences) {
        return new SettingsHandlerDefault(context, sharedPreferences);
    }

    @Provides
    MyoInteractor provideMyoInteractor(Context context) {
        return new MyoInteractorDefault(context);
    }

    @Provides
    @Singleton
    Presenter providePresenter(SettingsHandler settingsHandler, MyoInteractor myoInteractor) {
        return new PresenterDefault(settingsHandler, myoInteractor);
    }
}
