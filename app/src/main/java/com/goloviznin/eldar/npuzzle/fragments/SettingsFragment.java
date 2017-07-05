package com.goloviznin.eldar.npuzzle.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goloviznin.eldar.npuzzle.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsChangeListener callback;

    public interface SettingsChangeListener {
        void onFieldSizeChange();
        void onFieldTypeChange();
        void onCellBackChange();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (SettingsChangeListener) getActivity();
        }
        catch (ClassCastException ignored) {
        }
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

    private SharedPreferences.OnSharedPreferenceChangeListener settingsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(getString(R.string.preferences_field_size))) {
                callback.onFieldSizeChange();
            } else if (key.equals(getString(R.string.preferences_field_type))) {
                callback.onFieldTypeChange();
            } else if (key.equals(getString(R.string.preferences_cell_back))) {
                callback.onCellBackChange();
            }
        }
    };

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
}
