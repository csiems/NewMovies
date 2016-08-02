package com.siems.udacitymovies.models;

import org.parceler.Parcel;

@Parcel
public class Trailer {
    public String id;
    public String key;
    public String name;
    public String site;
    public Integer size;
    public String type;

    public Trailer() {
    }

    public Trailer(String id, String key, String name, String site, Integer size, String type) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
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

    public String getSite() {
        return site;
    }

    public Integer getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
