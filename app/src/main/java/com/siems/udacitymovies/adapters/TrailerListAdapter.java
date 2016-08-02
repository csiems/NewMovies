package com.siems.udacitymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.models.Trailer;

import java.util.ArrayList;

public class TrailerListAdapter extends RecyclerView.Adapter<TrailerViewHolder> {
    private ArrayList<Trailer> mTrailers = new ArrayList<>();
    public TrailerViewHolder viewHolder;

    public TrailerListAdapter(ArrayList<Trailer> trailers) {
        mTrailers = trailers;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailer, parent, false);
        viewHolder = new TrailerViewHolder(view, mTrailers);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bindTrailer(mTrailers.get(position));
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }
}
