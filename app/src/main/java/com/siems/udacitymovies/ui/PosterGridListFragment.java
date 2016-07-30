package com.siems.udacitymovies.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class PosterGridListFragment extends Fragment {
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private PosterGridAdapter mAdapter;
    public ArrayList<Poster> mPosters = new ArrayList<>();

    public PosterGridListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        queryPosters();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poster_grid_list, container, false);
        ButterKnife.bind(this, view);
        queryPosters();
        return view;
    }

    public void queryPosters() {
        final PosterApiService posterApiService = new PosterApiService(getActivity());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_default));

        posterApiService.findPosters(sortOrder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mPosters = posterApiService.processResults(response);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new PosterGridAdapter(getActivity().getApplicationContext(), mPosters);
                        mRecyclerView.setAdapter(mAdapter);
                        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
                        mRecyclerView.setLayoutManager(mGridLayoutManager);
                        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0));
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }

        });
    }

}
