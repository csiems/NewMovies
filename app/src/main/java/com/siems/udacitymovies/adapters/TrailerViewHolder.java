package com.siems.udacitymovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.models.Trailer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @Bind(R.id.list_item_name_textview) TextView mTrailerNameTextView;
    private Context mContext;
    private ArrayList<Trailer> mTrailers = new ArrayList<>();

    public TrailerViewHolder(View itemView, ArrayList<Trailer> trailers) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mTrailers = trailers;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Trailer trailer = mTrailers.get(getLayoutPosition());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
        mContext.startActivity(intent);
    }

    public void bindTrailer(Trailer trailer) {
        mTrailerNameTextView.setText(trailer.getName());
    }


}
