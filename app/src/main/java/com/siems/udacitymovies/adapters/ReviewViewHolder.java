package com.siems.udacitymovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.models.Review;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.list_item_review_textview) TextView mReviewNameTextView;
    private Context mContext;
    private ArrayList<Review> mReviews = new ArrayList<>();

    public ReviewViewHolder(View itemView, ArrayList<Review> reviews) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mReviews = reviews;
        mContext = itemView.getContext();
    }

    public void bindReview(Review review) {
        mReviewNameTextView.setText(review.getContent());
    }
}
