package com.siems.udacitymovies.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.adapters.PosterGridAdapter;
import com.siems.udacitymovies.adapters.SpacesItemDecoration;
import com.siems.udacitymovies.models.Poster;
import com.siems.udacitymovies.services.PosterApiService;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            getPopularPosters();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getPopularPosters();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getPopularPosters() {
        final PosterApiService posterApiService = new PosterApiService(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        final String sortOrder = prefs.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_default));

        posterApiService.findPosters(sortOrder, new Callback() {
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
