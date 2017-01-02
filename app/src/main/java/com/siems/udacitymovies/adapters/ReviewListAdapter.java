package com.siems.udacitymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.models.Review;

import java.util.ArrayList;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private ArrayList<Review> mReviews = new ArrayList<>();
    public ReviewViewHolder viewHolder;

    public ReviewListAdapter(ArrayList<Review> reviews) {
        mReviews = reviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        viewHolder = new ReviewViewHolder(view, mReviews);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bindReview(mReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

}
