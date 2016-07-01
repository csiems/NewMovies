package com.siems.udacitymovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.models.Poster;

import java.util.ArrayList;
import java.util.List;

public class PosterGridAdapter extends RecyclerView.Adapter<PosterViewHolder> {
    private List<Poster> mPosters = new ArrayList<>();
    private Context mContext;

    public PosterGridAdapter(Context context, List<Poster> posters) {
        mContext = context;
        mPosters = posters;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_list_item, parent, false);
        PosterViewHolder viewHolder = new PosterViewHolder(view, mPosters);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        holder.bindPoster(mPosters.get(position));
    }

    @Override
    public int getItemCount() {
        return mPosters.size();
    }

}
