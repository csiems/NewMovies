package com.siems.udacitymovies.ui;


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
import com.siems.udacitymovies.models.Poster;
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

public class PosterDetailFragment extends Fragment implements View.OnClickListener{
    private static final int MAX_WIDTH = 185;
    private static final int MAX_HEIGHT = 278;
    @Bind(R.id.movieTitleTextView) TextView mMovieTitleTextView;
    @Bind(R.id.posterDetailImageView) ImageView mPosterDetailImageView;
    @Bind(R.id.releaseYearTextView) TextView mReleaseYearTextView;
    @Bind(R.id.ratingTextView) TextView mRatingTextView;
    @Bind(R.id.markAsFavoriteButton) Button mMarkAsFavoriteButton;
    @Bind(R.id.overviewTextView) TextView mOverviewTextView;
    @Bind(R.id.recyclerview_trailers) RecyclerView mRecyclerView;

    private Poster mPoster;
    public ArrayList<Trailer> mTrailers = new ArrayList<>();
    private TrailerListAdapter mAdapter;

    public static PosterDetailFragment newInstance(Poster poster) {
        PosterDetailFragment posterDetailFragment = new PosterDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("poster", Parcels.wrap(poster));
        posterDetailFragment.setArguments(args);
        return posterDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPoster = Parcels.unwrap(getArguments().getParcelable("poster"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poster_detail, container, false);
        ButterKnife.bind(this, view);

        //Initialize movie
        mMovieTitleTextView.setText(mPoster.getTitle());
        String posterUrl = "http://image.tmdb.org/t/p/w185" + mPoster.getPoster_path();
        Picasso.with(view.getContext())
                .load(posterUrl)
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mPosterDetailImageView);
        mReleaseYearTextView.setText(mPoster.getRelease_date().substring(0,4));
        mRatingTextView.setText(mPoster.getVote_average() + " / 10");
        mOverviewTextView.setText(mPoster.getOverview());

        //Initialize button.
        mMarkAsFavoriteButton.setOnClickListener(this);

        //Get trailers for this movie here
        queryTrailers(mPoster.getId());

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
            Toast.makeText(getContext(), "I'm special!", Toast.LENGTH_SHORT).show();
        }
    }
}
