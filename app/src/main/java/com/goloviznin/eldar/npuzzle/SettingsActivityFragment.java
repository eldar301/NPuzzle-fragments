package com.goloviznin.eldar.npuzzle;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsActivityFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
}
