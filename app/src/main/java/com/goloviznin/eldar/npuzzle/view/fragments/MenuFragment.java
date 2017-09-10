package com.goloviznin.eldar.npuzzle.view.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goloviznin.eldar.npuzzle.R;

public class MenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new SwipingPagerAdapter(getChildFragmentManager()));

        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private class SwipingPagerAdapter extends FragmentPagerAdapter {

        private final int pagesCount = 3;

        SwipingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new SettingsFragment();
                case 1: return new MyoScanFragment();
                case 2: return new AboutFragment();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0 : return getString(R.string.menu_settings_title);
                case 1 : return getString(R.string.menu_myo_title);
                case 2 : return getString(R.string.menu_about_title);
            }
            return null;
        }

        @Override
        public int getCount() {
            return pagesCount;
        }
    }
}
