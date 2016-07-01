package com.siems.udacitymovies.ui;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.siems.udacitymovies.R;

import org.parceler.Parcels;

import java.util.ArrayList;

public class PosterDetailActivity extends AppCompatActivity {

    ArrayList<Movie> mMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovies = Parcels.unwrap(getIntent().getParcelableExtra("movies"));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));

    }
}
