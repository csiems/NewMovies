package com.siems.udacitymovies.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.siems.udacitymovies.models.Movie;
import com.siems.udacitymovies.ui.MovieDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class MoviePagerAdapter extends FragmentPagerAdapter {
    private List<Movie> mMovies;

    public MoviePagerAdapter(FragmentManager fm, ArrayList<Movie> movies) {
        super(fm);
        mMovies = movies;
    }

    @Override
    public Fragment getItem(int position) {
        return MovieDetailFragment.newInstance(mMovies, position);
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMovies.get(position).getTitle();
    }
}