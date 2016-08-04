package com.siems.udacitymovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

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

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

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

        public static Uri buildPosterWithStartDate(String poster, long startDate) {
            long normalizedDate = normalizeDate(startDate);
            return CONTENT_URI.buildUpon().appendPath(poster)
                    .appendQueryParameter(COLUMN_RELEASE_DATE,
                            Long.toString(normalizedDate)).build();
        }

        public static long getReleaseDateFromUri(Uri uri){
            String dateStr = uri.getQueryParameter(COLUMN_RELEASE_DATE);
            if (dateStr != null && dateStr.length() > 0){
                return Long.parseLong(dateStr);
            }
            else return 0;
        }

        public static String getTrailerFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }

        // TABLE AND COLUMN NAMES
        // Table name
        public static final String TABLE_NAME = "posters";
        // Column with link to movie poster.
        public static final String COLUMN_POSTER_PATH = "poster_path";
        // Summary of movie
        public static final String COLUMN_OVERVIEW = "overview";
        // Date of movie's release in theaters.
        public static final String COLUMN_RELEASE_DATE = "release_date";
        // Int UID used to search for trailers and reviews.
        public static final String COLUMN_ID = "id";
        // Title of movie
        public static final String COLUMN_TITLE = "title";
        // Image designed to be displayed as background of a detail page
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        // Number indicating average vote, stored as a double and displayed on detail page
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
    }

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

        // TABLE AND COLUMN NAMES
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
