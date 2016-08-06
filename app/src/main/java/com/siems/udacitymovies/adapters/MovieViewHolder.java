package com.siems.udacitymovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.models.Movie;
import com.siems.udacitymovies.ui.MovieDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieViewHolder extends RecyclerView.ViewHolder{

    @Bind(R.id.posterListItemImageView) ImageView mPosterImageView;

    private Context mContext;
    private List<Movie> mMovies = new ArrayList<>();

    public MovieViewHolder(View itemView, List<Movie> movies) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mMovies = movies;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("movies", Parcels.wrap(mMovies));
                mContext.startActivity(intent);
            }
        });
    }

    public void bindPoster(Movie movie) {
        String posterUrl = "http://image.tmdb.org/t/p/w185" + movie.getPoster_path();
        Picasso.with(mContext)
                .load(posterUrl)
                .fit()
                .error(R.drawable.placeholder_poster)
                .into(mPosterImageView);

    }

}