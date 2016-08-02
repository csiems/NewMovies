package com.siems.udacitymovies.data;

import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 */
public class MovieContract {

    // Inner class that defines the table contents of the poster table
    public static final class PosterEntry implements BaseColumns {
        public static final String TABLE_NAME = "posters";

        // Column with link to movie poster.
        public static final String COLUMN_POSTER_PATH = "poster_path";
        // Boolean describing whether movie is an adult movie.
        public static final String COLUMN_ADULT = "adult";
        // Summary of movie
        public static final String COLUMN_OVERVIEW = "overview";
        // Date of movie's release in theaters.
        public static final String COLUMN_RELEASE_DATE = "release_date";
        // A list of movie genre ids (integers)
        public static final String COLUMN_GENRE_IDS = "genre_ids";
        // Int UID used to search for trailers and reviews.
        public static final String COLUMN_ID = "id";
        // Original title of movie
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        // Original language of movie
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        // Title of movie
        public static final String COLUMN_TITLE = "title";
        // Image designed to be displayed as background of a detail page
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        // Number indicating popularity of movie stored as a double
        public static final String COLUMN_POPULARITY = "popularity";
        // Number of votes used in determining popularity/vote_average
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        // Boolean indicating whether movie is a video/TV show or a movie
        public static final String COLUMN_VIDEO = "video";
        // Number indicating average vote, stored as a double and displayed on detail page
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
    }

    // Inner class that defines the table contents of the trailer table
    public static final class TrailerEntry implements BaseColumns {
        public static final String TABLE_NAME = "trailers";

        // Column with the foreign key into the poster table.
        public static final String COLUMN_POSTER_KEY = "poster_id";
        // UID for trailer
        public String id;
        // String appended onto Youtube URL to find video
        public String key;
        // Name of trailer displayed in list
        public String name;
        // Hosting site (always Youtube)
        public String site;
        // Display size (e.g. 1080)
        public Integer size;
        // Video type (usually trailer)
        public String type;
    }
}
