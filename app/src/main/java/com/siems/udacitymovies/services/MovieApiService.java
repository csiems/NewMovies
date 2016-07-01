package com.siems.udacitymovies.services;

import android.content.Context;

import com.siems.udacitymovies.BuildConfig;
import com.siems.udacitymovies.models.Movie;

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

public class MovieApiService {
    private Context mContext;

    public MovieApiService(Context context) {
        this.mContext = context;
    }

    public void findMovies(String searchInput, Callback callback) {
        String THE_MOVIE_DB_API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.themoviedb.org/3/discover/movie?").newBuilder();
        urlBuilder.addQueryParameter("api_key", THE_MOVIE_DB_API_KEY);
        urlBuilder.addQueryParameter("sortBy", "popularity.desc"); //other option will be vote_average.desc
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Movie> processResults(Response response) {
        ArrayList<Movie> movies = new ArrayList<>();

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

    private ArrayList<Movie> processArray(JSONArray specificArray) {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 0; i < specificArray.length(); i++) {
            try {
                JSONObject movieJSON = specificArray.getJSONObject(i);
                String id = movieJSON.getString("id");
                String poster_path = movieJSON.getString("poster_path");
                String release_date = movieJSON.getString("release_date");
                String title = movieJSON.getString("title");

                ArrayList<String> genre_ids = new ArrayList<>();
                JSONArray genre_idsJSON = movieJSON.getJSONArray("genre_ids");
                for (int y = 0; y < genre_idsJSON.length(); y++) {
                    genre_ids.add(genre_idsJSON.get(y).toString());
                }

                Movie movie = new Movie(id, poster_path, release_date, title);
                movies.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }

}

