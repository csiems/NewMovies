package com.siems.udacitymovies.models;

import org.parceler.Parcel;

@Parcel
public class Movie {
    public int movie_id;
    public String title;
    public String release_date;
    public double vote_average;
    public int vote_count;
    public String overview;
    public String poster_path;
    public double popularity;
    public int runtime;
    public int favorite;

    public Movie() {}

    public Movie(int movie_id, String title, String release_date, double vote_average, int vote_count, String overview, String poster_path, double popularity) {
        this.movie_id = movie_id;
        this.title = title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.overview = overview;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.runtime = 0;
        this.favorite = 0;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getRuntime() {
        return runtime;
    }

    public int getFavorite() {
        return favorite;
    }
}




