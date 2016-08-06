package com.siems.udacitymovies.models;

import org.parceler.Parcel;

@Parcel
public class Trailer {
    public String id;
    public String name;
    public String key;


    public Trailer() {
    }

    public Trailer(String id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

}
