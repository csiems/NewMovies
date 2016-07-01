package com.siems.udacitymovies.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.siems.udacitymovies.adapters.PosterGridAdapter;
import com.siems.udacitymovies.adapters.SpacesItemDecoration;
import com.siems.udacitymovies.models.Poster;
import com.siems.udacitymovies.services.PosterApiService;
import com.siems.udacitymovies.R;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    public static final String TAG = MainActivity.class.getSimpleName();
    private GridLayoutManager mGridLayoutManager;
    private PosterGridAdapter mAdapter;
    public ArrayList<Poster> mPosters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getPopularPosters();
    }

    private void getPopularPosters() {

        final PosterApiService posterApiService = new PosterApiService(this);

        posterApiService.findPosters(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mPosters = posterApiService.processResults(response);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new PosterGridAdapter(getApplicationContext(), mPosters);
                        mRecyclerView.setAdapter(mAdapter);
                        mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                        mRecyclerView.setLayoutManager(mGridLayoutManager);
                        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0));
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }

        });
    }
}
