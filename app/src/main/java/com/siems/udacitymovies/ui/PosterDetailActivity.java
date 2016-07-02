package com.siems.udacitymovies.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.adapters.PosterPagerAdapter;
import com.siems.udacitymovies.models.Poster;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PosterDetailActivity extends AppCompatActivity {

    @Bind(R.id.viewPager) ViewPager mViewPager;
    private PosterPagerAdapter adapterViewPager;
    ArrayList<Poster> mPosters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mPosters = Parcels.unwrap(getIntent().getParcelableExtra("posters"));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));
        adapterViewPager = new PosterPagerAdapter(getSupportFragmentManager(), mPosters);

        //setting the PosterPagerAdapter returns PosterDetailFragment.
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
