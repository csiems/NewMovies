package com.siems.udacitymovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.models.Poster;
import com.siems.udacitymovies.ui.PosterDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PosterViewHolder extends RecyclerView.ViewHolder{

    @Bind(R.id.posterListItemImageView) ImageView mPosterImageView;

    private Context mContext;
    private List<Poster> mPosters = new ArrayList<>();

    public PosterViewHolder(View itemView, List<Poster> posters) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mPosters = posters;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, PosterDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("posters", Parcels.wrap(mPosters));
                mContext.startActivity(intent);
            }
        });
    }

    public void bindPoster(Poster poster) {
        String posterUrl = "http://image.tmdb.org/t/p/w185" + poster.getPoster_path();
        Picasso.with(mContext)
                .load(posterUrl)
                .fit()
                .error(R.drawable.placeholder_poster)
                .into(mPosterImageView);

    }

}