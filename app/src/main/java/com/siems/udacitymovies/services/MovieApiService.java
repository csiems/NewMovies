package com.siems.udacitymovies.services;

import android.content.Context;
import android.util.Log;

import com.siems.udacitymovies.BuildConfig;
import com.siems.udacitymovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieApiService {
    private static final String TAG = MovieApiService.class.getName();
    private Context mContext;

    public MovieApiService(Context context) {
        this.mContext = context;
    }

    public void findPosters(String sortOrder, Callback callback) {
        String THE_MOVIE_DB_API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        Date lastYear = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String searchYear = formatter.format(lastYear);

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.themoviedb.org/3/discover/movie?").newBuilder();
        urlBuilder.addQueryParameter("api_key", THE_MOVIE_DB_API_KEY);
        urlBuilder.addQueryParameter("sort_by", sortOrder + ".desc");
        urlBuilder.addQueryParameter("primary_release_date.gte", searchYear);
        urlBuilder.addQueryParameter("vote_count.gte", "100");
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
                int movie_id = movieJSON.getInt("id");
                String title = movieJSON.getString("title");
                String release_date = movieJSON.getString("release_date");
                double vote_average = movieJSON.getDouble("vote_average");
                int vote_count = movieJSON.getInt("vote_count");
                String overview = movieJSON.getString("overview");
                String poster_path = movieJSON.getString("poster_path");
                double popularity = movieJSON.getDouble("popularity");

                Movie movie = new Movie(movie_id, title, release_date, vote_average, vote_count, overview, poster_path,
                        popularity);
                movies.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, movies.toString());
        return movies;
    }

}
