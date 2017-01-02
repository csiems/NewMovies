package com.siems.udacitymovies.services;

import android.content.Context;
import android.util.Log;

import com.siems.udacitymovies.BuildConfig;
import com.siems.udacitymovies.models.Review;

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

public class ReviewApiService {
    private static final String TAG = ReviewApiService.class.getSimpleName();
    private Context mContext;

    public ReviewApiService(Context context) {
        this.mContext = context;
    }

    public void findReviews(String movieId, Callback callback) {
        String THE_MOVIE_DB_API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.themoviedb.org/3/movie").newBuilder();
        urlBuilder.addPathSegment(movieId);
        urlBuilder.addPathSegment("reviews");
        urlBuilder.addQueryParameter("api_key", THE_MOVIE_DB_API_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Review> processResults(Response response) {
        ArrayList<Review> reviews = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject reviewJSON = new JSONObject(jsonData);
                JSONArray resultsArray = reviewJSON.getJSONArray("results");
                return processArray(resultsArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    private ArrayList<Review> processArray(JSONArray specificArray) {
        ArrayList<Review> reviews = new ArrayList<>();
        for (int i = 0; i < specificArray.length(); i++) {
            try {
                JSONObject reviewJSON = specificArray.getJSONObject(i);
                String id = reviewJSON.getString("id");
                String author = reviewJSON.getString("author");
                String content = reviewJSON.getString("content");
                String url = reviewJSON.getString("url");
                Review trailer = new Review(id, author, content, url);
                reviews.add(trailer);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, reviews.toString());
        return reviews;
    }



}
