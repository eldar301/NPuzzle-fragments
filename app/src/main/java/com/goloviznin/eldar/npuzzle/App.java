package com.goloviznin.eldar.npuzzle;

import android.app.Application;

import com.goloviznin.eldar.npuzzle.di.AppComponent;
import com.goloviznin.eldar.npuzzle.di.AppModule;
import com.goloviznin.eldar.npuzzle.di.DaggerAppComponent;

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildAppComponent();
    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
