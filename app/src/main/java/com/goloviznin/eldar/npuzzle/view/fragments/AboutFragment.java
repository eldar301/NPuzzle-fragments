package com.goloviznin.eldar.npuzzle.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goloviznin.eldar.npuzzle.R;

public class AboutFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_about, container, false);
        view.findViewById(R.id.avatar)
                .setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.avatar) {
            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_github_link)));
            startActivity(browser);
        }
    }
}
