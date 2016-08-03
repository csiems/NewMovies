package com.siems.udacitymovies.models;

import org.parceler.Parcel;

@Parcel
public class Poster {
    public String poster_path;
    public String overview;
    public String release_date;
    public int id;
    public String title;
    public String backdrop_path;
    public double vote_average;

    public Poster() {}

    public Poster(String poster_path, String overview, String release_date, int id, String title, String backdrop_path, double vote_average) {
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.id = id;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public double getVote_average() {
        return vote_average;
    }


}

