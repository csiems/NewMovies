package com.siems.udacitymovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.models.Movie;
import com.siems.udacitymovies.ui.MovieDetailActivity;
import com.siems.udacitymovies.ui.MovieDetailFragment;
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
    private int mSmallestScreenWidth;

    public MovieViewHolder(View itemView, List<Movie> movies) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mMovies = movies;
        mSmallestScreenWidth = itemView.getResources().getConfiguration().smallestScreenWidthDp;
        if (mSmallestScreenWidth > 599) {
            createDetailFragment(0);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = getLayoutPosition();
                if (mSmallestScreenWidth > 599) {
                    createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra("position", itemPosition + "");
                    intent.putExtra("movies", Parcels.wrap(mMovies));
                    mContext.startActivity(intent);
                }
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

    public void createDetailFragment(int position) {
        // Creates new RestaurantDetailFragment with the given position:
        MovieDetailFragment detailFragment = MovieDetailFragment.newInstance(mMovies, position);
        // Gathers necessary components to replace the FrameLayout in the layout with the MovieDetailFragment:
        FragmentTransaction ft = ((FragmentActivity) extractActivityFromWrapper()).getSupportFragmentManager().beginTransaction();
        //  Replaces the FrameLayout with the MovieDetailFragment:
        ft.replace(R.id.movieDetailContainer, detailFragment);
        // Commits these changes:
        ft.commit();
    }

    private Activity extractActivityFromWrapper() {
        Context context = mContext;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}