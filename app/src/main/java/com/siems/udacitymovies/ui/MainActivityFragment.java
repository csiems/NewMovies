package com.siems.udacitymovies.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.adapters.MovieListAdapter;
import com.siems.udacitymovies.models.Movie;

import java.util.Arrays;


public class MainActivityFragment extends Fragment {

    private MovieListAdapter movieAdapter;

    Movie[] movies = {
        new Movie("47933", "/9KQX22BeFzuNM66pBA6JbiaJ7Mi.jpg", "2016-06-22", "Independence Day: Resurgence"),
        new Movie("246655", "/zSouWWrySXshPCT4t3UKCQGayyo.jpg", "2016-05-18", "X-Men: Apocalypse"),
        new Movie("127380", "/z09QAf8WbZncbitewNk6lKYMZsh.jpg", "2016-06-16", "Finding Dory"),
        new Movie("368596", "/is6QqgiPQlI3Wmk0bovqUFKM56B.jpg", "2016-05-20", "Back in the Day"),
        new Movie("368596", "/is6QqgiPQlI3Wmk0bovqUFKM56B.jpg", "2016-05-20", "Back in the Day")
    };

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieListAdapter(getActivity(), Arrays.asList(movies));

        GridView gridview = (GridView) rootView.findViewById(R.id.posterImageGridView);
        gridview.setAdapter(movieAdapter);
        return rootView;
    }
}