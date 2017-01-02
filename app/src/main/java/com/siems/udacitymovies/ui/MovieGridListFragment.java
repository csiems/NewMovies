package com.siems.udacitymovies.ui;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.adapters.MovieGridAdapter;
import com.siems.udacitymovies.adapters.SpacesItemDecoration;
import com.siems.udacitymovies.data.MovieContract;
import com.siems.udacitymovies.models.Movie;
import com.siems.udacitymovies.services.MovieApiService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieGridListFragment extends Fragment {
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private MovieGridAdapter mAdapter;
    public ArrayList<Movie> mMovies = new ArrayList<>();

    public MovieGridListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        querySelectedMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_grid_list, container, false);
        ButterKnife.bind(this, view);
        querySelectedMovies();
        return view;
    }

    private void queryMoviesFromSql() {
        mMovies = new ArrayList<>();
        //Query all items in db
        Cursor movieCursor = getContext().getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        //If something gets returned, convert to arraylist of movies
        if(movieCursor.moveToFirst()) {
            int idIndex = movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
            int titleIndex = movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE);
            int releaseDateIndex = movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
            int voteAverageIndex = movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE);
            int voteCountIndex = movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT);
            int overviewIndex = movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW);
            int posterPathIndex = movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
            int popularityIndex = movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POPULARITY);
            do {
                Movie movie = new Movie(
                        movieCursor.getInt(idIndex),
                        movieCursor.getString(titleIndex),
                        movieCursor.getString(releaseDateIndex),
                        movieCursor.getDouble(voteAverageIndex),
                        movieCursor.getInt(voteCountIndex),
                        movieCursor.getString(overviewIndex),
                        movieCursor.getString(posterPathIndex),
                        movieCursor.getDouble(popularityIndex)
                );
                mMovies.add(movie);
            } while (movieCursor.moveToNext());
        }
        movieCursor.close();

        mAdapter = new MovieGridAdapter(getActivity().getApplicationContext(), mMovies);
        mRecyclerView.setAdapter(mAdapter);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0));
        mRecyclerView.setHasFixedSize(true);
    }

    private void queryMoviesFromApi(String sortOrder) {
        final MovieApiService movieApiService = new MovieApiService(getActivity());
        movieApiService.findPosters(sortOrder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mMovies = movieApiService.processResults(response);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new MovieGridAdapter(getActivity().getApplicationContext(), mMovies);
                        mRecyclerView.setAdapter(mAdapter);
                        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
                        mRecyclerView.setLayoutManager(mGridLayoutManager);
                        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0));
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }

    private void querySelectedMovies() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_default));
        if (sortOrder.equals(getString(R.string.pref_sort_my_personal_favorites))) {
            queryMoviesFromSql();
        } else {
            queryMoviesFromApi(sortOrder);
        }
    }

}
