package com.siems.udacitymovies.services;

import android.content.Context;
import android.util.Log;

import com.siems.udacitymovies.BuildConfig;
import com.siems.udacitymovies.models.Poster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PosterApiService {
    private static final String TAG = PosterApiService.class.getName();
    private Context mContext;

    public PosterApiService(Context context) {
        this.mContext = context;
    }

    public void findPosters(String sortOrder, Callback callback) {
        String THE_MOVIE_DB_API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.themoviedb.org/3/discover/movie?").newBuilder();
        urlBuilder.addQueryParameter("api_key", THE_MOVIE_DB_API_KEY);
        urlBuilder.addQueryParameter("sortBy", sortOrder + ".desc");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Poster> processResults(Response response) {
        ArrayList<Poster> movies = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject movieJSON = new JSONObject(jsonData);
                JSONArray resultsArray = movieJSON.getJSONArray("results");
                return processArray(resultsArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    private ArrayList<Poster> processArray(JSONArray specificArray) {
        ArrayList<Poster> posters = new ArrayList<>();
        for (int i = 0; i < specificArray.length(); i++) {
            try {
                JSONObject movieJSON = specificArray.getJSONObject(i);
                String poster_path = movieJSON.getString("poster_path");
                boolean adult = movieJSON.getBoolean("adult");
                String overview = movieJSON.getString("overview");
                String release_date = movieJSON.getString("release_date");
                List<Integer> genre_ids = new ArrayList<>();
                JSONArray genre_idsJSON = movieJSON.getJSONArray("genre_ids");
                for (int y = 0; y < genre_idsJSON.length(); y++) {
                    genre_ids.add(Integer.parseInt(genre_idsJSON.get(y).toString()));
                }
                int id = movieJSON.getInt("id");
                String original_title = movieJSON.getString("original_title");
                String original_language = movieJSON.getString("original_language");
                String title = movieJSON.getString("title");
                String backdrop_path = movieJSON.getString("backdrop_path");
                double popularity = movieJSON.getDouble("popularity");
                int vote_count = movieJSON.getInt("vote_count");
                boolean video = movieJSON.getBoolean("video");
                double vote_average = movieJSON.getDouble("vote_average");

                Poster poster = new Poster(poster_path, adult, overview, release_date, genre_ids, id, original_title, original_language, title, backdrop_path, popularity, vote_count, video, vote_average);
                posters.add(poster);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, posters.toString());
        return posters;
    }

}
