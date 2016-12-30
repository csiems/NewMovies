package com.siems.udacitymovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDbHelper extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movies.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creates db the first time program is run.
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createMoviesTable = "CREATE TABLE " + MovieContractOld.MovieEntry.TABLE_NAME + " ( "
                + MovieContractOld.MovieEntry._ID + " INTEGER PRIMARY KEY, "
                + MovieContractOld.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, "
                + MovieContractOld.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + MovieContractOld.MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, "
                + MovieContractOld.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, "
                + MovieContractOld.MovieEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, "
                + MovieContractOld.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, "
                + MovieContractOld.MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "
                + MovieContractOld.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, "
                + MovieContractOld.MovieEntry.COLUMN_RUNTIME + " INTEGER, "
                + MovieContractOld.MovieEntry.COLUMN_FAVORITE + " INTEGER DEFAULT 0, "
                + " UNIQUE (" + MovieContractOld.MovieEntry.COLUMN_TITLE + ") ON CONFLICT REPLACE);";

        db.execSQL(createMoviesTable);
    }

    // Only gets called if database version number is incremented
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContractOld.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}