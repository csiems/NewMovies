package com.siems.udacitymovies.services;

import android.content.Context;
import android.util.Log;

import com.siems.udacitymovies.BuildConfig;
import com.siems.udacitymovies.models.Poster;

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

public class PosterApiService {
    private static final String TAG = PosterApiService.class.getName();
    private Context mContext;

    public PosterApiService(Context context) {
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
                String overview = movieJSON.getString("overview");
                String release_date = movieJSON.getString("release_date");
                int id = movieJSON.getInt("id");
                String title = movieJSON.getString("title");
                String backdrop_path = movieJSON.getString("backdrop_path");
                double vote_average = movieJSON.getDouble("vote_average");

                Poster poster = new Poster(poster_path, overview, release_date, id, title, backdrop_path, vote_average);
                posters.add(poster);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, posters.toString());
        return posters;
    }

}
