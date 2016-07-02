package com.siems.udacitymovies.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.siems.udacitymovies.models.Poster;
import com.siems.udacitymovies.ui.PosterDetailFragment;

import java.util.ArrayList;

public class PosterPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Poster> mPosters;

    public PosterPagerAdapter(FragmentManager fm, ArrayList<Poster> restaurants) {
        super(fm);
        mPosters = restaurants;
    }

    @Override
    public Fragment getItem(int position) {
        return PosterDetailFragment.newInstance(mPosters.get(position));
    }

    @Override
    public int getCount() {
        return mPosters.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPosters.get(position).getTitle();
    }
}