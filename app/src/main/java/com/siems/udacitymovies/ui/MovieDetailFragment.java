package com.siems.udacitymovies.ui;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.adapters.TrailerListAdapter;
import com.siems.udacitymovies.data.MovieContract;
import com.siems.udacitymovies.models.Movie;
import com.siems.udacitymovies.models.Trailer;
import com.siems.udacitymovies.services.TrailerApiService;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//TODO: Implement tab strip at top of view pager

public class MovieDetailFragment extends Fragment implements View.OnClickListener{
    private static final int MAX_WIDTH = 185;
    private static final int MAX_HEIGHT = 278;
    @Bind(R.id.movieTitleTextView) TextView mMovieTitleTextView;
    @Bind(R.id.posterDetailImageView) ImageView mPosterDetailImageView;
    @Bind(R.id.releaseYearTextView) TextView mReleaseYearTextView;
    @Bind(R.id.ratingTextView) TextView mRatingTextView;
    @Bind(R.id.markAsFavoriteButton) Button mMarkAsFavoriteButton;
    @Bind(R.id.overviewTextView) TextView mOverviewTextView;
    @Bind(R.id.recyclerview_trailers) RecyclerView mRecyclerView;

    private Movie mMovie;
    public ArrayList<Trailer> mTrailers = new ArrayList<>();
    private TrailerListAdapter mAdapter;

    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie", Parcels.wrap(movie));
        movieDetailFragment.setArguments(args);
        return movieDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovie = Parcels.unwrap(getArguments().getParcelable("movie"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);

        //Initialize movie
        mMovieTitleTextView.setText(mMovie.getTitle());
        String posterUrl = "http://image.tmdb.org/t/p/w185" + mMovie.getPoster_path();
        Picasso.with(view.getContext())
                .load(posterUrl)
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mPosterDetailImageView);
        mReleaseYearTextView.setText(mMovie.getRelease_date().substring(0,4));
        mRatingTextView.setText(mMovie.getVote_average() + " / 10");
        mOverviewTextView.setText(mMovie.getOverview());

        //Initialize button.
        mMarkAsFavoriteButton.setOnClickListener(this);

        //Get trailers for this movie here
        queryTrailers(mMovie.getMovie_id());

        return view;
    }

    private void queryTrailers(int movieId) {
        final TrailerApiService trailerApiService = new TrailerApiService(getActivity());
        String id = movieId + "";

        trailerApiService.findTrailers(id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mTrailers = trailerApiService.processResults(response);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new TrailerListAdapter(mTrailers);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mMarkAsFavoriteButton) {
            long movieId;
            Context context = getContext();

            Cursor movieCursor = context.getContentResolver().query(
                    MovieContract.MovieEntry.CONTENT_URI,
                    null,
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                    new String[]{mMovie.getMovie_id() + ""},
                    null
                    );

            if (movieCursor.moveToFirst()) {
                int movieIdIndex = movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
                movieId = movieCursor.getLong(movieIdIndex);
                int rowsDeleted = context.getContentResolver().delete(
                        MovieContract.MovieEntry.CONTENT_URI,
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                        new String[]{mMovie.getMovie_id() + ""}
                );
                Toast.makeText(getContext(), String.format("Removing %01d rows.", rowsDeleted), Toast.LENGTH_SHORT).show();
            } else {
                ContentValues values = new ContentValues();
                values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, mMovie.getMovie_id());
                values.put(MovieContract.MovieEntry.COLUMN_TITLE, mMovie.getTitle());
                values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, mMovie.getRelease_date());
                values.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, mMovie.getVote_average());
                values.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, mMovie.getVote_count());
                values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, mMovie.getOverview());
                values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, mMovie.getPoster_path());
                values.put(MovieContract.MovieEntry.COLUMN_POPULARITY, mMovie.getPopularity());
                values.put(MovieContract.MovieEntry.COLUMN_RUNTIME, mMovie.getRuntime());
                Uri insertedUri = context.getContentResolver().insert(
                        MovieContract.MovieEntry.CONTENT_URI,
                        values
                );

                // The resulting URI contains the ID for the row.  Extract the movieId from the Uri.
                movieId = ContentUris.parseId(insertedUri);

                Toast.makeText(getContext(), "Adding movie in row " + movieId, Toast.LENGTH_SHORT).show();
            }

        }
    }
}
