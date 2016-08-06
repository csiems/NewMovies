package com.siems.udacitymovies.models;

import org.parceler.Parcel;

@Parcel
public class Review {

    public String id;
    public String author;
    public String content;

    public Review() {}

    public Review(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
