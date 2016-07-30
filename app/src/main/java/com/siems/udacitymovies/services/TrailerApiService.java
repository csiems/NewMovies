package com.siems.udacitymovies.services;

import android.content.Context;
import android.util.Log;

import com.siems.udacitymovies.BuildConfig;
import com.siems.udacitymovies.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TrailerApiService {
    private static final String TAG = TrailerApiService.class.getSimpleName();
    private Context mContext;

    public TrailerApiService(Context context) {
        this.mContext = context;
    }

    public void findTrailers(String movieId, Callback callback) {
        String THE_MOVIE_DB_API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.themoviedb.org/3/movie/").newBuilder();
        urlBuilder.addPathSegment(movieId + "/videos");
        urlBuilder.addQueryParameter("api_key", THE_MOVIE_DB_API_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Trailer> processResults(Response response) {
        ArrayList<Trailer> videos = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject videoJSON = new JSONObject(jsonData);
                JSONArray resultsArray = videoJSON.getJSONArray("results");
                return processArray(resultsArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videos;
    }

    private ArrayList<Trailer> processArray(JSONArray specificArray) {
        ArrayList<Trailer> trailers = new ArrayList<>();
        for (int i = 0; i < specificArray.length(); i++) {
            try {
                JSONObject videoJSON = specificArray.getJSONObject(i);
                String id = videoJSON.getString("id");
                String iso6391 = videoJSON.getString("iso6391");
                String key = videoJSON.getString("key");
                String name = videoJSON.getString("name");
                String site = videoJSON.getString("site");
                Integer size = videoJSON.getInt("size");
                String type = videoJSON.getString("type");
                Trailer trailer = new Trailer(id, iso6391, key, name, site, size, type);
                trailers.add(trailer);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, trailers.toString());
        return trailers;
    }





}
