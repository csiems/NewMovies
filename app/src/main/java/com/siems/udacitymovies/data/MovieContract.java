package com.siems.udacitymovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 */
public class MovieContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.siems.udacitymovies.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    public static final String PATH_TRAILER = "trailers";
    public static final String PATH_POSTER = "posters";

    // Inner class that defines the table contents of the poster table
    public static final class PosterEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POSTER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POSTER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POSTER;

        public static Uri buildPosterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        // Table name
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
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;


        public static Uri buildTrailerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTrailerPoster(String parentPoster) {
            return CONTENT_URI.buildUpon().appendPath(parentPoster).build();
        }

        public static String getParentPosterFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        // Table name
        public static final String TABLE_NAME = "trailers";
        // Column with the foreign key into the poster table.
        public static final String COLUMN_POSTER_KEY = "poster_id";
        // UID for trailer
        public static final String COLUMN_TRAILER_ID = "id";
        // String appended onto Youtube URL to find video
        public static final String COLUMN_KEY = "key";
        // Name of trailer displayed in list
        public static final String COLUMN_NAME = "name";
        // Hosting site (always Youtube)
        public static final String COLUMN_SITE = "site";
        // Display size (e.g. 1080)
        public static final String COLUMN_SIZE = "size";
        // Video type (usually trailer)
        public static final String COLUMN_TYPE = "type";
    }
}
