package com.siems.udacitymovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.siems.udacitymovies.data.MovieContract.PosterEntry;
import com.siems.udacitymovies.data.MovieContract.TrailerEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " +
                PosterEntry.TABLE_NAME + " (" +
                PosterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PosterEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                PosterEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                PosterEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL," +
                PosterEntry.COLUMN_ID + " INTEGER NOT NULL, " +
                PosterEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                PosterEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                PosterEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL" +
                " );";

        final String SQL_CREATE_TRAILERS_TABLE = "CREATE TABLE " + TrailerEntry.TABLE_NAME + " (" +
                TrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TrailerEntry.COLUMN_POSTER_KEY + " INTEGER NOT NULL, " +
                TrailerEntry.COLUMN_TRAILER_ID + " TEXT NOT NULL, " +
                TrailerEntry.COLUMN_KEY + " TEXT NOT NULL, " +
                TrailerEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                TrailerEntry.COLUMN_SITE + " TEXT NOT NULL, " +
                TrailerEntry.COLUMN_SIZE + " INTEGER NOT NULL, " +
                TrailerEntry.COLUMN_TYPE + " TEXT NOT NULL, " +

                // Set up the poster column as a foreign key to poster table.
                "FOREIGN KEY (" + TrailerEntry.COLUMN_POSTER_KEY + ") REFERENCES " +
                PosterEntry.TABLE_NAME + " (" + PosterEntry._ID + "));";


        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRAILERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database's upgrade policy is to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PosterEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TrailerEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
