package com.siems.udacitymovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MovieProvider extends ContentProvider {

    public static final String LOG_TAG = MovieProvider.class.getSimpleName();

    public static final int MOVIE = 100;

    private static final UriMatcher sUriMatcher = createUriMatcher();
    private SQLiteOpenHelper mOpenHelper;


    /**
     * Create the {@code UriMatcher} for pointing records into the DB
     *
     * @return A {@code UriMatcher} instance
     */
    public static UriMatcher createUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIE, MOVIE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }


    /**
     * Queries the DB for the data requested by the caller of the method
     *
     * @param uri           The Uri of the data queried, containing information about where to search
     * @param projection    An array of required columns from the table ({@code null} for all columns)
     * @param selection     The where clause
     * @param selectionArgs The arguments of the where clause
     * @param sortOrder     The order of sorting the movies
     * @return A {@code Cursor} instance with the data
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                retCursor = db.query(
                        MovieContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /**
     * Returns the type of the item pointed by a given URI
     *
     * @param uri A given content URI pointing to data in the sqlite database
     * @return The type data pointed by the uri
     */
    @Override
    public String getType(Uri uri) {
        int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIE:
                return MovieContractOld.MovieEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    /**
     * Insert a new record into the table pointed by the Uri
     *
     * @param uri            The Uri that should point into a table
     * @param values         Record to add into the table
     * @return               A Content Uri with the ID of the inserted row
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnedUri;

        long insertedId;
        switch (match) {
            case MOVIE:
                insertedId = db.insert(MovieContractOld.MovieEntry.TABLE_NAME, null, values);
                if (insertedId > 0) {
                    returnedUri = MovieContract.MovieEntry.buildMovieUri(insertedId);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnedUri;
    }

    /**
     * Deletes the records in the table pointed by the URI and match the other args
     *
     * @param uri           Uri of the table where to search the records to delete
     * @param selection     A selection criteria to apply when filtering rows. If {@code null} then all
     *                      rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values
     *                      from selectionArgs, in order that they appear in the selection. The values
     *                      will be bound as Strings.
     * @return The number of deleted rows
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDeleted;

        //delete all rows and return the number of records deleted
        if (selection == null) selection = "1";

        switch (match) {
            case MOVIE:
                rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }


    /**
     * Updates a record on the table pointed by the URI.
     *
     * @param uri           Uri of the table where to search the records to update
     * @param values        The new set of {@code ContentValues} to replace the old ones
     * @param selection     A selection criteria to apply when filtering rows. If {@code null} then all
     *                      rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values
     *                      from selectionArgs, in order that they appear in the selection. The values
     *                      will be bound as Strings.
     * @return The number of updated rows
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MOVIE:
                rowsUpdated = db.update(
                        MovieContractOld.MovieEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
