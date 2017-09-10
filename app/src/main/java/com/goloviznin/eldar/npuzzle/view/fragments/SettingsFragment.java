package com.goloviznin.eldar.npuzzle.view.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goloviznin.eldar.npuzzle.App;
import com.goloviznin.eldar.npuzzle.R;
import com.goloviznin.eldar.npuzzle.presenter.Presenter;

import javax.inject.Inject;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Inject
    Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PreferenceManager.getDefaultSharedPreferences(getContext())
            .registerOnSharedPreferenceChangeListener(settingsListener);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(settingsListener);
    }

    private final SharedPreferences.OnSharedPreferenceChangeListener settingsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(getString(R.string.preferences_field_size))) {
                presenter.onFieldSizeChanged();
            } else if (key.equals(getString(R.string.preferences_field_type))) {
                presenter.onFieldTypeChanged();
            } else if (key.equals(getString(R.string.preferences_cell_back))) {
                presenter.onCellBackColorChanged();
            }
        }
    };

}
