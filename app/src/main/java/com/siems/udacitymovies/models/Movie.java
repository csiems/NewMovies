package com.siems.udacitymovies.models;

public class Movie {
    private String id;
    private String poster_path;
    private String release_date;
    private String title;

    public Movie(String id, String poster_path, String release_date, String title) {
        this.id = id;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
