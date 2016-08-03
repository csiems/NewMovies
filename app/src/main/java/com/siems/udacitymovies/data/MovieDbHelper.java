//package com.siems.udacitymovies.data;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.siems.udacitymovies.data.MovieContract.PosterEntry;
//import com.siems.udacitymovies.data.MovieContract.TrailerEntry;
//
//
///**
// * Manages a local database for weather data.
// */
//public class MovieDbHelper extends SQLiteOpenHelper {
//
//    // If you change the database schema, you must increment the database version.
//    private static final int DATABASE_VERSION = 1;
//
//    static final String DATABASE_NAME = "movies.db";
//
//    public MovieDbHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + PosterEntry.TABLE_NAME + " (" +
//                // Why AutoIncrement here, and not above?
//                // Unique keys will be auto-generated in either case.  But for weather
//                // forecasting, it's reasonable to assume the user will want information
//                // for a certain date and all dates *following*, so the forecast data
//                // should be sorted accordingly.
//                PosterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//
//                // the ID of the location entry associated with this weather data
//                PosterEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
//                PosterEntry.COLUMN_ADULT + " INTEGER NOT NULL, " +
//                PosterEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
//                PosterEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL," +
//
//                PosterEntry.COLUMN_GENRE_IDS + " REAL NOT NULL, " +
//                PosterEntry.COLUMN_ID + " REAL NOT NULL, " +
//
//                PosterEntry.COLUMN_ORIGINAL_TITLE + " REAL NOT NULL, " +
//                PosterEntry.COLUMN_ORIGINAL_LANGUAGE + " REAL NOT NULL, " +
//                PosterEntry.COLUMN_TITLE + " REAL NOT NULL, " +
//                PosterEntry.COLUMN_BACKDROP_PATH + " REAL NOT NULL, " +
//                PosterEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
//                PosterEntry.COLUMN_VOTE_COUNT + " REAL NOT NULL, " +
//                PosterEntry.COLUMN_VIDEO + " REAL NOT NULL, " +
//                PosterEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
//
//                // Set up the location column as a foreign key to location table.
//                " FOREIGN KEY (" + WeatherEntry.COLUMN_LOC_KEY + ") REFERENCES " +
//                LocationEntry.TABLE_NAME + " (" + LocationEntry._ID + "), " +
//
//                // To assure the application have just one weather entry per day
//                // per location, it's created a UNIQUE constraint with REPLACE strategy
//                " UNIQUE (" + WeatherEntry.COLUMN_DATE + ", " +
//                WeatherEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";
//
//        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        // This database is only a cache for online data, so its upgrade policy is
//        // to simply to discard the data and start over
//        // Note that this only fires if you change the version number for your database.
//        // It does NOT depend on the version number for your application.
//        // If you want to update the schema without wiping data, commenting out the next 2 lines
//        // should be your top priority before modifying this method.
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherEntry.TABLE_NAME);
//        onCreate(sqLiteDatabase);
//    }
//}
