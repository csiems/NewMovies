package com.siems.udacitymovies.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.models.Poster;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PosterDetailFragment extends Fragment implements View.OnClickListener{
    private static final int MAX_WIDTH = 185;
    private static final int MAX_HEIGHT = 278;
    @Bind(R.id.movieTitleTextView) TextView mMovieTitleTextView;
    @Bind(R.id.posterDetailImageView) ImageView mPosterDetailImageView;
    @Bind(R.id.releaseYearTextView) TextView mReleaseYearTextView;
    @Bind(R.id.runningTimeTextView) TextView mRunningTimeTextView;
    @Bind(R.id.ratingTextView) TextView mRatingTextView;
    @Bind(R.id.markAsFavoriteButton) Button mMarkAsFavoriteButton;
    @Bind(R.id.overviewTextView) TextView mOverviewTextView;

    private Poster mPoster;

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
        View view = inflater.inflate(R.layout.fragment_detail_activity, container, false);
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
        //No running time in API endpoint.
        mRunningTimeTextView.setText("120 minutes");
        mRatingTextView.setText(mPoster.getVote_average() + " / 10");
        mOverviewTextView.setText(mPoster.getOverview());

        //Initialize button.
        mMarkAsFavoriteButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mMarkAsFavoriteButton) {
            Toast.makeText(getContext(), "I'm special!", Toast.LENGTH_SHORT).show();
        }
    }
}
